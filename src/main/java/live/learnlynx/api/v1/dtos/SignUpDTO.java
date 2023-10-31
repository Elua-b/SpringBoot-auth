package live.learnlynx.api.v1.dtos;


import live.learnlynx.api.v1.enums.EGender;
import live.learnlynx.api.v1.enums.ERole;
import live.learnlynx.api.v1.security.ValidPassword;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class SignUpDTO {

    @Email
    private  String email;

    @NotBlank
    private  String firstName;

    @NotBlank
    private  String lastName;

    @NotBlank
    private  String mobile;

    private EGender gender;

    private ERole role;

    @ValidPassword
    private  String password;
}
