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
