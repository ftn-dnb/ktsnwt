package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.EventDay;

public class EventDayConstants {

    private EventDayConstants() {}

    public static final Long MOCK_ID = 1L;

    public static EventDay returnMockedEventDay() {
        EventDay eventDay = new EventDay();
        eventDay.setId(EventDayConstants.MOCK_ID);
        return eventDay;
    }
}
