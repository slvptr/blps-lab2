package blps.lab2.model.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String login;
    private long remainingLikes;

    public User(String login, long remainingLikes){
        this.login = login;
        this.remainingLikes = remainingLikes;
    }
}
