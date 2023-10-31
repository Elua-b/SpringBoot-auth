package live.learnlynx.api.v1.services;

import live.learnlynx.api.v1.dtos.InitiatePasswordDTO;
import live.learnlynx.api.v1.dtos.ResetPasswordDTO;
import live.learnlynx.api.v1.dtos.SignInDTO;
import live.learnlynx.api.v1.payload.JwtAuthenticationResponse;

public interface IAuthenticationService {

    public JwtAuthenticationResponse login(SignInDTO dto);
    public String initiateResetPassword(InitiatePasswordDTO dto);
    public String resetPassword(ResetPasswordDTO dto);
    public String initiateAccountVerification();
    public String verifyAccount(String accountVerificationToken);

}
