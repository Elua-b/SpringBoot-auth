package live.learnlynx.api.v1.repositories;

import live.learnlynx.api.v1.models.Course;
import live.learnlynx.api.v1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ICourseRepository extends JpaRepository<Course, UUID> {

    @Query("SELECT c FROM Course c where c.courseInstructor=:instructor")
    public Course getCourseByInstructor(User instructor);

}
