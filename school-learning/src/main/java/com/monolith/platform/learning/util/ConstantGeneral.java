package com.monolith.platform.learning.util;

public final class ConstantGeneral {

    private ConstantGeneral(){
    }

    public static final String ID = "id";
    public static final String SPACE = " ";
    public static final String ERROR_FIELD_NULL = "El valor de %s es %s.";
    public static final String FIELD_NOT_REQUIRED = "El valor %s de campo %s es no es requerido.";
    public static final String ERROR_OPEN_IA_CURRENT_QUOTA = "El saldo es insuficiente para utilizar OpenAI.";
    public static final String ERROR_OPEN_IA_API_KEY_INCORRECT = "La API key OpenAI es incorecta.";
    public static final String FALLBACK_MESSAGE = """
        Somos un equipo de profesionales de emprendedores, soñadores, amantes de la educación,
        intrépidos en el servicio de gestión de cursos académicos.

        Aspiramos a mejorar la vida de las personas aportando nuestro compromiso, eficiencia y calidad
        a nuestros clientes.

        ¡Si tú también sueñas, únete a nosotros!
        
        Creado Sin OpenAI
        
        """;
}
