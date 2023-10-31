package live.learnlynx.api.v1.repositories;

import live.learnlynx.api.v1.models.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMarkRepository extends JpaRepository<Mark, UUID> {
}
