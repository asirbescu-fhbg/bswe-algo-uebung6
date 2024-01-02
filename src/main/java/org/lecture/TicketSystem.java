/**
 * TicketSystemDriver is the entry point of the application with a console interface.
 * It initializes and starts the TicketSystemService to run the ticket reservation system.
 * This class serves as the main driver for the ticket reservation application.
 *
 * @authors: Graf Andreas, Sirbescu Amalia, Vass Viktoria
 * @date: 02.01.2024
 * @project: uebung6 - ALGO
 */

package org.lecture;

import org.lecture.enums.Event;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class TicketSystem {
    private static Map<Event, Integer> ticketSystem;
    private static Map<Event, List<Integer>> ratings;
    private static List<String> history;

    private TicketSystem() {
    }

    private static ReentrantLock[] mutex = new ReentrantLock[3];
    private static ReentrantLock historyLock;

    /**
     * Initializes the ticket system, setting the initial ticket availability, and creating necessary locks.
     */
    public static void initialize() {
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

    /**
     * Reserves tickets for a specific event.
     *
     * @param event The type of event.
     * @param amountTickets The number of tickets to reserve.
     */
    public static void reserveTicket(Event event, int amountTickets) {
        try {
            mutex[event.ordinal()].lock();
            int freeTickets = ticketSystem.get(event);
            if ((freeTickets - amountTickets) < 0) {
                System.err.println("Benutzer: " + Thread.currentThread().getName() +
                        "\nNicht genügend Tickets für das Event '" + event.name() + "' verfügbar!\n" +
                        "Anzahl der verfügbaren Tickets: " + freeTickets + "\n");
            }
            ticketSystem.replace(event, ticketSystem.get(event) - amountTickets);

            historyLock.lock();
            try {
                history.add("Benutzer: " + Thread.currentThread().getName() +
                        "\nReserviert " + amountTickets +
                        " Tickets für das Event '" + event.name() + "' um " + event.getTime() + "." + "\n");
            } finally {
                historyLock.unlock();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage() + " reserveTicket");
        } finally {
            mutex[event.ordinal()].unlock();
        }
    }

    /**
     * Checks if the requested number of tickets is available for reservation.
     *
     * @param event The type of event.
     * @param amountTickets The number of tickets requested.
     * @param name The name of the user making the request.
     * @return True if tickets are available; false otherwise.
     */
    public static boolean ticketsAvailable(Event event, int amountTickets, String name) {
        int freeTickets = ticketSystem.get(event);
        if ((freeTickets - amountTickets) < 0) {
            System.err.println("Benutzer: " + name +
                    "\nNicht genügend Tickets für das Event '" + event.name() + "' verfügbar!\n" +
                    "Anzahl der verfügbaren Tickets: " + freeTickets + "\n");
            return false;
        }
        return true;
    }

    /**
     * Cancels a reservation for tickets to a specific event.
     *
     * @param event The type of event.
     * @param amountTickets The number of tickets to cancel.
     * @param nameUser The name of the user canceling the reservation.
     */
    public static void cancelTicket(Event event, int amountTickets, String nameUser) {
        try {
            mutex[event.ordinal()].lock();
            int freeTickets = ticketSystem.get(event);
            if ((freeTickets + amountTickets) > event.getMaxTickets()) {
                System.err.println("Benutzer: " + nameUser + "\nEs wurden zu viele Tickets storniert.\n"
                        + "Anzahl der maximalen stornierbaren Tickets: " + amountTickets + "\n");
                return;
            }
            ticketSystem.replace(event, ticketSystem.get(event) + amountTickets);

            historyLock.lock();
            try {
                if (Thread.currentThread().getName().contains("Timer")) {
                    history.add("Benutzer: " + nameUser + "\nAbgelaufene Reservierung. Es werden " + amountTickets +
                            " Tickets für das Event '" + event.name() + "' um " + event.getTime() + " freigegeben." + "\n");
                } else {
                    history.add("Benutzer: " + Thread.currentThread().getName() + "\nStorniert " + amountTickets
                            + " Tickets für das Event '" + event.name() + "' um " + event.getTime() + "." + "\n");
                }
            } finally {
                historyLock.unlock();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage() + " - cancelTicket");
        } finally {
            mutex[event.ordinal()].unlock();
        }
    }

    /**
     * Records a user's rating for a specific event.
     *
     * @param event  The type of event.
     * @param rating The user's rating for the event.
     */
    public static void rate(Event event, int rating) {
        try {
            mutex[event.ordinal()].lock();
            if ((rating < 1) || (rating > 5)) {
                System.err.println("Benutzer: " + Thread.currentThread().getName() + " - bitte eine Bewertung zwischen 1 und 5 abgeben." + "\n");
                return;
            }
            ratings.get(event).add(rating);
            int res = 0;
            List<Integer> list = ratings.get(event);
            for (int i = 0; i < list.size(); i++) {
                res += list.get(i);
            }
            historyLock.lock();
            history.add("Benutzer: " + Thread.currentThread().getName() + "\nBewertet das Event '"
                    + event.name() + "' um " + event.getTime() + " mit " + rating + " Sternen." + "\n");
            history.add("Event: " + event.name()
                    + "\nHat eine durchschnittliche Bewertung von " + ((double) res / list.size()) + " Sternen." + "\n");
        } catch (Exception ex) {
            System.err.println(ex.getMessage() + " - rate");
        } finally {
            mutex[event.ordinal()].unlock();
            historyLock.unlock();
        }
    }

    public static void printHistory() {
        for (String s : history)
            System.out.println(s);
    }

    /**
     * Records a user's confirmation of purchased tickets for a specific event.
     *
     * @param event The type of event.
     * @param amount The number of tickets confirmed.
     * @param name The name of the user confirming the purchase.
     */
    public static void confirm(Event event, int amount, String name) {
        try {
            mutex[event.ordinal()].lock();
            historyLock.lock();
            history.add("Benutzer: " + name + "\nKauft " + amount + " Tickets für das Event '" + event.name() + "'\n");
        } catch (Exception ex) {
            System.err.println(ex.getMessage() + " - confirm" + name + " - " + event.name());
        } finally {
            mutex[event.ordinal()].unlock();
            historyLock.unlock();
        }
    }

    /**
     * Prints the average rating for a specific event.
     *
     * @param event The type of event.
     */
    public static void printAverageRating(Event event) {
        List<Integer> list = ratings.get(event);

        if (list != null && !list.isEmpty()) {
            int res = 0;
            for (int rating : list) {
                res += rating;
            }

            DecimalFormat df = new DecimalFormat("#,##");
            double averageRating = (double) res / list.size();
            double roundedRating = Double.parseDouble(df.format(averageRating));

            System.out.println("Event: " + event.name()
                    + " hat eine durchschnittliche Bewertung von " + roundedRating + " Sternen.\n");
        } else {
            System.out.println("Event: " + event.name()
                    + " hat keine Bewertung.\n");
        }
    }
}
