package toledotechevets.org.toledotech;

import org.junit.Test;


import org.techtoledo.dao.EventsDAO;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void EventsDAOTest(){
        System.out.println("Begin EventsDAOTest");
        EventsDAO eventsDAO = new EventsDAO();
        eventsDAO.getEventList();
    }
}