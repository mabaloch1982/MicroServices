package com.aa.microservices.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//One of handling exception in Spring, for adding custom errors refer to Advice Class
//In case Advice class is defined, and this exception is thrown it will mix both exceptions, means message from this execption will be used in custom error defined of advice exception 
//If global exception is defined then it really doesnt matter if ResponseStatus annotation is used or nor
//@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException{

		private String resourceName;
	    private String fieldName;
	    private Long fieldValue;

	    
	    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue){
	        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
	        this.resourceName = resourceName;
	        this.fieldName = fieldName;
	        this.fieldValue = fieldValue;
	    }
}
