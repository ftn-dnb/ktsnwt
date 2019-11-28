package rs.ac.uns.ftn.ktsnwt.constants;

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

}
