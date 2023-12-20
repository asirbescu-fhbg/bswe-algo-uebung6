package org.lecture.enums;

import lombok.Getter;

@Getter
public enum Event {
    Concert(30, "20:30"),
    Theatre(40, "22:00"),
    Sport(50, "18:00");

    private final int maxTickets;
    private final String time;


    Event(int maxTickets, String time) {
        this.maxTickets = maxTickets;
        this.time = time;
    }
}
