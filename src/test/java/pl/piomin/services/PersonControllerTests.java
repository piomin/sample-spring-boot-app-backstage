package pl.piomin.services;

import org.instancio.Instancio;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.piomin.services.domain.Person;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTests {

    private static final String API_PATH = "/api/v1";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void add() {
        Person obj = restTemplate.postForObject(API_PATH, Instancio.create(Person.class), Person.class);
        assertNotNull(obj);
        assertEquals(1, obj.getId());
    }

    @Test
    @Order(2)
    void findAll() {
        Person[] objs = restTemplate.getForObject(API_PATH, Person[].class);
        assertTrue(objs.length > 0);
    }

    @Test
    @Order(2)
    void findById() {
        Person obj = restTemplate.getForObject(API_PATH + "/{id}", Person.class, 1L);
        assertNotNull(obj);
        assertEquals(1, obj.getId());
    }

    @Test
    @Order(3)
    void delete() {
        restTemplate.delete(API_PATH + "/{id}", 1L);
        Person obj = restTemplate.getForObject(API_PATH + "/{id}", Person.class, 1L);
        assertNull(obj.getId());
    }

}