package com.monolith.platform.learning.persistence.entity.student;

import com.monolith.platform.learning.persistence.entity.AuditableEntity;
import com.monolith.platform.learning.persistence.entity.course.CourseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Getter
@Setter
@Entity
@Table(name = "students")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StudentEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "course_number",               // columna en students
            referencedColumnName = "number_course", // columna en courses
            nullable = false
    )
    private CourseEntity course;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attendant_id")
    private AttendantEntity attendant;
}
