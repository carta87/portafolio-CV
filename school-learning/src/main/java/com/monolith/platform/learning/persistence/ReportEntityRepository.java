package com.monolith.platform.learning.persistence;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.domain.repository.report.IReportRepository;
import com.monolith.platform.learning.util.ConstantGeneral;
import com.monolith.platform.learning.util.ConstantReport;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ReportEntityRepository implements IReportRepository {

    @Override
    public ResponseEntity<byte[]> reporteComprobante(StudentDTO studentDTO) {
        int numeroComprobante = 1245;
        numeroComprobante++;

        String nombreArchivo = ConstantReport.NAME_REPORT;

        String filePath =
                ConstantReport.PATH_ORIGIN_PRINCIPAL
                        + nombreArchivo+ ConstantReport.ARCHIVE_EXTENSION_JRXML;

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantReport.DATE_HOUR);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ConstantReport.NUMBER_PROOF, String.valueOf(numeroComprobante));
        parameters.put(ConstantReport.DATE_PRO0F, formatter.format(date));
        parameters.put(ConstantReport.VALUE_PAID, new BigDecimal(30000));
        parameters.put(ConstantReport.HALF_PAYMENT, ConstantReport.CASH);
        parameters.put(ConstantReport.NAME_STUDENT, studentDTO.name() + ConstantGeneral.SPACE + studentDTO.lastName());
        parameters.put(ConstantReport.NAME_ATTENDANT, studentDTO.attendant().name() + ConstantGeneral.SPACE + studentDTO.attendant().lastName());
        parameters.put(ConstantReport.IMAGE_DIR, ConstantReport.PATH_IMAGE);

        /*try {
            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint print =  print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(print, destinationPath);
            return ConstantesReportes.REPORTE_EXITOSO;
        } catch (JRException e) {
            return ConstantesReportes.REPORTE_FALLIDO;
        }*/
        try {
            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, byteArrayOutputStream);
            byte[] pdfBytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=" +
                    nombreArchivo+studentDTO.name() + studentDTO.lastName() + ConstantReport.ARCHIVE_EXTENSION_PDF);

            log.info(ConstantReport.REPORT_SUCCESSFUL);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (JRException e) {
            log.error(ConstantReport.REPORT_FAILED, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
