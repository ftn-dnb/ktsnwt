package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.SectorMapper;
import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.service.sector.SectorService;

import java.util.List;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<SectorDTO>> getAllSectors(@RequestParam(name = "page") int page,
                                                       @RequestParam(name = "size") int size,
                                                       @PathVariable Long id) {
        List<Sector> sectors = sectorService.findAllById(id, page, size);
        return new ResponseEntity<>(SectorMapper.toListDto(sectors), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<SectorDTO> addSector(@RequestBody SectorDTO sectorDTO) {
        Sector sector = sectorService.addSector(sectorDTO);
        return new ResponseEntity<>(SectorMapper.toDto(sector), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public ResponseEntity<SectorDTO> editSector(@RequestBody SectorDTO sectorDTO) {
        Sector sector = sectorService.editSector(sectorDTO);
        return new ResponseEntity<>(SectorMapper.toDto(sector), HttpStatus.OK);
    }
}
