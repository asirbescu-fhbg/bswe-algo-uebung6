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

    private String filepath;

    public TicketSystemService(String filepath) {
        this.filepath = filepath;
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
        InputHandler.readFile(filepath);

        Thread.sleep(7000);

        System.out.println("* Reservation history *\n");
        TicketSystem.printHistory();

        System.out.println("* Ratings *\n");
        TicketSystem.printAverageRating(Event.Concert);
        TicketSystem.printAverageRating(Event.Sport);
        TicketSystem.printAverageRating(Event.Theatre);
    }
}
