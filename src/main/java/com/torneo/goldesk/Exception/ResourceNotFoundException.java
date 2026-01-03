package com.torneo.goldesk.Exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends GoldeskException{

    public ResourceNotFoundException(String message){
        super(message,"RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}

