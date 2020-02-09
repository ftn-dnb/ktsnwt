package rs.ac.uns.ftn.ktsnwt.service.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.service.email.MailSenderServiceIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.event.EventServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.hall.HallServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.eventday.EventDayServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.location.LocationServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.sector.SectorServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.address.AddressServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.pricing.PricingServiceImplIntegrationTest;
import rs.ac.uns.ftn.ktsnwt.service.tickets.TicketsServiceImplIntegrationTest;

import rs.ac.uns.ftn.ktsnwt.service.userDetails.CustomUserDetailsServiceIntegrationTest;

import rs.ac.uns.ftn.ktsnwt.service.user.UserServiceImplIntegrationTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        SectorServiceImplIntegrationTest.class,
        AddressServiceImplIntegrationTest.class,
        PricingServiceImplIntegrationTest.class,
        TicketsServiceImplIntegrationTest.class,
        HallServiceImplIntegrationTest.class,
        EventDayServiceImplIntegrationTest.class,
        LocationServiceImplIntegrationTest.class,
        EventServiceImplIntegrationTest.class,
        MailSenderServiceIntegrationTest.class,
        CustomUserDetailsServiceIntegrationTest.class,
        UserServiceImplIntegrationTest.class,
})
public class ServiceIntegrationTests {
}
