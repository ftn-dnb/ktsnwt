package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketDTO;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.service.location.LocationService;
import rs.ac.uns.ftn.ktsnwt.service.ticket.TicketService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/{page_num}", method = RequestMethod.GET)
    public ResponseEntity<List<TicketDTO>> getTickets(@PathVariable int page_num) {
        List<Ticket> tickets = ticketService.findAll(page_num);

        List<TicketDTO> ticketsDTO = new ArrayList<>();
        for (Ticket t : tickets) {
            ticketsDTO.add(new TicketDTO(t));
        }
        return new ResponseEntity<>(ticketsDTO, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id){
        Ticket ticket = ticketService.findById(id);
        if(ticket == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new TicketDTO(ticket), HttpStatus.OK);
    }


    @PostMapping("/locationDailyReport/{idLocation}")
    @ResponseBody
    ResponseEntity<?> onLocationDailyReport(@PathVariable long idLocation, @RequestBody String date){
        try {
            return new ResponseEntity<ReportInfoDTO>(ticketService.onLocationDailyReport(idLocation, date), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/locationMonthlyReport/{idLocation}")
    @ResponseBody
    ResponseEntity<?> onLocationMonthlyReport(@PathVariable long idLocation){
        try {
            return new ResponseEntity<ReportInfoDTO>(ticketService.onLocationMonthlyReport(idLocation), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/eventDailyReport/{idEvent}")
    @ResponseBody
    ResponseEntity<?> onEventDailyReport(@PathVariable long idEvent, @RequestBody String date){
        try {
            return new ResponseEntity<ReportInfoDTO>(ticketService.onEventDailyReport(idEvent, date), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
