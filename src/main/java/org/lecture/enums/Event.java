/**
 * Event Enumeration representing different types of events.
 * Each event type has a maximum number of tickets available and a specific time.
 *
 * @authors: Graf Andreas, Sirbescu Amalia, Vass Viktoria
 * @date: 02.01.2024
 * @project: uebung6 - ALGO
 */

package org.lecture.enums;

import lombok.Getter;

@Getter
public enum Event {
    Concert(30, "20:30 Uhr"),
    Theatre(40, "22:00 Uhr"),
    Sport(50, "18:00 Uhr");

    private final int maxTickets;
    private final String time;

    Event(int maxTickets, String time) {
        this.maxTickets = maxTickets;
        this.time = time;
    }
}
