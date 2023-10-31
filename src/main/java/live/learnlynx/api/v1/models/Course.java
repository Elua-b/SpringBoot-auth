package live.learnlynx.api.v1.models;

import live.learnlynx.api.v1.enums.ECourseStatus;
import live.learnlynx.api.v1.enums.ECurrency;
import live.learnlynx.api.v1.fileHandling.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "course_description")
    private String courseDescription;

    @OneToOne
    @JoinColumn(name="course_introductory_video")
    private File courseIntroductoryVideo;

    @Column(name = "course_duration")
    private int courseDuration;

    @Column(name = "course_start_date")
    private LocalDate courseStartDate;

    @Column(name = "course_end_date")
    private LocalDate courseEndDate;

    @ManyToOne
    @JoinColumn(name="course_instructor")
    private User courseInstructor;

    @ManyToMany
    @JoinColumn(name = "course_tags")
    private List<Tag> courseTags;

    @OneToOne
    @JoinColumn(name="course_image")
    private File courseImage;

    @Enumerated(EnumType.STRING)
    private ECourseStatus courseStatus=ECourseStatus.DRAFT;

    @Column(name = "course_enrollment_fee")
    private int courseEnrollmentFee;

    @Column(name = "course_enrollment_fee_currency")
    private ECurrency courseEnrollmentFeeCurrency=ECurrency.RWF;

    @OneToMany
    @JoinColumn(name="lessons_in_course")
    private List<Lesson> lessonsInCourse;

    public Course(String courseTitle,String courseDescription, File courseIntroductoryVideo,File courseImage,int courseDuration,int courseStartDate, int courseEnrollmentFee, ECurrency courseEnrollmentFeeCurrency,List<Tag> tags,User courseInstructor){
         this.courseTitle=courseTitle;
         this.courseStartDate= LocalDate.ofInstant(Instant.ofEpochMilli(courseStartDate), ZoneId.systemDefault());
         this.courseDescription=courseDescription;
         this.courseIntroductoryVideo=courseIntroductoryVideo;
         this.courseImage=courseImage;
         this.courseDuration=courseDuration;
         this.courseEnrollmentFee=courseEnrollmentFee;
         this.courseEnrollmentFeeCurrency=courseEnrollmentFeeCurrency;
         this.courseTags=tags;
         this.courseInstructor=courseInstructor;
    }

}
