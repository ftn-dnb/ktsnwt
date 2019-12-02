package rs.ac.uns.ftn.ktsnwt.service.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.service.address.AddressServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.pricing.PricingServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.tickets.TicketsServiceImplIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressServiceImplIntegrationTest.class,
        PricingServiceImplIntegrationTest.class,
        TicketsServiceImplIntegrationTest.class
})
public class ServiceIntegrationTests {
}
