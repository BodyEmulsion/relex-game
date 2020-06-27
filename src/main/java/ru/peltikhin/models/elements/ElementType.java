package ru.peltikhin.models.elements;

import java.util.Optional;

public enum ElementType {
    ROOM("R"),
    WALL("W"),
    OPENABLE_WALL("O"),
    CLOSABLE_WALL("C");

    private final String type;

    ElementType(String type) {
        this.type = type;
    }

    public static Optional<ElementType> of(final String type) {
        if (type == null) {
            return Optional.empty();
        }

        for (var value : ElementType.values()) {
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
