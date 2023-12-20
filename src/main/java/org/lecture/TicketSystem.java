package org.lecture;

import org.lecture.enums.Event;

import java.util.HashMap;
import java.util.Map;

public class TicketSystem {
    Map<Event, Integer> ticketSystem;

    public TicketSystem(){
        ticketSystem = new HashMap<>();
        ticketSystem.put(Event.Concert, Event.Concert.getMaxTickets());
        ticketSystem.put(Event.Sport, Event.Sport.getMaxTickets());
        ticketSystem.put(Event.Theatre, Event.Theatre.getMaxTickets());
    }

    public static void reserveTicket (Map<Event, Integer> ticketSystem) {
        // sperren?

        System.out.println(":)");

    }
}
