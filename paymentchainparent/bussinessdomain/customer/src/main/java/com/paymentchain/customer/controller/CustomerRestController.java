/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.controller;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.respository.CustomerRepository;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

/**
 *
 * @author Virginia del Valle
 */
@RestController
@RequestMapping("/customer")
public class CustomerRestController {
    
    @Autowired
    CustomerRepository customerRepository;
    
    //cliente reactivo web para consumir la api
    private final WebClient.Builder webClientBuilder;
    
    public CustomerRestController(WebClient.Builder webClientBuilder) {
    	this.webClientBuilder = webClientBuilder;  
    }
    
    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)  
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5));   // 5 segundos timeout lectura
                connection.addHandlerLast(new WriteTimeoutHandler(5));  // 5 segundos timeout escritura
            });
       
     
    /**
     *   método para llamar al microservicio de product, busca por id
     *   y devuelve el nombre del producto  
     * @param id
     * @return
     */
    private String getProductName(long id) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:8083/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8083/product"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        String name = block.get("name").asText();
        return name;
    }
    
    @GetMapping()
    public List<Customer> list() {
        return customerRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Customer get(@PathVariable(name = "id") long id) {
        return customerRepository.findById(id).get();
    }
    
    
    /**
     * Método que busca en la bbdd un cliente, y ese cliente tiene
     * un listado de productos donde tiene un id, recorremos
     * el listado y usamos el id para llamar a getPRoductName
     * para obterner el nombre.
     * @param code
     * @return
     */
    @GetMapping("/full")
    public Customer getByCode(@RequestParam String code) {
    	Customer customer = this.customerRepository.findBycode(code);
		List<CustomerProduct> products = customer.getProducts();
		products.forEach(x->{
			String productName = getProductName(x.getId());
			x.setProductName(productName);
		});
		return customer;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody Customer input) {
         Customer find = customerRepository.findById(id).get();   
        if(find != null){     
            find.setCode(input.getCode());
            find.setName(input.getName());
            find.setIban(input.getIban());
            find.setPhone(input.getPhone());
            find.setSurname(input.getSurname());
            find.setAddress(input.getAddress());
            
            // Actualizar productos
            find.getProducts().clear();
            input.getProducts().forEach(p -> p.setCustomer(find));
            find.getProducts().addAll(input.getProducts());
        }
        Customer save = customerRepository.save(find);
           return ResponseEntity.ok(save);
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) {
    	//si el input trae muchos product, le asigna quien es el customer
        input.getProducts().forEach(x -> x.setCustomer(input));
        Customer save = customerRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")   
    public ResponseEntity<?> delete(@PathVariable("id") long id){
          Optional<Customer> findById = customerRepository.findById(id);   
        if(findById.get() != null){               
                  customerRepository.delete(findById.get());  
        }
        return ResponseEntity.ok().build();
    }
    
}
