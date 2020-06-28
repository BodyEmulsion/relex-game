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

    public boolean getStartPosition(){
        switch (this){
            case OPENABLE_WALL:
            case WALL:
                return false;
            case CLOSABLE_WALL:
            case ROOM:
                return true;
        }
        return false;
        //TODO fix
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
