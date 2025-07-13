package com.paymentchain.transacciones.entities;

public enum StatusEnum {
	 	
		PENDIENTE("01"),
	    LIQUIDADA("02"),
	    RECHAZADA("03"),
	    CANCELADA("04");
	
	private final String codigo;

    StatusEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
