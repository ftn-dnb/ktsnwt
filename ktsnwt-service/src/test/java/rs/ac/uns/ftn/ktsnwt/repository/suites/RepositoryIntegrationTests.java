package rs.ac.uns.ftn.ktsnwt.repository.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.repository.SectorRepositoryIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.repository.AddressRepositoryIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectorRepositoryIntegrationTest.class,
        AddressRepositoryIntegrationTest.class
})
public class RepositoryIntegrationTests {
}
