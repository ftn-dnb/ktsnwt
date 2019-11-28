package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.EventDay;

public class EventDayConstants {

    private EventDayConstants() {}

    public static final Long DB_ID = 1L;

    public static EventDay returnMockedEventDay() {
        EventDay eventDay = new EventDay();
        eventDay.setId(EventDayConstants.DB_ID);
        return eventDay;
    }
}
