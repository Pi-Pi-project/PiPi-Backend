package pipi.api.domain.user.domain.repository;

import org.springframework.data.repository.CrudRepository;
import pipi.api.domain.user.domain.EmailVerification;

public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {
}
