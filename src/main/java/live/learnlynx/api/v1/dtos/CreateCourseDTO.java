package live.learnlynx.api.v1.dtos;

import live.learnlynx.api.v1.enums.ECurrency;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
public class CreateCourseDTO {

    private String courseTitle;
    private String courseDescription;
    private int courseDuration;
    private int courseStartDate;
    private UUID courseInstructor;
    private List<UUID> tags;
    private int courseEnrollmentFee;
    private ECurrency courseEnrollmentFeeCurrency;


}
