package app.user.model;

import app.story.model.Story;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Basic
    private String firstName;
    @Basic
    private String lastName;
    @Basic
    private String email;
    @Basic
    private String profilePictureUrl;
    @OneToMany(mappedBy = "addedBy")
    private List<Story> stories;

}
