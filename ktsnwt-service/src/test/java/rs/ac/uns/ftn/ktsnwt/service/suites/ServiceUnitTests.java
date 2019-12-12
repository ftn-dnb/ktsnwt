package rs.ac.uns.ftn.ktsnwt.service.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.service.hall.HallServiceImplUnitTest;
import rs.ac.uns.ftn.ktsnwt.service.sector.SectorServiceImplUnitTest;
import rs.ac.uns.ftn.ktsnwt.service.address.AddressServiceImplUnitTest;
import rs.ac.uns.ftn.ktsnwt.service.pricing.PricingServiceImplUnitTest;
import rs.ac.uns.ftn.ktsnwt.service.tickets.TicketsServiceUnitTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectorServiceImplUnitTest.class,
        AddressServiceImplUnitTest.class,
        PricingServiceImplUnitTest.class,
        TicketsServiceUnitTest.class,
        HallServiceImplUnitTest.class
})
public class ServiceUnitTests {
}
