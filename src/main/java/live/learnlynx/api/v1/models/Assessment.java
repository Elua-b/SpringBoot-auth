package live.learnlynx.api.v1.models;


import live.learnlynx.api.v1.audits.TimestampAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assessments")
public class Assessment extends TimestampAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "assessment_name")
    private String assessmentName;

    @Column(name = "assessment_description")
    private String assessmentDescription;

    @OneToMany
    @JoinColumn(name = "assessment_questions")
    private List<Question> assessmentQuestions;

    @ManyToOne
    @JoinColumn(name = "marked_by")
    private User markedBy;


}
