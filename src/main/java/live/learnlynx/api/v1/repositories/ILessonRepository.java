package live.learnlynx.api.v1.repositories;

import live.learnlynx.api.v1.models.Course;
import live.learnlynx.api.v1.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, UUID> {

    @Query("SELECT l FROM Lesson l WHERE l.course=:course")
    public List<Lesson> getLessonByCourse(Course course);

}
