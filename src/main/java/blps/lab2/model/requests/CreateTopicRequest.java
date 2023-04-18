package blps.lab2.model.requests;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateTopicRequest implements Serializable {
    private String title;
    private String description;
    private String content;
    private String category;
}
