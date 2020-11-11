package pipi.api.domain.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.project.domain.Calendar;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
