package rs.ac.uns.ftn.ktsnwt.service.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.service.address.AddressServiceImplIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressServiceImplIntegrationTest.class
})
public class ServiceIntegrationTests {
}
