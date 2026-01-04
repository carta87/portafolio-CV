package com.monolith.platform.learning.util;

import java.io.File;

public final class ConstantReport {
    private ConstantReport(){
    }

    public static final String PATH_DESTINACION_PRINCIPAL =
            "src" + File.separator +
            "main"+ File.separator +
            "resources"+ File.separator +
            "static" + File.separator ;

    public static final String PATH_ORIGIN_PRINCIPAL =
            "src" + File.separator +
            "main"+ File.separator +
            "resources"+ File.separator +
            "templates" + File.separator +
            "report" + File.separator;
    public static final String CASH = "Efectivo";
    public static final String NAME_REPORT = "comprobantePago";
    public static final String DATE_HOUR = "dd-MM-yyyy HH:mm:ss";

    public static final String NUMBER_PROOF = "numCombante";

    public static final String DATE_PRO0F = "fechaComprobante";
    public static final String VALUE_PAID = "valorPagado";

    public static final String HALF_PAYMENT = "medioPago";

    public static final String NAME_STUDENT = "nomAlumno";

    public static final String NAME_ATTENDANT = "nomAcudiente";

    public static final String IMAGE_DIR = "imageDir";

    public static final String PATH_IMAGE = "classpath:/static/images/";

    public static final String ARCHIVE_EXTENSION_PDF = ".pdf";
    public static final String ARCHIVE_EXTENSION_JRXML = ".jrxml";

    public static final String REPORT_SUCCESSFUL = "Creacion de reporte exitoso";
    public static final String REPORT_FAILED = "Creacion de reporte fallida";
}
