package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.SectorMapper;
import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.service.sector.SectorService;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @GetMapping("/{id}")
    public ResponseEntity<SectorDTO> getSectorById(@PathVariable Long id) {
        Sector sector = sectorService.findById(id);
        return new ResponseEntity<>(SectorMapper.toDto(sector), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SectorDTO> addSector(@RequestBody SectorDTO sectorDTO) {
        Sector sector = sectorService.addSector(sectorDTO);
        return new ResponseEntity<>(SectorMapper.toDto(sector), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<SectorDTO> editSector(@RequestBody SectorDTO sectorDTO) {
        Sector sector = sectorService.editSector(sectorDTO);
        return new ResponseEntity<>(SectorMapper.toDto(sector), HttpStatus.OK);
    }
}
