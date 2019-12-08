package rs.ac.uns.ftn.ktsnwt.service.eventday;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.EventDayConstants;
import rs.ac.uns.ftn.ktsnwt.exception.EventDayNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventDayServiceImplIntegrationTest {

    @Autowired
    private EventDayServiceImpl eventDayService;

    @Test
    public void whenGetEventDayReturnEventDay() {
        EventDay eventDay = eventDayService.getEventDay(EventDayConstants.DB_ID);
        assertEquals(EventDayConstants.DB_ID, eventDay.getId());
        assertEquals(EventDayConstants.DB_DATE, eventDay.getDate().toString());
        assertEquals(EventDayConstants.DB_DESCRIPTION, eventDay.getDescription());
        assertEquals(EventDayConstants.DB_NAME, eventDay.getName());
        assertEquals(EventDayConstants.DB_REFERENCED_EVENT_ID, eventDay.getEvent().getId());
    }

    @Test(expected = EventDayNotFoundException.class)
    public void whenGetEventDayThrowNotFoundException() {
        EventDay eventDay = eventDayService.getEventDay(EventDayConstants.NON_EXISTING_DB_ID);
    }
}
