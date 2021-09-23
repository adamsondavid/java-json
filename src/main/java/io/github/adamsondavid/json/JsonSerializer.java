package io.github.adamsondavid.json;

import io.github.adamsondavid.json.util.JavaPrimitiveWrapperTypes;

import java.lang.reflect.Field;

public class JsonSerializer {

    public static <T> String toString(T object) {
        if (object == null){
            return "null";
        }

        if (JavaPrimitiveWrapperTypes.isWrapperType(object.getClass())){
            if (object.getClass() == String.class){
                return "\"" + object + "\"";
            }
            return object.toString();
        }

        if (object.getClass().isArray()){
            return arrayToString((Object[]) object);
        }

        String value = "";

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields){
            try {
                field.setAccessible(true);
                value += "\"" + field.getName() + "\": " + toString(field.get(object)) + ", ";
            } catch(IllegalAccessException e){
                e.printStackTrace();
            }
        }

        if (value.length() > 1){
            value = value.substring(0, value.length()-2);
        }

        return "{" + value + "}";
    }

    private static <T> String arrayToString(Object[] array){
        String value = "[";
        for (Object o : array){
            value += toString((T)o) + ", ";
        }

        if (value.length() > 1){
            value = value.substring(0, value.length()-2);
        }

        return value + "]";
    }

}
