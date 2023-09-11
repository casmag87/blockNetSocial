package serviceTest;

import DTO.PostDTO;
import entities.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import service.UserFacade;

import static org.junit.jupiter.api.Assertions.*;

public class PostServiceTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;

    @BeforeAll
    public static void beforeAll(){
        emf = Persistence.createEntityManagerFactory("puTest");
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass(){

    }

    @BeforeEach
    public void setUP(){
    }

    @AfterEach
    public void tearDown(){
    }

    @Test
    public void testGetFacade() {

        assertNotNull(UserFacade.getUserFacade(emf));
        assertSame(UserFacade.getUserFacade(emf), UserFacade.getUserFacade(emf));
    }




}
