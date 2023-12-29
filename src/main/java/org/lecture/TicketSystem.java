package org.lecture;

import org.lecture.enums.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class TicketSystem {
    private static Map<Event, Integer> ticketSystem;
    private static Map<Event, List<Integer>> ratings;
    private static List<String> history;

    private TicketSystem(){
    }

    private static ReentrantLock[] mutex = new ReentrantLock[3];
    private static ReentrantLock historyLock;

    public static void initialize(){
        ticketSystem = new HashMap<>();
        // key = Event, value = current amount of available tickets
        ticketSystem.put(Event.Concert, Event.Concert.getMaxTickets());
        ticketSystem.put(Event.Sport, Event.Sport.getMaxTickets());
        ticketSystem.put(Event.Theatre, Event.Theatre.getMaxTickets());

        history = new ArrayList<>();
        for (int i = 0; i < mutex.length; i++) {
            mutex[i] = new ReentrantLock();
        }

        ratings = new HashMap<>();
        ratings.put(Event.Concert, new ArrayList<>());
        ratings.put(Event.Sport, new ArrayList<>());
        ratings.put(Event.Theatre, new ArrayList<>());

        historyLock = new ReentrantLock();
    }

    public static void reserveTicket(Event event, int amountTickets) {
        try {
            mutex[event.ordinal()].lock();
            int freeTickets = ticketSystem.get(event);
            if((freeTickets - amountTickets) < 0)
            {
                System.err.println("Benutzer: " + Thread.currentThread().getName() + "\nNicht genügend Tickets verfügbar!\n"
                        + "Anzahl der verfügbaren Tickets: " + freeTickets);
                return;
            }
            ticketSystem.replace(event, ticketSystem.get(event) - amountTickets);

            // reservation history
            historyLock.lock();
            history.add("Benutzer " + Thread.currentThread().getName() + " reserviert " + amountTickets
                    + " Tickets für das Event: " + event.name() + " um " + event.getTime() + ".");
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        finally {
            mutex[event.ordinal()].unlock();
            historyLock.unlock();
        }
    }

    public static void cancelTicket(Event event, int amountTickets) {
        try {
            mutex[event.ordinal()].lock();
            int freeTickets = ticketSystem.get(event);
            if((freeTickets + amountTickets) > event.getMaxTickets())
            {
                System.err.println("Benutzer: " + Thread.currentThread().getName() + "\nEs wurden zu viele Tickets storniert.\n"
                        + "Anzahl der maximalen Tickets: " + event.getMaxTickets());
                return;
            }
            ticketSystem.replace(event, ticketSystem.get(event) + amountTickets);

            // reservation history
            historyLock.lock();
            history.add("Benutzer " + Thread.currentThread().getName() + " storniert " + amountTickets
                    + " Tickets für das Event: " + event.name() + " um " + event.getTime() + ".");
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        finally {
            mutex[event.ordinal()].unlock();
            historyLock.unlock();
        }
    }

    public static void rate(Event event, int rating) {
        try {
            mutex[event.ordinal()].lock();
            if ((rating < 1) || (rating > 5))
            {
                System.err.println("Benutzer: " + Thread.currentThread().getName() + "Bitte eine Bewertung zwischen 1 und 5 abgeben.");
                return;
            }
            ratings.get(event).add(rating);

            historyLock.lock();
            history.add("Benutzer " + Thread.currentThread().getName() + " bewertet das Event: "
                    + event.name() + " um " + event.getTime() + " mit " + rating + " Sternen.");
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        finally {
            mutex[event.ordinal()].unlock();
            historyLock.unlock();
        }
    }

    public static void printHistory(){
        for (String s : history)
            System.out.println(s);
    }

    public static void printAverageRating(Event event) {
        int res = 0;
        List<Integer> list = ratings.get(event);
        for (int i = 0; i < list.size(); i++) {
            res += list.get(i);
        }

        System.out.println("Event: " + event.name()
                + " hat eine durchschnittliche Bewertung von " + ((double)res / list.size()) + " Sternen.");
    }
}
