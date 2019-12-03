package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.dto.PricingSeatDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TicketConstants {

    private TicketConstants() {}

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static final Long MOCK_ID_1 = 1L;
    public static final String MOCK_DATE_PURCHASED_1 = "2019-11-28";

    public static final Long MOCK_ID_2 = 2L;
    public static final String MOCK_DATE_PURCHASED_2 = "2019-11-28";


    public static final Long DB_ID_1 = 1L;
    public static final int DB_COLUMN_NUM_1 = 2;
    public static final String DB_DATE_1 = "2019-11-20 22:30:22.0";
    public static final boolean DB_PURCHASED_1 = true;
    public static final int DB_ROW_NUM_1 = 4;
    public static final Long DB_EVENT_DAY_ID_1 = 1L;
    public static final Long DB_PRICING_ID_1 = 1L;
    public static final Long DB_USER_ID_1 = 1L;

    public static final Long DB_ID_2 = 2L;
    public static final int DB_COLUMN_NUM_2 = 3;
    public static final String DB_DATE_2 = "2019-11-20 21:30:22";
    public static final boolean DB_PURCHASED_2 = true;
    public static final int DB_ROW_NUM_2 = 4;
    public static final Long DB_EVENT_DAY_ID_2 = 1L;
    public static final Long DB_PRICING_ID_2 = 1L;
    public static final Long DB_USER_ID_2 = 1L;

    public static final int NEW_DB_COLUMN_NUM = 3;
    public static final String NEW_DB_DATE = "2019-12-09 21:30:22.0";
    public static final boolean NEW_DB_PURCHASED = false;
    public static final int NEW_DB_ROW_NUM = 4;
    public static final Long NEW_DB_EVENT_DAY_ID = 3L;
    public static final Long NEW_DB_PRICING_ID = 3L;
    public static final Long NEW_DB_USER_ID = 1L;

    public static final Long NON_EXISTING_ID = 88L;

    public static final Long DELETE_TICKET_ID = 3L;

    public static TicketsToReserveDTO returnTicketDTO() {
        TicketsToReserveDTO dto = new TicketsToReserveDTO();
        dto.setEventDayId(NEW_DB_EVENT_DAY_ID);

        PricingSeatDTO seatDTO = new PricingSeatDTO();
        seatDTO.setSeat(NEW_DB_COLUMN_NUM);
        seatDTO.setRow(NEW_DB_ROW_NUM);
        seatDTO.setPricingId(NEW_DB_PRICING_ID);

        ArrayList<PricingSeatDTO> list = new ArrayList<>();
        list.add(seatDTO);

        dto.setSeats(list);

        return dto;

    }

    public static final int MOCK_ROW_1 = 4;
    public static final int MOCK_ROW_2 = 4;
    public static final int MOCK_SEAT_1 = 2;
    public static final int MOCK_SEAT_2 = 3;

    public static final Long MOCK_ID_TICKET_OTHER = 3L;



    public static List<Ticket> returnMockedTickets() throws ParseException {
        Ticket t1 = new Ticket();
        t1.setId(TicketConstants.MOCK_ID_1);
        t1.setDatePurchased(new Timestamp(formatter.parse(TicketConstants.MOCK_DATE_PURCHASED_1).getTime()));

        Ticket t2 = new Ticket();
        t2.setId(TicketConstants.MOCK_ID_2);
        t2.setDatePurchased(new Timestamp(formatter.parse(TicketConstants.MOCK_DATE_PURCHASED_2).getTime()));

        Pricing mockedPricing = PricingConstants.returnMockedPricing();

        t1.setPricing(mockedPricing);
        t2.setPricing(mockedPricing);

        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(t1);
        tickets.add(t2);

        return tickets;

    }

    public static TicketsToReserveDTO returnTicketCanceledEvent() {
        TicketsToReserveDTO dto = new TicketsToReserveDTO();
        dto.setEventDayId(2L);
        return dto;
    }

    public static TicketsToReserveDTO returnTicketMuchSeats() {
        TicketsToReserveDTO dto = new TicketsToReserveDTO();
        PricingSeatDTO pricingDTO1 = new PricingSeatDTO();
        PricingSeatDTO pricingDTO2 = new PricingSeatDTO();
        PricingSeatDTO pricingDTO3 = new PricingSeatDTO();
        PricingSeatDTO pricingDTO4 = new PricingSeatDTO();
        ArrayList<PricingSeatDTO> list = new ArrayList<>();
        list.add(pricingDTO1);
        list.add(pricingDTO2);
        list.add(pricingDTO3);
        list.add(pricingDTO4);
        dto.setSeats(list);
        dto.setEventDayId(3L);
        return dto;
    }

    public static TicketsToReserveDTO returnTicketPurchaseDay() {
        TicketsToReserveDTO dto = new TicketsToReserveDTO();
        dto.setEventDayId(3L);
        return dto;
    }

    public static TicketsToReserveDTO returnValidTicketDTO() {
        TicketsToReserveDTO dto = new TicketsToReserveDTO();
        dto.setEventDayId(4L);

        ArrayList<PricingSeatDTO> list = new ArrayList<>();
        PricingSeatDTO seat1 = new PricingSeatDTO();
        seat1.setPricingId(4L);
        seat1.setRow(MOCK_ROW_1);
        seat1.setSeat(MOCK_SEAT_1);
        list.add(seat1);

        PricingSeatDTO seat2 = new PricingSeatDTO();
        seat2.setPricingId(4L);
        seat2.setRow(MOCK_ROW_2);
        seat2.setSeat(MOCK_SEAT_2);
        list.add(seat2);

        dto.setSeats(list);

        return dto;
    }

    public static Ticket returnTicketWithLoggedOffUser() {
        Ticket ticket = new Ticket();
        ticket.setId(MOCK_ID_TICKET_OTHER);
        ticket.setUser(UserConstants.returnTicketUser());
        return ticket;
    }

    public static Ticket returnPurchasedTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(MOCK_ID_1);
        ticket.setPurchased(true);
        return ticket;
    }

    public static Ticket returnCancelTicketPurchaseLimit() throws ParseException{
        Ticket ticket = new Ticket();
        ticket.setId(MOCK_ID_1);
        ticket.setPurchased(false);
        ticket.setEventDay(EventDayConstants.returnMockedEventDayEventPurchaseDate());
        return ticket;
    }
}
