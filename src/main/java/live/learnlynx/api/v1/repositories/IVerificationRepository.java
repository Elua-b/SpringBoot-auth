package live.learnlynx.api.v1.repositories;

import live.learnlynx.api.v1.models.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IVerificationRepository extends JpaRepository<Verification, UUID> {

    @Query("SELECT v FROM Verification v WHERE v.verificationToken=:accountVerificationToken AND v.verificationExpiresAt > current_timestamp ")
    public Optional<Verification> getVerificationByToken(String accountVerificationToken);
}

