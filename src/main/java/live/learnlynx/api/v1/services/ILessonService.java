package live.learnlynx.api.v1.services;

import live.learnlynx.api.v1.dtos.CreateLessonDTO;
import live.learnlynx.api.v1.dtos.UpdateLessonDTO;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.models.Lesson;

import java.util.List;
import java.util.UUID;

public interface ILessonService {

    public Lesson createLesson(CreateLessonDTO dto, List<File> lessonMultimedia);

    public Lesson updateLesson(UUID lessonId, UpdateLessonDTO dto);

    public Lesson addLessonMultimedia(UUID lessonId, File newLessonMultimedia);
    public Lesson deleteLessonMultimedia(UUID lessonId, File lessonMultimediaToRemove);

    public String deleteLesson(UUID lessonId);

    public List<Lesson> getLessonsByCourse(UUID courseId);

    public Lesson getLessonById(UUID lessonId);

}
