package com.topsycreed.superhero;

public enum EventType {
    REJECTED("Отклоненный"),
    REGISTERED("зарегистрированный");

    EventType(String translation) {
        this.translation = translation;
    }

    final String translation;
}
