package ru.peltikhin.models.elements;

public class Element {
    private ElementType elementType;
    private int period;
    private int altitude;
    private boolean isOpen = false;
    private boolean isContainBall = false;
    private ElementFunction elementFunction;

    public Element() {
    }

    public Element(Element that) {
        this.elementType = that.elementType;
        this.period = that.period;
        this.altitude = that.altitude;
        this.isOpen = that.isOpen;
        this.isContainBall = that.isContainBall;
        this.elementFunction = that.elementFunction;
    }

    public ElementFunction getElementFunction() {
        return elementFunction;
    }

    public void setElementFunction(ElementFunction elementFunction) {
        this.elementFunction = elementFunction;
    }

    public Boolean isStart() {
        return elementFunction==ElementFunction.START;
    }

    public Boolean isFinish() {
        return elementFunction==ElementFunction.FINISH;
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
            switch (elementFunction){
                case START:
                    return "|" + altitude + "|";
                case FINISH:
                    return "{" + altitude + "}";
                case DEFAULT:
                    return "[" + altitude + "]";
                default:
                    throw new UnknownError("It's impossible, but suddenly");
            }
        } else {
            if(isOpen){
                return "[" + altitude + "]";
            } else {
                return "[X]";
            }
        }
    }

    public Boolean getIsContainBall() {
        return isContainBall;
    }

    public void setIsContainBall(Boolean isContainBall) {
        this.isContainBall = isContainBall;
    }
}
