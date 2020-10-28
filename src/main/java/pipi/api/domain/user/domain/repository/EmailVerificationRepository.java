package pipi.api.domain.user.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.user.domain.EmailVerification;

@Repository
public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {
}
