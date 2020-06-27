package ru.peltikhin.models.rooms;

public class VolatileWall extends Element {
    private Boolean isOpen;
    private Boolean isOpenable;
    private Integer period;
    private Integer firstMoveTime;
    private Integer altitude;
    @Override
    public Boolean isCanContaineBall() {
        return isOpen;
    }
}
