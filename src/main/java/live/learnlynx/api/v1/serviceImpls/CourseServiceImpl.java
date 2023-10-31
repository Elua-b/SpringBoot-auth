package live.learnlynx.api.v1.serviceImpls;

import live.learnlynx.api.v1.dtos.CreateCourseDTO;
import live.learnlynx.api.v1.dtos.UpdateCourseDTO;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.models.Course;
import live.learnlynx.api.v1.models.Enrollment;
import live.learnlynx.api.v1.models.Tag;
import live.learnlynx.api.v1.models.User;
import live.learnlynx.api.v1.repositories.ICourseRepository;
import live.learnlynx.api.v1.services.ICourseService;
import live.learnlynx.api.v1.services.IEnrollmentService;
import live.learnlynx.api.v1.services.ITagService;
import live.learnlynx.api.v1.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final IUserService userService;
    private final ITagService tagService;
    private final ICourseRepository courseRepository;
    private final IEnrollmentService enrollmentService;
    @Override
    public Course createCourse(CreateCourseDTO dto, File courseImage, File courseIntroductoryVideo) {
        List<Tag> tags = dto.getTags().stream().map(tagId->this.tagService.getTagById(tagId)).collect(Collectors.toList());
        User courseInstructor = this.userService.getById(dto.getCourseInstructor());

        Course course = new Course(dto.getCourseTitle(),dto.getCourseDescription(),courseIntroductoryVideo,courseImage,dto.getCourseDuration(),dto.getCourseStartDate(),dto.getCourseEnrollmentFee(),dto.getCourseEnrollmentFeeCurrency(),tags,courseInstructor);
        return this.courseRepository.save(course);
    }

    @Override
    public Course updateCourse(UUID courseId, UpdateCourseDTO dto, File courseImage, File courseIntroductoryVideo) {
        List<Tag> tags = dto.getTags().stream().map(tagId->this.tagService.getTagById(tagId)).collect(Collectors.toList());
        User courseInstructor = this.userService.getById(dto.getCourseInstructor());
        Course course = this.courseRepository.getById(courseId);
        if(courseIntroductoryVideo != null){
            course.setCourseIntroductoryVideo(courseIntroductoryVideo);
        }
        if(courseImage != null){
            course.setCourseImage(courseImage);
        }
        course.setCourseInstructor(courseInstructor);
        course.setCourseTitle(dto.getCourseTitle());
        course.setCourseDuration(dto.getCourseDuration());
        course.setCourseStartDate(LocalDate.ofInstant(Instant.ofEpochMilli(dto.getCourseStartDate()), ZoneId.systemDefault()));
        course.setCourseTags(tags);
        course.setCourseEnrollmentFee(dto.getCourseEnrollmentFee());
        course.setCourseEnrollmentFeeCurrency(dto.getCourseEnrollmentFeeCurrency());

        return this.courseRepository.save(course);
    }

    @Override
    public String deleteCourse(UUID courseId) {
        this.courseRepository.deleteById(courseId);
        return "Course deleted Successfully";
    }

    @Override
    public List<Course> getCourses() {
        List<Course> courses = this.courseRepository.findAll();
        return courses;
    }

    @Override
    public Course getCourseById(UUID id) {
        return this.courseRepository.getById(id);
    }

    @Override
    public Course getCourseByInstructor(UUID instructorId) {
        User courseInstructor = this.userService.getById(instructorId);
        Course course = this.courseRepository.getCourseByInstructor(courseInstructor);
        return course;
    }

    @Override
    public List<User> getLearnersPursuingCourse(UUID courseId) {
        List<Enrollment> enrollments = this.enrollmentService.getEnrollmentsByCourseId(courseId);
        List<User> usersEnrolledInCourse = enrollments.stream().map(enrollment->enrollment.getUser()).collect(Collectors.toList());
        return usersEnrolledInCourse;
    }
}
