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

    private final Scanner scanner = new Scanner(System.in);

    public TicketSystemService(){

    }

    /**
     * Entry point of the application with a console interface.
     */
    public void startApplication() throws InterruptedException {
        System.out.println("Hi");

        TicketSystem.initialize();

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


        Thread.sleep(5000);

        TicketSystem.printHistory();

        TicketSystem.printAverageRating(Event.Concert);
        TicketSystem.printAverageRating(Event.Sport);
        TicketSystem.printAverageRating(Event.Theatre);
    }
}
