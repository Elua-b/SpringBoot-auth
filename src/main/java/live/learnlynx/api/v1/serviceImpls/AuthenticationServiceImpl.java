package live.learnlynx.api.v1.serviceImpls;

import live.learnlynx.api.v1.dtos.InitiatePasswordDTO;
import live.learnlynx.api.v1.dtos.ResetPasswordDTO;
import live.learnlynx.api.v1.dtos.SignInDTO;
import live.learnlynx.api.v1.enums.EPasswordResetStatus;
import live.learnlynx.api.v1.enums.EVerificationStatus;
import live.learnlynx.api.v1.exceptions.AppException;
import live.learnlynx.api.v1.exceptions.ResourceNotFoundException;
import live.learnlynx.api.v1.models.User;
import live.learnlynx.api.v1.models.Verification;
import live.learnlynx.api.v1.payload.JwtAuthenticationResponse;
import live.learnlynx.api.v1.repositories.IUserRepository;
import live.learnlynx.api.v1.repositories.IVerificationRepository;
import live.learnlynx.api.v1.security.JwtTokenProvider;
import live.learnlynx.api.v1.services.IAuthenticationService;
import live.learnlynx.api.v1.services.IUserService;
import live.learnlynx.api.v1.services.MailService;
import live.learnlynx.api.v1.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final IUserRepository userRepository;
    private final IVerificationRepository verificationRepository;

    @Override
    public JwtAuthenticationResponse login(SignInDTO dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = null;

        try {
            jwt = jwtTokenProvider.generateToken(authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public String initiateResetPassword(InitiatePasswordDTO dto) {
        User user = this.userService.getByEmail(dto.getEmail());
        user.getPasswordReset().setPasswordResetToken(Utility.randomUUID(6, 0, 'N'));
        user.getPasswordReset().setPasswordResetStatus(EPasswordResetStatus.PENDING);

        this.userRepository.save(user);

        mailService.sendResetPasswordMail(user.getEmail(), user.getFirstName() + " " + user.getLastName(), user.getPasswordReset().getPasswordResetToken());
        return "Please check your mail and activate account";
    }

    @Override
    public String resetPassword(ResetPasswordDTO dto) {
        User user = this.userService.getByEmail(dto.getEmail());

        if (Utility.isCodeValid(user.getPasswordReset().getPasswordResetToken(), dto.getActivationCode()) &&
                user.getPasswordReset().getPasswordResetStatus().equals(EPasswordResetStatus.PENDING)) {
            user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            user.getPasswordReset().setPasswordResetInitiatedAt(null);
            this.userRepository.save(user);
            return "Password reset successfully";
        } else {
            throw new AppException("Invalid code or account status");
        }
    }

    @Override
    public String initiateAccountVerification() {
        User user = this.userService.getLoggedInUser();
        Verification verification = user.getVerification();
        System.out.println(verification);
        verification.setVerificationStatus(EVerificationStatus.PENDING);
        verification.setVerificationToken(Utility.randomUUID(8, 1, 'N'));
        verification.setVerificationExpiresAt(LocalDateTime.now().plusHours(5));
        this.verificationRepository.save(verification);
        mailService.sendVerificationMail(user.getEmail(), user.getFirstName() + " " + user.getLastName(), verification.getVerificationToken());
        return "Check your email for the verification code";
    }

    @Override
    public String verifyAccount(String accountVerificationToken) {
        Optional<Verification> optionalVerification = this.verificationRepository.getVerificationByToken(accountVerificationToken);
        if (optionalVerification.isEmpty())
            throw new ResourceNotFoundException("Verification", "account_verification_token", accountVerificationToken);
        Verification verification = optionalVerification.get();
        verification.setVerificationToken(null);
        verification.setLastVerifiedAt(LocalDateTime.now());
        verification.setVerificationStatus(EVerificationStatus.VERIFIED);
        verification.setVerificationExpiresAt(null);
        this.verificationRepository.save(verification);
        return "Account verified successfully";
    }
}
