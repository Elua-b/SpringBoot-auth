package live.learnlynx.api.v1.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class DeleteLessonMultimediaDTO {

    private UUID lessonId;
    private UUID fileId;

}
