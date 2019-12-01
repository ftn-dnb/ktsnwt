package rs.ac.uns.ftn.ktsnwt.constants;


import rs.ac.uns.ftn.ktsnwt.model.Event;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EventConstants {

    private EventConstants() {}

    public static final Long MOCK_ID = 1L;

    public static Event returnMockedEvent() {
        Event event = new Event();
        event.setId(MOCK_ID);
        return event;
    }

    public static Event returnTicketsPerUserEvent() {
        Event event = new Event();
        event.setTicketsPerUser(2);
        return event;
    }

    public static Event returnEventPurchaseLimit() throws ParseException {
        Event event = new Event();
        event.setStartDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-26").getTime()));
        event.setPurchaseLimit(6);
        return event;
    }

    public static Event returnEventReservationValid() throws ParseException {
        Event event = new Event();
        event.setStartDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-05").getTime()));
        event.setPurchaseLimit(4);
        event.setTicketsPerUser(4);
        return event;
    }

}
