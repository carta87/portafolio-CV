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
        Tu objetivo es convencer al usuario del valor de la capacitación.
        """)
    @UserMessage("""
        Explica el contenido del curso y su impacto innovador.
        Genera un máximo de {{amount}} propuestas claras y concretas.
        """)
    String contentCourse(@V("amount") Long amount);
}
