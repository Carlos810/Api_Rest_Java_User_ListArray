package com.prueba.tecnica.mvp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
public class ModelAddress {
    private Long id;
    private String name;
    private String street;
    private String country_code;

}
