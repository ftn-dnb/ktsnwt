package rs.ac.uns.ftn.ktsnwt.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.SectorConstants;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SectorRepositoryIntegrationTest
{
    @Autowired
    SectorRepository sectorRepository;

    @Test
    public void whenFindByExistingName_thenReturnSector(){
        Sector s = sectorRepository.findByName(SectorConstants.ANOTHER_EXISTING_NAME,SectorConstants.EXISTING_DB_HALL_ID);
        assertEquals(SectorConstants.ANOTHER_EXISTING_NAME, s.getName());
        assertTrue(SectorConstants.EXISTING_DB_HALL_ID == s.getHall().getId());
        assertEquals(SectorConstants.ORIGINAL_CAPACITY,s.getCapacity());
        assertEquals(SectorConstants.ORIGINAL_COLUMNS, s.getNumColumns());
        assertEquals(SectorConstants.ORIGINAL_ROWS, s.getNumRows());
    }

    @Test
    public void whenFindByNotExistingName_thenReturnNull(){
        Sector s = sectorRepository.findByName(SectorConstants.NOT_EXISTING_NAME, SectorConstants.EXISTING_DB_HALL_ID);
        assertNull(s);
    }

    @Test
    public void whenFindByNotExistingHallId_thenReturnNull(){
        Sector s = sectorRepository.findByName(SectorConstants.EXISTING_NAME, SectorConstants.NOT_EXISTING_HALL_ID);
        assertNull(s);
    }


}
