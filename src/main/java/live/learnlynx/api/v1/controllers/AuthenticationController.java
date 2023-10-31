package live.learnlynx.api.v1.controllers;

import live.learnlynx.api.v1.dtos.InitiatePasswordDTO;
import live.learnlynx.api.v1.dtos.ResetPasswordDTO;
import live.learnlynx.api.v1.dtos.SignInDTO;
import live.learnlynx.api.v1.payload.ApiResponse;
import live.learnlynx.api.v1.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody SignInDTO dto) {
        return ResponseEntity.ok(new ApiResponse(true, "Logged in successfully", this.authenticationService.login(dto)));
    }

    @PostMapping(path = "/initiate-reset-password")
    public ResponseEntity<ApiResponse> initiateResetPassword(@RequestBody @Valid InitiatePasswordDTO dto) {
        return ResponseEntity.ok(new ApiResponse(true, this.authenticationService.initiateResetPassword(dto)));
    }


    @PostMapping(path = "/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordDTO dto) {
        return ResponseEntity.ok(new ApiResponse(true, this.authenticationService.resetPassword(dto)));
    }

    @PatchMapping(path = "/initiate-email-verification")
    public ResponseEntity<ApiResponse> initiateEmailVerification() {
        return ResponseEntity.ok(new ApiResponse(true, this.authenticationService.initiateAccountVerification()));
    }

    @PostMapping(path = "/verify-email/{token}")
    public ResponseEntity<ApiResponse> resetPassword(@PathVariable(name = "token") String accountVerificationToken) {
        return ResponseEntity.ok(new ApiResponse(true, this.authenticationService.verifyAccount(accountVerificationToken)));
    }
}