package rs.ac.uns.ftn.ktsnwt.service.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.repository.SectorRepositoryIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.sector.SectorServiceImplIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectorServiceImplIntegrationTest.class
})
public class ServiceIntegrationTests {
}
