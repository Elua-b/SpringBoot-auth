package live.learnlynx.api.v1.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue()
    private UUID id;

    @Column(name = "question_name")
    private String questionName;

    @OneToMany
    @JoinColumn(name = "answers")
    private List<Answer> answers;

    @OneToOne
    @JoinColumn(name = "valid_answer")
    private Answer validAnswer;
}
