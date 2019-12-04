package rs.ac.uns.ftn.ktsnwt.controller.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.controller.SectorControllerIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.controller.AddressControllerIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.controller.PricingControllerIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.controller.TicketControllerIntegrationTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectorControllerIntegrationTest.class,
        AddressControllerIntegrationTest.class,
        PricingControllerIntegrationTest.class,
        TicketControllerIntegrationTest.class
})
public class ControllerIntegrationTests {
}
