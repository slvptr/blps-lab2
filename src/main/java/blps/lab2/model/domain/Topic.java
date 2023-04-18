package blps.lab2.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String content;
    private TopicCategory category;
    private Date createdAt;
    private Date updatedAt;


    public Topic(String title, String description, String content, TopicCategory category,
                 Date createdAt, Date updatedAt) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}