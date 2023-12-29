package org.lecture;

import lombok.Setter;
import org.lecture.enums.Event;

@Setter
public class User {

    public Event event = null;
    public int amountTickets = 0;
    public String name;

    public User(String name) {
        this.name = name;
    }

    public void reserve(Event event, int amountTickets){
        if(this.event != null){
            System.err.println("Es wurden bereits Tickets für das Event " + this.event.name() + " reserviert.");
            return;
        }

        Thread thread = new Thread(() -> {
            TicketSystem.reserveTicket(event, amountTickets);
        });
        thread.setName(this.name);
        thread.start();

        this.amountTickets += amountTickets;
        this.event = event;
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
