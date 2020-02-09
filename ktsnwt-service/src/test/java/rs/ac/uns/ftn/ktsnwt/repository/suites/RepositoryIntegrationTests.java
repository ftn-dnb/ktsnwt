package rs.ac.uns.ftn.ktsnwt.repository.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.repository.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectorRepositoryIntegrationTest.class,
        AddressRepositoryIntegrationTest.class,
        HallRepositoryIntegrationTest.class,
        LocationRepositoryIntegrationTest.class,
        EventRepositoryIntegrationTest.class,
        ConfirmationTokenRepositoryIntegrationTest.class,
        AuthorityRepositoryIntegrationTest.class
})
public class RepositoryIntegrationTests {
}
