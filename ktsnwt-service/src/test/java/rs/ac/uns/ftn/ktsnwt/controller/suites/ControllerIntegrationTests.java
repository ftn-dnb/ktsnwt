package rs.ac.uns.ftn.ktsnwt.controller.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.controller.*;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectorControllerIntegrationTest.class,
        AddressControllerIntegrationTest.class,
        PricingControllerIntegrationTest.class,
        TicketControllerIntegrationTest.class,
        HallControllerIntegrationTest.class,
        LocationControllerIntegrationTest.class,
        EventDayControllerIntegrationTest.class,
        EventControllerIntegrationTest.class
})
public class ControllerIntegrationTests {
}
