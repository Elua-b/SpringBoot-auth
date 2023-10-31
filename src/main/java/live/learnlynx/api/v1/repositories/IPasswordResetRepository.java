package live.learnlynx.api.v1.repositories;

import live.learnlynx.api.v1.models.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
}
