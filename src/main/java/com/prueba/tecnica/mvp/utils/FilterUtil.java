package com.prueba.tecnica.mvp.utils;

import com.prueba.tecnica.mvp.model.ModelUser;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;


public class FilterUtil {

    public static Stream<ModelUser> applyFilter(Stream<ModelUser> stream, String filter){
        System.out.println("start apply filter:  "+filter);
        String[] parts = filter.split("[\\s+]+");
        System.out.println("splitted apply filter:  "+java.util.Arrays.toString(parts));
        if(parts.length != 3){
            throw new IllegalArgumentException("Invalid filter format. Expected field+op+value");
        }

        String field = parts[0];
        String op = parts[1];
        String value = parts[2];

        return stream.filter(u -> evaluate(u, field, op, value));
    }

    private static boolean evaluate(ModelUser user, String field, String op, String value){
        String fieldValue;

        switch(field){
            case "name" -> fieldValue = user.getName();
            case "email" -> fieldValue = user.getEmail();
            case "phone" -> fieldValue = user.getPhone();
            case "tax_id" -> fieldValue = user.getTax_id();
            case "created_at" -> fieldValue = user.getCreated_at();
            default -> { return false; }
        }

        if(fieldValue == null) return false;

        return switch(op){

            case "eq" -> fieldValue.equalsIgnoreCase(value);

            case "co" -> fieldValue.toLowerCase().contains(value.toLowerCase());

            case "sw" -> fieldValue.toLowerCase().startsWith(value.toLowerCase());

            case "ew" -> fieldValue.toLowerCase().endsWith(value.toLowerCase());

            case "dif" -> !fieldValue.equalsIgnoreCase(value);

            default -> false;
        };

    }

    public static String getMadagascarTime(){

        ZoneId zone = ZoneId.of("Indian/Antananarivo");

        DateTimeFormatter format =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return ZonedDateTime.now(zone).format(format);
    }

}
