package com.monolith.platform.learning.domain.service.course;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AiServiceCourse {

    @UserMessage("""
                    Genrera un saludo de texto de maximo de 200 palabras
                    donde vas a animar a la capacitacion de formacion por
                    medio de cursos online sobe temas de desarrollo de software
                    en la plataforma con el nombre {{platform}}.
                    """)
    String welcomeCoursePlatform(String platform);

    @SystemMessage("""
        Eres un experto en formación académica y desarrollo de software.
        Tu misión es explicar el contenido del curso de forma clara,
        motivadora y profesional, resaltando su valor práctico.
        """)
    @UserMessage("""
        Explica el contenido del curso con la siguiente información:

        Número de curso: {{numberCourse}}

        Describe qué aprenderá el estudiante, 
        qué habilidades desarrollará y 
        por qué este curso es importante en su formación profesional.
        """)
    String contentCourse(
            @V("numberCourse") Long numberCourse
    );
}
