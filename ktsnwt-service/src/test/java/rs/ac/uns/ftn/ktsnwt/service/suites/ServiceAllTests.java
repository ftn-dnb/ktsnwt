package rs.ac.uns.ftn.ktsnwt.service.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ServiceIntegrationTests.class,
        ServiceUnitTests.class
})
public class ServiceAllTests {
}
