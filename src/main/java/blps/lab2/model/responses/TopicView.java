package blps.lab2.model.responses;

import blps.lab2.model.domain.Topic;
import blps.lab2.model.domain.TopicCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TopicView {
    private long id;
    private String title;
    private String description;
    private TopicCategory category;
    private Date createdAt;
    private long rate;

    public static TopicView fromTopic(Topic topic) {
        return new TopicView(topic.getId(),
                topic.getTitle(),
                topic.getDescription(),
                topic.getCategory(),
                topic.getCreatedAt(),
                topic.getRate()
        );
    }
}
