package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.ktsnwt.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByGoogleApiId(String googleApiId);

}
