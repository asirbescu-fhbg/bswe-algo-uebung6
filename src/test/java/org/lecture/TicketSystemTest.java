package org.lecture;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lecture.enums.Event;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketSystemTest {
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(TestHelper.outputStreamCaptor));
    }

    @AfterEach
    public void afterEach() {
        System.setIn(TestHelper.backupSysIn);
        System.setOut(TestHelper.backupSysOut);
        TestHelper.outputStreamCaptor.reset(); // clear the outputstream
    }

    @Test
    public void generalFunctionalityTest() throws InterruptedException {
        TicketSystemService ticketSystemService = new TicketSystemService("src/test/resources/testReservations.txt");
        ticketSystemService.startApplication();
    }

    @Test
    public void reserveTicketTest() {
        TicketSystem.initialize();
        TicketSystem.reserveTicket(Event.Concert, 5);
        TicketSystem.printHistory();

        String output = TestHelper.outputStreamCaptor.toString();

        assertTrue(output.contains("Benutzer: main\n" +
                "Reserviert 5 Tickets für das Event 'Concert' um 20:30 Uhr."));
    }

    @Test
    public void checkTicketsAvailableTest() {
        TicketSystem.initialize();
        assertTrue(TicketSystem.ticketsAvailable(Event.Sport, 40, "Max"));
        assertFalse(TicketSystem.ticketsAvailable(Event.Sport, 60, "Max"));
    }

    @Test
    public void cancelTicketTest() {
        TicketSystem.initialize();
        TicketSystem.reserveTicket(Event.Concert, 5);
        TicketSystem.cancelTicket(Event.Concert, 3, "main");
        TicketSystem.printHistory();

        String output = TestHelper.outputStreamCaptor.toString();

        assertTrue(output.contains("Benutzer: main\n" +
                "Reserviert 5 Tickets für das Event 'Concert' um 20:30 Uhr."));

        assertTrue(output.contains("Benutzer: main\n" +
                "Storniert 3 Tickets für das Event 'Concert' um 20:30 Uhr."));
    }

    @Test
    public void rateTest() {
        TicketSystem.initialize();
        TicketSystem.reserveTicket(Event.Concert, 5);
        TicketSystem.rate(Event.Concert, 4);
        TicketSystem.printHistory();

        String output = TestHelper.outputStreamCaptor.toString();

        assertTrue(output.contains("Benutzer: main\n" +
                "Reserviert 5 Tickets für das Event 'Concert' um 20:30 Uhr."));

        assertTrue(output.contains("Benutzer: main\n" +
                "Bewertet das Event 'Concert' um 20:30 Uhr mit 4 Sternen."));

        assertTrue(output.contains("Event: Concert\n" +
                "Hat eine durchschnittliche Bewertung von 4.0 Sternen."));
    }

    @Test
    public void averageRatingTest() {
        TicketSystem.initialize();
        TicketSystem.reserveTicket(Event.Concert, 5);
        TicketSystem.rate(Event.Concert, 4);
        TicketSystem.rate(Event.Concert, 2);
        TicketSystem.printHistory();

        String output = TestHelper.outputStreamCaptor.toString();

        assertTrue(output.contains("Event: Concert\n" +
                "Hat eine durchschnittliche Bewertung von 3.0 Sternen."));
    }
}
