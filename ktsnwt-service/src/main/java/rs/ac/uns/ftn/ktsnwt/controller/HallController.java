package rs.ac.uns.ftn.ktsnwt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.service.hall.HallService;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    @Autowired
    private HallService hallService;

    @PostMapping("")
    public ResponseEntity<HallDTO> addHall(@RequestBody HallDTO hallDTO) {
        Hall hall = hallService.addHall(hallDTO.getLocationId(), hallDTO);
        return new ResponseEntity<>(new HallDTO(hall), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<HallDTO> editHall(@RequestBody HallDTO hallDTO) {
        Hall hall = hallService.editHall(hallDTO);
        return new ResponseEntity<>(new HallDTO(hall), HttpStatus.OK);
    }
}
