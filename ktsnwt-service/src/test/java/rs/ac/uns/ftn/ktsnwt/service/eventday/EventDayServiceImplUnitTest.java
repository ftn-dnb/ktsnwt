package rs.ac.uns.ftn.ktsnwt.service.eventday;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.EventDayConstants;
import rs.ac.uns.ftn.ktsnwt.exception.EventDayNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.repository.EventDayRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventDayServiceImplUnitTest {

    @Autowired
    private EventDayServiceImpl eventDayService;

    @MockBean
    private EventDayRepository eventDayRepository;

    @Test
    public void whenGetEventDayReturnEventDay() {
        EventDay eventDay = EventDayConstants.returnMockedEventDay();
        Mockito.when(eventDayRepository.findById(EventDayConstants.MOCK_ID)).thenReturn(Optional.of(eventDay));
        EventDay returnedEventDay = eventDayService.getEventDay(EventDayConstants.MOCK_ID);
        assertEquals(EventDayConstants.MOCK_ID, returnedEventDay.getId());
    }

    @Test(expected = EventDayNotFoundException.class)
    public void whenGetEventDayThrowNotFoundException() {
        Mockito.when(eventDayRepository.findById(EventDayConstants.MOCK_ID)).thenReturn(Optional.empty());
        EventDay eventDay = eventDayService.getEventDay(EventDayConstants.MOCK_ID);
    }
}
