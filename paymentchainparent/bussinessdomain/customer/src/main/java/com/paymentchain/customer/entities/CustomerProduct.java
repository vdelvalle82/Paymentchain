package com.paymentchain.customer.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
/**
 * Clase de asociación entre un cliente(Customer) y un product (product)
 * @author Virginia
 *
 */

@Data
@Entity
public class CustomerProduct {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long productId;
	
	@Transient // se usa para indicar que no se va a guardar en la bbdd
	private String productName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customer;
}
