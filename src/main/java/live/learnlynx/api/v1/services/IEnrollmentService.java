package live.learnlynx.api.v1.services;

import live.learnlynx.api.v1.dtos.CreateEnrollmentDTO;
import live.learnlynx.api.v1.models.Enrollment;

import java.util.List;
import java.util.UUID;

public interface IEnrollmentService {

    public Enrollment createEnrollment(CreateEnrollmentDTO dto);
    public List<Enrollment> getAllEnrollments();
    public List<Enrollment> getAllEnrollmentsForLoggedInUser();
    public List<Enrollment> getAllEnrollmentsByUserId(UUID userId);
    public List<Enrollment> getEnrollmentsByCourseId(UUID courseId);

}
