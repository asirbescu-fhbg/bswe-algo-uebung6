/**
 * SortService is a Java class that provides a console-based application for sorting arrays using Radix Sort
 * and Bubble Sort algorithms. It allows the user to choose sorting options, input data manually or from a file,
 * and view the sorted results along with the execution time.
 *
 * @authors: Graf Andreas, Sirbescu Amalia, Vass Viktoria
 * @date: 06.12.2023
 * @project: uebung3 - ALGO
 */

package org.lecture;

import org.lecture.enums.Event;

import java.util.Scanner;

public class TicketSystemService {

    private Scanner scanner = new Scanner(System.in);

    public TicketSystemService(){

    }

    /**
     * Entry point of the application with a console interface.
     */
    public void startApplication() throws InterruptedException {
        System.out.println("Hi");

        TicketSystem ticketSystem = new TicketSystem();
        Thread andi = new Thread(() -> ticketSystem.reserveTicket(Event.Concert, 7));
        andi.setName("Andi");
        Thread viktoria = new Thread(() -> ticketSystem.reserveTicket(Event.Concert, 9));
        viktoria.setName("Viktoria");

        andi.start();
        viktoria.start();
        andi.join();
        viktoria.join();

        andi = new Thread(() -> ticketSystem.cancelTicket(Event.Concert, 2));
        andi.setName("Andi");
        viktoria = new Thread(() -> ticketSystem.cancelTicket(Event.Concert, 4));
        viktoria.setName("Viktoria");

        andi.start();
        viktoria.start();
        andi.join();
        viktoria.join();

        andi = new Thread(() -> ticketSystem.rate(Event.Concert, 2));
        andi.setName("Andi");
        viktoria = new Thread(() -> ticketSystem.rate(Event.Concert, 5));
        viktoria.setName("Viktoria");

        andi.start();
        viktoria.start();
        andi.join();
        viktoria.join();

        ticketSystem.printHistory();

        ticketSystem.printAverageRating(Event.Concert);
    }
}
