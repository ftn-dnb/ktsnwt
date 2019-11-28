package rs.ac.uns.ftn.ktsnwt.controller.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.controller.AddressControllerIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.controller.PricingControllerIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressControllerIntegrationTest.class,
        PricingControllerIntegrationTest.class
})
public class ControllerIntegrationTests {
}
