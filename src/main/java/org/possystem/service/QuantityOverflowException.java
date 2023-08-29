package org.possystem.service;

public class QuantityOverflowException extends Exception{
    public QuantityOverflowException(String message){
        super(message);
    }
}
