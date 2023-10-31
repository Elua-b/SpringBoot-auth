package live.learnlynx.api.v1.dtos;

import live.learnlynx.api.v1.security.ValidPassword;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ResetPasswordDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String activationCode;

    @ValidPassword
    private String password;
}
