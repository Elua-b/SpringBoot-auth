package live.learnlynx.api.v1.models;

import live.learnlynx.api.v1.enums.EPasswordResetStatus;
import live.learnlynx.api.v1.enums.EVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "password_resets")
@AllArgsConstructor
@NoArgsConstructor
public class PasswordReset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "password_last_rest_at")
    private LocalDateTime lastResetAt;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "password_reset_status")
    private EPasswordResetStatus passwordResetStatus=EPasswordResetStatus.NEVER_RESET;

    @Column(name = "password_reset_initiated_at")
    private LocalDateTime passwordResetInitiatedAt;

}
