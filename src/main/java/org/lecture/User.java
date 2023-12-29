package org.lecture;

import lombok.Setter;
import org.lecture.enums.Event;

import java.util.Timer;
import java.util.TimerTask;

@Setter
public class User {

    public Event event = null;
    public int amountTickets = 0;
    public String name;
    private Timer reservationTimer;

    public User(String name) {
        this.name = this.name.contains("Timer") ? "default" : name;
        this.reservationTimer = new Timer(true);
    }

    public void reserve(Event event, int amountTickets){
        if(this.event != null){
            System.err.println("Es wurden bereits Tickets für das Event " + this.event.name() + " reserviert.");
            return;
        }

        Thread thread = new Thread(() -> {
            TicketSystem.reserveTicket(event, amountTickets);
        });

        checkReservationTimeout();

        thread.setName(this.name);
        thread.start();

        this.amountTickets += amountTickets;
        this.event = event;
    }

    private void checkReservationTimeout(){
        TimerTask task = new TimerTask() {
            public void run() {
                TicketSystem.cancelTicket(event, amountTickets);
            }
        };
        reservationTimer.schedule(task, 5000L);
    }

    public void confirmReservation() {
        reservationTimer.cancel();
    }

    public void cancel(int amountTickets){
        if(this.event == null){
            System.err.println("Es wurden noch keine Tickets reserviert!");
            return;
        }

        if (amountTickets <= this.amountTickets) {
            Thread thread = new Thread(() -> {
                TicketSystem.cancelTicket(this.event, amountTickets);
            });
            thread.setName(this.name);
            thread.start();

            this.amountTickets -= amountTickets;

            if(this.amountTickets == amountTickets)
                reservationTimer.cancel();
        }
    }

    public void rate(int rating){
        if(this.event == null){
            System.err.println("Es wurden noch keine Tickets reserviert!");
            return;
        }

        Thread thread = new Thread(() -> {
            TicketSystem.rate(this.event, rating);
        });
        thread.setName(this.name);
        thread.start();
    }
}
