package com.prueba.tecnica.mvp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ModelUser {
    @Id
    private UUID id;

    @Email
    @NotBlank(message = "enter email")
    @Size(min=12, max=120)
    private String email;

    @NotBlank(message = "enter name")
    @Size(min=3, max=120)
    private String name;

    @NotBlank(message = "enter cellphone")
    @Pattern(regexp = "^[0-9]{10}$",message = "phone must contain exactly 10 digits national number without country code. (example: 01234567890)")
    private String phone;

    @Size(min=8, max=120)
    @NotBlank(message = "enter password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /*RFC persona física (13) o moral (12)"*/
    @Size(min=12, max=13,message = "tax_id must contain 13 characters for mexican users or 13 chars for enterpise members")
    @Pattern(
            regexp = "^([A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3})$",
            message = "tax_id must follow RFC format: 3-4 letters + 6 digit date (YYMMDD) + 3 SAT characters (example: GOMC850412AB3)"
    )
    private String tax_id;

    private String created_at;

    private List<ModelAddress> addresses;
}
