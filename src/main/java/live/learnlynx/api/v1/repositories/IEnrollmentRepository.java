package live.learnlynx.api.v1.repositories;

import live.learnlynx.api.v1.models.Course;
import live.learnlynx.api.v1.models.Enrollment;
import live.learnlynx.api.v1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IEnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    @Query("SELECT e FROM Enrollment  e WHERE e.course=:course")
    public List<Enrollment> getEnrollmentsByCourse(Course course);

    @Query("SELECT e FROM Enrollment e WHERE e.user=:user")
    public List<Enrollment> getEnrollmentsByUser(User user);

}
