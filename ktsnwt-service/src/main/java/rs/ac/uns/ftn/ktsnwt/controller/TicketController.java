package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketDTO;
import rs.ac.uns.ftn.ktsnwt.service.tickets.TicketsService;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import org.springframework.security.access.prepost.PreAuthorize;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.TicketMapper;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketsService ticketsService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<TicketDTO>> getTickets(@RequestParam(name = "page") int page,
                                                      @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(TicketMapper.toListDto(ticketsService.findAll(page, size)), HttpStatus.OK);
    }

    @RequestMapping(value="/specific/{id}", method=RequestMethod.GET)
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id){
        Ticket ticket = ticketsService.findById(id);
        return new ResponseEntity<>(new TicketDTO(ticket), HttpStatus.OK);
    }

    @PostMapping("/locationDailyReport/{idLocation}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReportInfoDTO> onLocationDailyReport(@PathVariable long idLocation, @RequestBody String date){
        return new ResponseEntity<>(ticketsService.onLocationDailyReport(idLocation, date), HttpStatus.OK);
    }

    @PostMapping("/locationMonthlyReport/{idLocation}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReportInfoDTO> onLocationMonthlyReport(@PathVariable long idLocation, @RequestBody String date){
        return new ResponseEntity<>(ticketsService.onLocationMonthlyReport(idLocation, date), HttpStatus.OK);
    }

    @PostMapping("/eventDailyReport/{idEvent}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReportInfoDTO> onEventDailyReport(@PathVariable long idEvent, @RequestBody String date){
        return new ResponseEntity<>(ticketsService.onEventDailyReport(idEvent, date), HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<TicketDTO>> getUsersTickets(@RequestParam(name = "page") int page,
                                                           @RequestParam(name = "size") int size) {
        List<Ticket> tickets = ticketsService.getUsersTickets(page, size);
        return new ResponseEntity<>(TicketMapper.toListDto(tickets), HttpStatus.OK);
    }

    @PostMapping("/reserve")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity reserveTicket(@Valid @RequestBody TicketsToReserveDTO ticketsInfo) {
        ticketsService.reserveTickets(ticketsInfo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buy/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity buyTicket(@PathVariable Long id) {
        ticketsService.buyTicket(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity cancelTicket(@PathVariable Long id) {
        ticketsService.cancelTicket(id);
        return ResponseEntity.ok().build();
    }
}
