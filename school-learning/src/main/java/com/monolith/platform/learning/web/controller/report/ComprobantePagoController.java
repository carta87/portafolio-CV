package com.monolith.platform.learning.web.controller.report;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.domain.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/reports")
public class ComprobantePagoController {

    private final ReportService reportService;

    @PostMapping(path = "/comprobantePago")
    public ResponseEntity<byte[]> reporteComprobante(@RequestBody StudentDTO studentDTO) {
       return reportService.reporteComprobante(studentDTO);
    }
}
