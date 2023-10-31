package live.learnlynx.api.v1.models;

import live.learnlynx.api.v1.audits.TimestampAudit;
import live.learnlynx.api.v1.enums.EEnrollmentCertificateStatus;
import live.learnlynx.api.v1.enums.EEnrollmentGrade;
import live.learnlynx.api.v1.enums.EEnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment extends TimestampAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "enrollment_status")
    private EEnrollmentStatus eEnrollmentStatus = EEnrollmentStatus.IN_PROGRESS;

    @Column(name="expected_completion_date")
    private LocalDate expectedCompletionDate;

    @Column(name = "grade")
    private EEnrollmentGrade eEnrollmentGrade;

    @Column(name = "enrollment_certificate_status")
    private EEnrollmentCertificateStatus enrollmentCertificateStatus = EEnrollmentCertificateStatus.NOT_ISSUED;

    @ManyToOne()
    @JoinColumn(name = "course_attended")
    private Course course;

    @ManyToOne()
    @JoinColumn()
    private User user;

    @Column(name = "fee_paid")
    private boolean isFeePaid=false;
}
