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

public class TicketSystemDriver {

    /**
     * Entry point of the application with a console interface.
     *
     * @param args The arguments given when executing the application.
     */
    public static void main(String[] args) throws InterruptedException {
        TicketSystemService ticketSystem = new TicketSystemService();

        ticketSystem.startApplication();

        System.out.println("\nGood Bye - Closing Application");
    }
}
