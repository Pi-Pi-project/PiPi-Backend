package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.user.domain.Report;
import pipi.api.domain.user.domain.ReportPK;

@Repository
public interface ReportRepository extends JpaRepository<Report, ReportPK> {
    Report findByReportedEmailAndReporterEmail(String reportedEmail, String reporterEmail);
    void deleteByReportedEmailAndReporterEmail(String reportedEmail, String reporterEmail);
}
