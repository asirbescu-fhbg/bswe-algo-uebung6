/**
 * InputHandler is a utility class that provides methods for file handling, including reading CSV files and
 * processing the content for further use.
 *
 * @authors: Graf Andreas, Sirbescu Amalia, Vass Viktoria
 * @date: 06.12.2023
 * @project: uebung3 - ALGO
 */

package org.lecture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This Class provides methods for file handling.
 */
public class InputHandler {

    /**
     * Read the given CSV File and return the content as String Array.
     *
     * @param pathString The path of the File.
     * @return The content of the File as String Array.
     */
    public static List<String> readCsvFile(String pathString) {
        Path path = Paths.get(pathString);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Should not happen - dummy.txt not found !");
        }

        return lines;
    }

    public static String[] splitNumbers(List<String> lines) {
        List<String> numbers = new ArrayList<>();
        for (var line : lines) {
            String[] split = line.replace(" ", "").split(";");
            numbers.addAll(Arrays.asList(split));
        }

        return numbers.toArray(new String[0]);
    }
}
