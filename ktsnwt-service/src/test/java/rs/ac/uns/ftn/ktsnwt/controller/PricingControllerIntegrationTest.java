package rs.ac.uns.ftn.ktsnwt.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.PricingConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.PricingDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.repository.PricingRepository;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PricingRepository pricingRepository;

    private String accessToken;

    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }

    private HttpEntity<Object> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(headers);
    }

    @Test
    public void whenGetPricingsReturnList() {
        ResponseEntity<PricingDTO[]> response = restTemplate.exchange(
                "/api/pricings", HttpMethod.GET, createHttpEntity(), PricingDTO[].class);

        PricingDTO[] pricings = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(pricings);
        assertEquals(PricingConstants.DB_NUM_PRICINGS, pricings.length);
        assertEquals(PricingConstants.DB_ID_1, pricings[0].getId());
        assertEquals(PricingConstants.DB_ID_2, pricings[1].getId());
    }

    @Test
    public void whenFindByIdReturnPricing() {
        ResponseEntity<PricingDTO> response = restTemplate.exchange(
                "/api/pricings/" + PricingConstants.DB_ID_1, HttpMethod.GET, createHttpEntity(), PricingDTO.class);

        PricingDTO pricing = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(pricing);
        assertEquals(PricingConstants.DB_ID_1, pricing.getId());
    }

    @Test
    public void whenFindByIdThrowNotFound() {
        ResponseEntity<PricingDTO> response = restTemplate.exchange(
                "/api/pricings/" + PricingConstants.NON_EXISTING_DB_ID, HttpMethod.GET, createHttpEntity(), PricingDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
