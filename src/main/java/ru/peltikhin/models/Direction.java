package ru.peltikhin.models;

import java.util.Optional;

public enum Direction {
    UP("UP"),
    DOWN("DOWN"),
    RIGHT("RIGHT"),
    LEFT("LEFT");

    private final String type;

    Direction(String type) {
        this.type = type;
    }

    public static Optional<Direction> of(final String type) {
        if (type == null) {
            return Optional.empty();
        }

        for (var value : Direction.values()) {
            if (value.type.equals(type)) {
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

    public String getType() {
        return type;
    }
}
