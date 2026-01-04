package com.monolith.platform.learning.config;

import com.monolith.platform.learning.persistence.entity.course.CourseEntity;
import com.monolith.platform.learning.persistence.entity.student.AttendantEntity;
import com.monolith.platform.learning.persistence.entity.student.StudentEntity;
import com.monolith.platform.learning.persistence.repository.course.ICursoRepository;
import com.monolith.platform.learning.persistence.repository.student.IEstudianteEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class ChargeInitialData implements CommandLineRunner {

    private final ICursoRepository cursoRepository;
    private final IEstudianteEntityRepository estudianteRepository;

    @Override
    public void run(String... args) {

        /* =========================
           1. CARGAR CURSOS
           ========================= */
        if (cursoRepository.count() == 0) {

            CourseEntity matematicas = CourseEntity.builder()
                    .numberCourse(101L)
                    .name("Matematicas")
                    .teacher("Francisco Paez")
                    .build();

            CourseEntity espanol = CourseEntity.builder()
                    .numberCourse(102L)
                    .name("Español")
                    .teacher("Martha Suares")
                    .build();

            cursoRepository.saveAll(List.of(matematicas, espanol));
        }

        /* =========================
           2. CARGAR ESTUDIANTES
           ========================= */
        if (estudianteRepository.count() == 0) {

            CourseEntity matematicas = cursoRepository
                    .findByNumberCourse(101L)
                    .orElseThrow();

            CourseEntity espanol = cursoRepository
                    .findByNumberCourse(102L)
                    .orElseThrow();

            StudentEntity student1 = StudentEntity.builder()
                    .name("Sara")
                    .lastName("Perez")
                    .email("sara@gmail.com")
                    .course(matematicas)
                    .attendant(AttendantEntity.builder()
                            .name("Pedro")
                            .lastName("Perez")
                            .email("pedro@gmail.com")
                            .build())
                    .build();

            StudentEntity student2 = StudentEntity.builder()
                    .name("Juliana")
                    .lastName("Arenas")
                    .email("juliana@gmail.com")
                    .course(espanol)
                    .attendant(AttendantEntity.builder()
                            .name("Cindy")
                            .lastName("Arenas")
                            .email("cindy@gmail.com")
                            .build())
                    .build();

            StudentEntity student3 = StudentEntity.builder()
                    .name("Andres")
                    .lastName("Castro")
                    .email("andres@gmail.com")
                    .course(espanol)
                    .attendant(AttendantEntity.builder()
                            .name("Ismael")
                            .lastName("Castro")
                            .email("ismael@gmail.com")
                            .build())
                    .build();

            estudianteRepository.saveAll(List.of(student1, student2, student3));
        }
    }
}
