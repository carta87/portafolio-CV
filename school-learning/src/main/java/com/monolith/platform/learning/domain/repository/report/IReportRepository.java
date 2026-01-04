package com.monolith.platform.learning.domain.repository.report;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import org.springframework.http.ResponseEntity;

public interface IReportRepository {
    ResponseEntity<byte[]> reporteComprobante( StudentDTO studentDTO);
}
