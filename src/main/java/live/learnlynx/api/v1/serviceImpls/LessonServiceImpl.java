package live.learnlynx.api.v1.serviceImpls;

import live.learnlynx.api.v1.dtos.CreateLessonDTO;
import live.learnlynx.api.v1.dtos.UpdateLessonDTO;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.models.Course;
import live.learnlynx.api.v1.models.Lesson;
import live.learnlynx.api.v1.repositories.ILessonRepository;
import live.learnlynx.api.v1.services.ICourseService;
import live.learnlynx.api.v1.services.ILessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements ILessonService {

    private final ICourseService courseService;
    private final ILessonRepository lessonRepository;

    @Override
    public Lesson createLesson(CreateLessonDTO dto, List<File> lessonMultimedia) {
        Course course = this.courseService.getCourseById(dto.getCourseId());
        Lesson lesson = new Lesson(dto.getLessonTitle(), dto.getLessonDescription(), course, lessonMultimedia);
        this.lessonRepository.save(lesson);
        return lesson;
    }

    @Override
    public Lesson updateLesson(UUID lessonId, UpdateLessonDTO dto) {
        Lesson lesson = this.lessonRepository.getById(lessonId);
        lesson.setLessonDescription(dto.getLessonDescription());
        lesson.setLessonTitle(dto.getLessonTitle());
        this.lessonRepository.save(lesson);
        return lesson;
    }

    @Override
    public Lesson addLessonMultimedia(UUID lessonId, File newLessonMultimedia) {
        Lesson lesson = this.lessonRepository.getById(lessonId);
        List<File> lessonMultimedia = lesson.getLessonMultimedia();
        lessonMultimedia.add(newLessonMultimedia);
        lesson.setLessonMultimedia(lessonMultimedia);
        this.lessonRepository.save(lesson);
        return lesson;
    }

    @Override
    public Lesson deleteLessonMultimedia(UUID lessonId, File lessonMultimediaToRemove) {
        Lesson lesson = this.lessonRepository.getById(lessonId);
        List<File> lessonMultimedia = lesson.getLessonMultimedia();
        lessonMultimedia.remove(lessonMultimediaToRemove);
        lesson.setLessonMultimedia(lessonMultimedia);
        this.lessonRepository.save(lesson);
        return lesson;
    }

    @Override
    public String deleteLesson(UUID lessonId) {
        this.lessonRepository.deleteById(lessonId);
        return "Lesson deleted successfully";
    }

    @Override
    public List<Lesson> getLessonsByCourse(UUID courseId) {
        return this.lessonRepository.getLessonByCourse(this.courseService.getCourseById(courseId));
    }

    @Override
    public Lesson getLessonById(UUID lessonId) {
        return this.lessonRepository.getById(lessonId);
    }
}
