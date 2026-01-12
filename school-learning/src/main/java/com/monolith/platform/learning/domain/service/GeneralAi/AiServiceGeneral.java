package com.monolith.platform.learning.domain.service.GeneralAi;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AiServiceGeneral {

    @SystemMessage("""
        Eres un experto en comunicación educativa y marketing académico.
        Debes generar un mensaje inspirador, cercano y profesional,
        manteniendo la esencia del texto original.
        """)
    @UserMessage("""
        Basándote en la siguiente información institucional:

        {{baseMessage}}

        Genera un nuevo mensaje de bienvenida (máximo 200 palabras)
        para una plataforma de cursos online llamada {{platform}},
        manteniendo un tono motivador e inspirador.
        """)
    String welcomeCoursePlatform(
            @V("platform") String platform,
            @V("baseMessage") String baseMessage
    );
}
