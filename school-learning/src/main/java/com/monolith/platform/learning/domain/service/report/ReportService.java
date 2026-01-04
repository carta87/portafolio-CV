package com.monolith.platform.learning.domain.service.report;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.domain.repository.report.IReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final IReportRepository iReportRepository;

    public ReportService(IReportRepository iReportRepository) {

        this.iReportRepository = iReportRepository;
    }

    public ResponseEntity<byte[]> reporteComprobante(StudentDTO studentDTO){
        return iReportRepository.reporteComprobante(studentDTO);
    }
}
