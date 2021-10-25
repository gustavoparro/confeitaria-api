package com.gustavoparro.confeitaria_api.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class AuthUserForm {

    @Email
    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 6, max = 20)
    private String password;

}
