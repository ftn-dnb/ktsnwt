package rs.ac.uns.ftn.ktsnwt.service.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import rs.ac.uns.ftn.ktsnwt.service.address.AddressServiceImplUnitTest;
import rs.ac.uns.ftn.ktsnwt.service.pricing.PricingServiceImplUnitTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressServiceImplUnitTest.class,
        PricingServiceImplUnitTest.class
})
public class ServiceUnitTests {
}
