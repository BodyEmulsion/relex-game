package ru.peltikhin.models.rooms;

public class Wall extends Element {
    @Override
    public Boolean isCanContaineBall() {
        return false;
    }
}
