package ru.peltikhin.models.elements;

public class Element {
    private ElementType elementType;
    private int period;
    private int firstMoveTime;
    private int altitude;
    private boolean isOpen = false;
    private boolean isContainBall = false;
    private boolean isStart = false;
    private boolean isFinish = false;

    public Element() {
    }

    public Element(Element that) {
        this.elementType = that.elementType;
        this.period = that.period;
        this.firstMoveTime = that.firstMoveTime;
        this.altitude = that.altitude;
        this.isOpen = that.isOpen;
        this.isContainBall = that.isContainBall;
        this.isStart = that.isStart;
        this.isFinish = that.isFinish;
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
                return "|" + altitude + "|";
            } else if(isFinish){
                return "{" + altitude + "}";
            } else {
                return "[" + altitude + "]";
            }
        } else {
            if(isOpen){
                return "[" + altitude + "]";
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
