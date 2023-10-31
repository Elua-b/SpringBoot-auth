package live.learnlynx.api.v1.services;

import live.learnlynx.api.v1.dtos.CreateCourseDTO;
import live.learnlynx.api.v1.dtos.UpdateCourseDTO;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.models.Course;
import live.learnlynx.api.v1.models.User;

import java.util.List;
import java.util.UUID;

public interface ICourseService {

    public Course createCourse(CreateCourseDTO dto, File courseImage, File courseIntroductoryVideo);
    public Course updateCourse(UUID courseId, UpdateCourseDTO dto, File courseImage, File courseIntroductoryVideo);

    public String deleteCourse(UUID courseId);
    public List<Course> getCourses();
    public Course getCourseById(UUID id);

    public Course getCourseByInstructor(UUID instructorId);
    public List<User> getLearnersPursuingCourse(UUID courseId);
}
