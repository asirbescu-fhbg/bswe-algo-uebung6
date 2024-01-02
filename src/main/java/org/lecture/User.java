/**
 * User is a class representing a user in the ticket reservation system.
 * Each user has a name, can reserve tickets for events, confirm reservations,
 * cancel reservations, and provide ratings for events they attended.
 *
 * @authors: Graf Andreas, Sirbescu Amalia, Vass Viktoria
 * @date: 02.01.2024
 * @project: uebung6 - ALGO
 */

package org.lecture;

import lombok.Getter;
import lombok.Setter;
import org.lecture.enums.Event;

import java.util.Timer;
import java.util.TimerTask;

@Setter
@Getter
public class User {

    public Event event = null;
    public int amountTickets = 0;
    public String name;

    public int ratingFromUser = 0;
    private Timer reservationTimer;

    /**
     * Constructor for User class.
     *
     * @param name The name of the user.
     */
    public User(String name) {
        this.name = name.contains("Timer") ? "default" : name;
        this.reservationTimer = new Timer(true);
    }

    /**
     * Reserves tickets for a specified event.
     *
     * @param event The event for which tickets are to be reserved.
     * @param amountTickets The number of tickets to be reserved.
     */
    public void reserve(Event event, int amountTickets) {
        if (this.event != null) {
            System.err.println("Benutzer: " + name + "\nHat bereits Tickets für das Event " + this.event.name() + " reserviert.\n");
            return;
        }

        boolean enoughFreeTickets = TicketSystem.ticketsAvailable(event, amountTickets, name);

        if (enoughFreeTickets) {
            Thread thread = new Thread(() -> {
                TicketSystem.reserveTicket(event, amountTickets);
            });

            thread.setName(this.name);
            thread.start();

            this.amountTickets += amountTickets;
            this.event = event;

            checkReservationTimeout();
        }
    }

    /**
     * Checks if a reservation times out and cancels it if necessary.
     */
    private void checkReservationTimeout() {
        TimerTask task = new TimerTask() {
            public void run() {
                TicketSystem.cancelTicket(event, amountTickets, name);
            }
        };
        reservationTimer.schedule(task, 5000L);
    }

    /**
     * Confirms a reservation if the user has reserved tickets.
     * Cancels confirmation if there are no reserved tickets.
     */
    public void confirmReservation() {
        if (this.amountTickets > 0) {
            reservationTimer.cancel();
            TicketSystem.confirm(event, amountTickets, name);
        } else {
            System.err.println("Benutzer: " + name + "\nHat keine reservierten Tickets, daher kann die Reservierung nicht bestätigt werden.\n");
        }
    }

    /**
     * Cancels a specified number of tickets for the user.
     *
     * @param amountTickets The number of tickets to be canceled.
     */
    public void cancel(int amountTickets) {
        if (this.event == null) {
            System.err.println("Benutzer: " + name + "\nHat noch keine Tickets für das Event '" + this.event.name() + "' reserviert! Stornierung nicht möglich.\n");
            return;
        }

        if (amountTickets <= this.amountTickets) {
            Thread thread = new Thread(() -> {
                TicketSystem.cancelTicket(this.event, amountTickets, name);
            });

            thread.setName(this.name);
            thread.start();

            this.amountTickets -= amountTickets;

            if (this.amountTickets == amountTickets)
                reservationTimer.cancel();
        }
    }

    /**
     * Provides a rating for a specified event if the conditions are met.
     *
     * @param event  The event for which the rating is provided.
     * @param rating The rating provided by the user.
     */
    public void rate(Event event, int rating) {
        if (this.ratingFromUser <= 0 || this.ratingFromUser > 5) {
            this.ratingFromUser = rating;
            if (this.event != null && this.event.equals(event) && this.amountTickets > 0) {
                Thread thread = new Thread(() -> {
                    TicketSystem.rate(this.event, rating);
                });
                thread.setName(this.name);
                thread.start();
            } else {
                System.err.println("Benutzer: " + name + "\nHat keine Tickets für das Event '" + event.name() + "' reserviert! Bewertung nicht möglich.\n");
            }
        } else {
            System.err.println("Benutzer: " + name + "\nHat bereits das Event '" + event.name() + "' mit " + this.ratingFromUser + " Sternen bewertet! Erneute Bewertung nicht möglich.\n");
        }
    }
}
