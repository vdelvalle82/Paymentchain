/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.paymentchain.product.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentchain.product.entities.Product;

/**
 *
 * @author Virginia del Valle
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
