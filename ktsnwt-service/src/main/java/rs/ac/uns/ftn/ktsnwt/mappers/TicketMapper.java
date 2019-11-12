package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.TicketDTO;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketMapper {

    public TicketMapper() {
    }

    public static List<TicketDTO> toTicketDTOs (List<Ticket> tickets){

        List<TicketDTO> ticketsDTO = new ArrayList<>();
        for (Ticket t : tickets) {
            ticketsDTO.add(new TicketDTO(t));
        }
        return ticketsDTO;
    }
}
