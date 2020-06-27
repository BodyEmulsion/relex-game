package ru.peltikhin.models.rooms;

public abstract class Element {
    private ElementType elementType;
    public abstract Boolean isCanContaineBall();

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public ElementType getElementType() {
        return elementType;
    }
}
