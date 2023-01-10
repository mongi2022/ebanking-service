package com.tpc.ebankingservice.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

//@ExceptionHandler
public class CustomerNotFoundException extends Exception{
public CustomerNotFoundException(String message){
 super(message);
}
}
