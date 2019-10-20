package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.ktsnwt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
