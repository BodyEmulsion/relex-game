package ru.peltikhin.models.elements;

public class Element {
    private ElementType elementType;
    private Integer period;
    private Integer firstMoveTime;
    private Integer altitude;
    private Boolean isOpen = false;
    private Boolean isContainBall = false;
    private Boolean isStart = false;
    private Boolean isFinish = false;

    public Element() {
    }

    public Boolean isStart() {
        return isStart;
    }

    public void setStart(Boolean start) {
        isStart = start;
    }

    public Boolean isFinish() {
        return isFinish;
    }

    public void setEnd(Boolean end) {
        isFinish = end;
    }

    public Boolean isOpen() {
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

    public String getView() {
        if(isContainBall){
            return "[o]";
        } else if(elementType == ElementType.WALL){
            return "[X]";
        } else if (elementType == ElementType.ROOM){
            if(isStart){
                return "|" + altitude.toString() + "|";
            } else if(isFinish){
                return "{" + altitude.toString() + "}";
            } else {
                return "[" + altitude.toString() + "]";
            }
        } else {
            if(isOpen){
                return "[" + altitude.toString() + "]";
            } else {
                return "[X]";
            }
        }
    }

    @Override
    public String toString() {
        return getElementType().getType()+ ":" + getAltitude()+ ":" +getFirstMoveTime() +":" +getPeriod() + " ";
    }

    public Boolean getIsContainBall() {
        return isContainBall;
    }

    public void setIsContainBall(Boolean isContainBall) {
        this.isContainBall = isContainBall;
    }
}
