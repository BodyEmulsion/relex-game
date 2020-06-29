package ru.peltikhin.models.elements;

import java.util.Optional;

public enum ElementFunction {
    DEFAULT("D"),
    START("S"),
    FINISH("F");

    private final String type;

    ElementFunction(String type) {
        this.type = type;
    }

    public static Optional<ElementFunction> of(final String type) {
        if (type == null) {
            return Optional.empty();
        }

        for (var value : ElementFunction.values()) {
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
