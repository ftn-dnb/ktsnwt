package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventStatus;

import java.text.ParseException;

public class EventDayConstants {

    private EventDayConstants() {}

    public static final Long DB_ID = 1L;
    public static final String DB_DATE = "2019-11-29 21:58:58.0";
    public static final String DB_DESCRIPTION = "dan 1";
    public static final String DB_NAME = "DAY1";
    public static final Long DB_REFERENCED_EVENT_ID = 1L;

    public static final Long NON_EXISTING_DB_ID = 1234567L;

    public static final Long MOCK_ID = 1L;

    public static final Long MOCK_CANCELED_ID = 2L;
    public static final EventStatus MOCK_STATUS = EventStatus.CANCELED;

    public static final Long MOCK_INVALID_ID = 3L;

    public static final Long MOCK_VALID_ID = 4L;

    public static EventDay returnMockedEventDay() {
        EventDay eventDay = new EventDay();
        eventDay.setId(EventDayConstants.MOCK_ID);
        eventDay.setEvent(EventConstants.returnMockedEvent());
        return eventDay;
    }

    public static EventDay returnMockedCanceledEventDay() {
        EventDay eventDay = new EventDay();
        eventDay.setId(MOCK_CANCELED_ID);
        eventDay.setStatus(MOCK_STATUS);
        return eventDay;
    }

    public static EventDay returnMockedEventDayEventSeatsTooMuch() {
        EventDay eventDay = new EventDay();
        eventDay.setId(MOCK_INVALID_ID);
        eventDay.setEvent(EventConstants.returnTicketsPerUserEvent());
        return eventDay;
    }

    public static EventDay returnMockedEventDayEventPurchaseDate() throws ParseException {
        EventDay eventDay = new EventDay();
        eventDay.setId(MOCK_INVALID_ID);
        eventDay.setEvent(EventConstants.returnEventPurchaseLimit());
        return eventDay;
    }

    public static EventDay returnMockedEventDayValid() throws ParseException{
        EventDay eventDay = new EventDay();
        eventDay.setId(MOCK_VALID_ID);
        eventDay.setStatus(EventStatus.ACTIVE);
        eventDay.setEvent(EventConstants.returnEventReservationValid());
        return eventDay;
    }
}
