/**
 * TicketSystemService is a class representing the main ticket reservation system application.
 * It provides a console interface for the ticket reservation system.
 * The system processes reservations from the reservations.txt file and allows users to
 * perform actions such as reserving tickets, canceling reservations, and providing ratings.
 *
 * @authors: Graf Andreas, Sirbescu Amalia, Vass Viktoria
 * @date: 02.01.2024
 * @project: uebung6 - ALGO
 */

package org.lecture;

import org.lecture.enums.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketSystemService {

    private final Scanner scanner = new Scanner(System.in);

    public TicketSystemService() {

    }

    /**
     * Entry point of the application with a console interface.
     */
    public void startApplication() throws InterruptedException {
        String welcomeText = """
                           * * * Ticketreservierungssystem * * *
                                     
                Sie befinden sich in einer Ticketreservierungs-Applikation, die die
                bereitgestellten Reservierungen aus der Datei reservations.txt
                (gespeichert unter: src/main/resources/reservations.txt) verarbeitet.
                                
                Wenn Sie Reservierungen hinzufügen, entfernen oder ändern möchten, passen
                Sie bitte dieses .txt File an und starten Sie diese Applikation erneut.
                """;
        System.out.println(welcomeText);

        TicketSystem.initialize();
        InputHandler.readFile();

        /*
        User amalia = new User("Amalia");
        amalia.reserve(Event.Sport, 10);
        amalia.cancel(3);

        User andi = new User("Andi");
        andi.reserve(Event.Concert, 2);
        andi.rate(5);

        User viktoria = new User("Viktoria");
        viktoria.reserve(Event.Theatre, 15);
        viktoria.cancel(7);
        viktoria.rate(3);

        User anon = new User("Anon");
        anon.reserve(Event.Theatre, 10);
        anon.rate(2);

        Thread.sleep(2000);
        andi.confirmReservation();
        viktoria.confirmReservation();
         */

        Thread.sleep(7000);

        System.out.println("* Reservation history *\n");
        TicketSystem.printHistory();

        System.out.println("* Ratings *\n");
        TicketSystem.printAverageRating(Event.Concert);
        TicketSystem.printAverageRating(Event.Sport);
        TicketSystem.printAverageRating(Event.Theatre);
    }
}
