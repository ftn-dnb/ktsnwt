package rs.ac.uns.ftn.ktsnwt.constants;


import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EventConstants {

    private EventConstants() {}

    public static final Long MOCK_ID = 1L;

    public static final String NEW_DB_NAME = "New Concert";
    public static final EventType NEW_DB_TYPE = EventType.CONCERT;
    public static final int NEW_DB_TICKETS_PER_USER = 2;
    public static final int NEW_DB_PURCHASE_LIMIT = 2;
    public static final String NEW_DB_DESCRIPTION = "This is description for new concert.";
    public static final String NEW_DB_STARTDATE = "03-03-2019";
    public static final String NEW_DB_ENDDATE = "04-03-2019";
    public static final Long NEW_DB_HALL_ID = 1L;

    public static final Long NON_EXISTING_DB_ID = 1234567L;

    public static final int DB_SIZE = 2;

    public static final Long DB_1_ID = 1L;
    public static final String DB_1_NAME = "Koncert";
    public static final String DB_1_DESCRIPTION = "Ovo je event";
    public static final int DB_1_TICKETS_PER_USER = 2;
    public static final int DB_1_PURCHASE_LIMIT = 2;

    public static final Long DB_2_ID = 2L;

    public static Event returnMockedEvent() {
        Event event = new Event();
        event.setId(MOCK_ID);
        return event;
    }

    public static Event returnNewEvent() {
        Event event = new Event();
        event.setName(NEW_DB_NAME);
        event.setType(NEW_DB_TYPE);
        event.setTicketsPerUser(NEW_DB_TICKETS_PER_USER);
        event.setPurchaseLimit(NEW_DB_PURCHASE_LIMIT);
        event.setDescription(NEW_DB_DESCRIPTION);
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
