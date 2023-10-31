package live.learnlynx.api.v1.serviceImpls;

import live.learnlynx.api.v1.dtos.CreateEnrollmentDTO;
import live.learnlynx.api.v1.enums.EEnrollmentStatus;
import live.learnlynx.api.v1.models.Course;
import live.learnlynx.api.v1.models.Enrollment;
import live.learnlynx.api.v1.models.User;
import live.learnlynx.api.v1.repositories.ICourseRepository;
import live.learnlynx.api.v1.repositories.IEnrollmentRepository;
import live.learnlynx.api.v1.services.IEnrollmentService;
import live.learnlynx.api.v1.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements IEnrollmentService {

    private final IEnrollmentRepository enrollmentRepository;
    private final ICourseRepository courseRepository;
    private final IUserService userService;

    @Override
    public Enrollment createEnrollment(CreateEnrollmentDTO dto) {
        Enrollment enrollment = new Enrollment();
        Course course = this.courseRepository.getById(dto.getCourseId());
        enrollment.setCourse(course);
        enrollment.setFeePaid(true);
        enrollment.setEEnrollmentStatus(EEnrollmentStatus.IN_PROGRESS);
        this.enrollmentRepository.save(enrollment);
        return enrollment;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return this.enrollmentRepository.findAll().subList(0, 100);
    }

    @Override
    public List<Enrollment> getAllEnrollmentsForLoggedInUser() {
        User user = this.userService.getLoggedInUser();
        return this.enrollmentRepository.getEnrollmentsByUser(user);
    }

    @Override
    public List<Enrollment> getAllEnrollmentsByUserId(UUID userId) {
        User user = this.userService.getById(userId);
        return this.enrollmentRepository.getEnrollmentsByUser(user);
    }

    @Override
    public List<Enrollment> getEnrollmentsByCourseId(UUID courseId) {
        Course course = this.courseRepository.getById(courseId);
        List<Enrollment> enrollments = this.enrollmentRepository.getEnrollmentsByCourse(course);
        return enrollments;
    }
}
