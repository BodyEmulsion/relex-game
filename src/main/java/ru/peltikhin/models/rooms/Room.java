package ru.peltikhin.models.rooms;

public class Room extends Element{
    private Integer altitude;

    @Override
    public Boolean isCanContaineBall() {
        return true;
    }
}
