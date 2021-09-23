package io.github.adamsondavid.json;

import java.lang.reflect.Field;

public class NoSuchEntryException extends Exception {

    private Field field;

    public NoSuchEntryException(Field field){
        super("field '" + field.getName() + "' not found");
        this.field = field;
    }

    public Field getField(){
        return this.field;
    }

}
