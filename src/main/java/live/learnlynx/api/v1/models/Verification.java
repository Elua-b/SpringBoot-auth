package live.learnlynx.api.v1.models;

import live.learnlynx.api.v1.enums.EVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "verifications")
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "last_verified_at")
    private LocalDateTime lastVerifiedAt;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "verification_status")
    private EVerificationStatus verificationStatus;

    @Column(name = "verification_expires_at")
    private LocalDateTime verificationExpiresAt;
}
