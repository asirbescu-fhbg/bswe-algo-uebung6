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

public class TicketSystemDriver {

    /**
     * Entry point of the application with a console interface.
     *
     * @param args The arguments given when executing the application.
     */
    public static void main(String[] args) throws InterruptedException {
        TicketSystemService ticketSystemService = new TicketSystemService("src/main/resources/reservations.txt");

        ticketSystemService.startApplication();

        System.out.println("\nGood Bye - Closing Application");
    }
}
