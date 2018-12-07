package com.whereIsMyMoney.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ObjectToJSON {

    public static String convertToJson( final Object o){
        try{
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
