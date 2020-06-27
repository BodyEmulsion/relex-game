package ru.peltikhin.models.elements;

public class Element {
    private ElementType elementType;
    private Boolean isOpen;
    private Integer period;
    private Integer firstMoveTime;
    private Integer altitude;

    public Element(ElementType elementType, Boolean isOpen, Integer period, Integer firstMoveTime, Integer altitude) {
        this.elementType = elementType;
        this.isOpen = isOpen;
        this.period = period;
        this.firstMoveTime = firstMoveTime;
        this.altitude = altitude;
    }

    public Element() {
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getFirstMoveTime() {
        return firstMoveTime;
    }

    public void setFirstMoveTime(Integer firstMoveTime) {
        this.firstMoveTime = firstMoveTime;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public  Boolean isCanContaineBall(){
        if(elementType == ElementType.WALL){
            return false;
        } else if(elementType == ElementType.ROOM) {
            return true;
        } else {
            return isOpen;
        }
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public ElementType getElementType() {
        return elementType;
    }
}
