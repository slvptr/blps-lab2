package blps.lab2.model.domain.user;

import blps.lab2.model.domain.topic.Topic;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Email
    private String email;
    private String password;
    private UserRole role;
    private Integer remainingGrades;
    private Date createdAt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Topic> topics;

    public User(String email, String password, UserRole role, Integer remainingGrades, Date createdAt) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.remainingGrades = remainingGrades;
        this.createdAt = createdAt;
    }

}