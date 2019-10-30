package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.ktsnwt.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByToken(String token);
}
