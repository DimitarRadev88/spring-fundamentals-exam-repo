package app.story.model;

import app.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Stories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 25)
    private String title;
    @Column(nullable = false, length = 1000)
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Kind kind;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalDate addedOn;
    @ManyToOne
    private User addedBy;
    @Basic
    private Boolean isVisible;

}
