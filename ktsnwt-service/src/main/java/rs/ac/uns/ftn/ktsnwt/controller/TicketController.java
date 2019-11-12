package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.TicketMapper;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.service.location.LocationService;
import rs.ac.uns.ftn.ktsnwt.service.tickets.TicketsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketsService ticketsService;

    @RequestMapping(value = "/{page_num}", method = RequestMethod.GET)
    public ResponseEntity<List<TicketDTO>> getTickets(@PathVariable int page_num) {
        return new ResponseEntity<>(TicketMapper.toTicketDTOs(ticketsService.findAll(page_num)), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id){
        Ticket ticket = ticketsService.findById(id);
        return new ResponseEntity<>(new TicketDTO(ticket), HttpStatus.OK);
    }


    @PostMapping("/locationDailyReport/{idLocation}")
    ResponseEntity<ReportInfoDTO> onLocationDailyReport(@PathVariable long idLocation, @RequestBody String date){
        return new ResponseEntity<>(ticketsService.onLocationDailyReport(idLocation, date), HttpStatus.OK);
    }


    @GetMapping("/locationMonthlyReport/{idLocation}")
    ResponseEntity<ReportInfoDTO> onLocationMonthlyReport(@PathVariable long idLocation){
        return new ResponseEntity<>(ticketsService.onLocationMonthlyReport(idLocation), HttpStatus.OK);
    }



    @PostMapping("/eventDailyReport/{idEvent}")
    ResponseEntity<ReportInfoDTO> onEventDailyReport(@PathVariable long idEvent, @RequestBody String date){
        return new ResponseEntity<>(ticketsService.onEventDailyReport(idEvent, date), HttpStatus.OK);

    }



}
