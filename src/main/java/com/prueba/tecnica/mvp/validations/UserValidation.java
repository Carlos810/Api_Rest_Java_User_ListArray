package com.prueba.tecnica.mvp.validations;

import com.prueba.tecnica.mvp.service.UserServiceMemory;

public class UserValidation {

    public static void validateEmailUnique(String email){

        boolean exists = UserServiceMemory.users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

        if(exists){
            throw new IllegalArgumentException("email already registered");
        }
    }

    public static void validateTaxIdUnique(String taxId){

        boolean exists = UserServiceMemory.users.stream()
                .anyMatch(u -> u.getTax_id().equalsIgnoreCase(taxId));

        if(exists){
            throw new IllegalArgumentException("tax_id already registered");
        }
    }
}
