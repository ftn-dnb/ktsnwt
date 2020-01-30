package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.TicketDTO;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

import java.util.List;
import java.util.stream.Collectors;

public class TicketMapper {

    private TicketMapper() {
    }

    public static TicketDTO toDto(Ticket ticket) {
        return new TicketDTO(ticket);
    }

    public static List<TicketDTO> toListDto(List<Ticket> tickets) {
        return tickets.stream().map(TicketDTO::new).collect(Collectors.toList());
    }
}
