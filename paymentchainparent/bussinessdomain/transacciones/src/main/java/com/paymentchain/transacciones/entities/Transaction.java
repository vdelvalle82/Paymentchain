/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transacciones.entities;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
/**
 *
 * @author Virginia del Valle
 */
@Data
@Entity
public class Transaction {
   
   @GeneratedValue(strategy = GenerationType.AUTO)  
   @Id
   private long id;
   private String reference; //Identificador unico de la transacción para el negocio
   private String ibanAccount; //Número de cuenta bancaria del cliente
   private Date date; //Fecha en que se realizazo la transacción
   private double amount; //Monto de la transacción, si el valor es negativo sera un debito(disminuye el saldo), si el valor es positivo sera un credito (aumenta el saldo)
   private double fee; //Cómision de la transacción
   private String descripcion; //Descripción breve de la transacción
   
   @Enumerated(EnumType.STRING)
   private StatusEnum status; //Guarda el estado de la transacción 01 pendiente, 02 liquidada, 03 rechazada, 04 cancelada
   
   @Enumerated(EnumType.STRING)
   private ChannelEnum channel;//Indica el canal por el que se realiza la transacción y debe ser uno de los siguientes valores:WEB,CAJERO,OFICINA
   
   
}
