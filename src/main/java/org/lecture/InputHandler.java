/**
 * InputHandler is a utility class that provides methods for file handling, including reading CSV files and
 * processing the content for further use.
 *
 * @authors: Graf Andreas, Sirbescu Amalia, Vass Viktoria
 * @date: 02.01.2024
 * @project: uebung6 - ALGO
 */

package org.lecture;

import org.lecture.enums.Event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This Class provides methods for file handling.
 */
public class InputHandler {

    static List<User> users = new ArrayList<>();

    /**
     * Read the given CSV File and return the content as String Array.
     *
     * @return The content of the File as String Array.
     */
    public static void readFile(String filepath) {
        Path path = Paths.get(filepath);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("File not found: + " + filepath);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int lineNr = 1;
        for (var line : lines) {
            if (isValidLine(line)) {
                executorService.submit(() -> processLine(line));
            } else {
                System.err.println("Line " + lineNr + " is invalid: '" + line + "' It will be ignored !\n");
            }
            lineNr++;
        }

        executorService.shutdown();
    }

    private static boolean isValidLine(String line) {
        if (line.trim().isEmpty()) {
            return false;
        }

        String[] parts = line.split(";");
        for (String part : parts) {
            if (part.trim().isEmpty()) {
                return false;
            }
        }

        try {
            Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            return false;
        }

        return parts.length == 4;
    }

    private static void processLine(String line) {
        String[] split = line.replace(" ", "").split(";");

        String userName = split[0];
        int amount = Integer.parseInt(split[1]);
        Event event = findEvent(split[2]);
        String action = split[3].toLowerCase();

        if (event == null) {
            System.err.println("Event existiert nicht: " + Arrays.toString(split));
            return;
        }

        if (userName.length() <= 1) {
            System.err.println("Benutzername ist nicht gültig: " + Arrays.toString(split));
            return;
        }

        synchronized (users) {
            User user = users.stream()
                    .filter(u -> u.getName() != null && u.getName().equals(userName))
                    .findFirst()
                    .orElseGet(() -> {
                        User newUser = new User(userName);
                        users.add(newUser);
                        return newUser;
                    });

            switch (action) {
                case "reserve" -> user.reserve(event, amount);
                case "confirmreservation" -> user.confirmReservation();
                case "cancel" -> user.cancel(amount);
                case "rate" -> {
                    if (amount <= 5 && amount >= 1) {
                        user.rate(event, amount);
                    } else {
                        System.err.println("Benutzer: " + split[0] + "\nUngültige Bewertung: " + split[1] + " Sterne für das Event '" + split[2] + "'\nBewertung muss zw. 1 und 5 sein\n");
                    }
                }
                default -> System.err.println("Maßnahme nicht vorhanden: " + Arrays.toString(split));
            }
        }
    }

    private static Event findEvent(String eventName) {
        Event event;
        switch (eventName.toLowerCase()) {
            case "concert" -> event = Event.Concert;
            case "theatre" -> event = Event.Theatre;
            case "sport" -> event = Event.Sport;
            default -> event = null;
        }
        return event;
    }
}
