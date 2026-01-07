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
@Service
@Slf4j
public class ReportEntityRepository implements IReportRepository {

    @Override
    public ResponseEntity<byte[]> reporteComprobante(StudentDTO studentDTO) {

        int numeroComprobante = 1246;

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(ConstantReport.DATE_HOUR);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ConstantReport.NUMBER_PROOF, String.valueOf(numeroComprobante));
        parameters.put(ConstantReport.DATE_PRO0F, formatter.format(date));
        parameters.put(ConstantReport.VALUE_PAID, new BigDecimal(30000));
        parameters.put(ConstantReport.HALF_PAYMENT, ConstantReport.CASH);
        parameters.put(ConstantReport.NAME_STUDENT,
                studentDTO.name() + " " + studentDTO.lastName());
        parameters.put(ConstantReport.NAME_ATTENDANT,
                studentDTO.attendant().name() + " " + studentDTO.attendant().lastName());

        // 📌 CLAVE: imágenes desde classpath
        parameters.put(
                ConstantReport.IMAGE_DIR,
                getClass().getResource(ConstantReport.IMAGE_PATH).toString()
        );

        try (var reportStream =
                     getClass().getResourceAsStream(ConstantReport.REPORT_JRXML_PATH)) {

            if (reportStream == null) {
                throw new RuntimeException("No se encontró el JRXML en classpath");
            }

            JasperReport report =
                    JasperCompileManager.compileReport(reportStream);

            JasperPrint print =
                    JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, outputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=" +
                            ConstantReport.NAME_REPORT +
                            "_" +
                            studentDTO.name() +
                            studentDTO.lastName() +
                            ConstantReport.ARCHIVE_EXTENSION_PDF
            );

            log.info(ConstantReport.REPORT_SUCCESSFUL);
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            log.error(ConstantReport.REPORT_FAILED, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}