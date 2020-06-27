package ru.peltikhin.models;

import ru.peltikhin.models.elements.Element;

public class Field {
    private Element[][] field;

    public Field(Element[][] field) {
        this.field = field;
    }

    public void showField() {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                System.out.print(field[j][i].getView());
            }
            System.out.println();
        }
    }
}
