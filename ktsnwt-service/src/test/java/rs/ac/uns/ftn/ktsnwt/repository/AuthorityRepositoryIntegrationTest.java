package rs.ac.uns.ftn.ktsnwt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.model.Authority;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorityRepositoryIntegrationTest {

    @Autowired AuthorityRepository authorityRepository;

    @Test
    public void findByNameUser(){
        Long id = 1l;
        String name = "ROLE_USER";

        Authority a = authorityRepository.findByName("name");
        assertEquals(id, a.getId());
        assertEquals(name, a.getName());
    }

    @Test
    public void findByNameAdmin(){
        Long id = 2l;
        String name = "ROLE_ADMIN";

        Authority a = authorityRepository.findByName(name);
        assertEquals(id, a.getId());
        assertEquals(name, a.getName());
    }

    @Test
    public void findByNameFail(){
        Long id = 22l;
        String name = "failName";

        Authority a = authorityRepository.findByName(name);
        assertEquals(null, a);
    }


}
