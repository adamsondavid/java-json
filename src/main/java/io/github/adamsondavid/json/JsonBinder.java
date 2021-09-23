package io.github.adamsondavid.json;

import io.github.adamsondavid.json.util.JavaPrimitiveWrapperTypes;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class JsonBinder {

    public static <T> T to(Class<T> clazz, JsonObject jsonObject) throws NoSuchEntryException {
        T result = null;
        try {
            result = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            try {
                field.setAccessible(true);

                Object object = jsonObject.get(field.getName());

                if (object == null){
                    throw new NoSuchEntryException(field);
                }

                field.set(result, objectTo(field.getType(), object));
            } catch(IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    private static <T> T objectTo(Class<T> clazz, Object object) throws NoSuchEntryException {
        if (object == null){
            throw new NullPointerException();
        }

        if (JavaPrimitiveWrapperTypes.isWrapperType(clazz)){
            return JavaPrimitiveWrapperTypes.castPrimitive(clazz, object);
        }

        if (clazz.isArray()){
            return (T) objectArrayTo(clazz.getComponentType(), (JsonArray)object);
        }

        return JsonBinder.to(clazz, (JsonObject) object);
    }

    private static <T> T[] objectArrayTo(Class<T> componentClazz, JsonArray jsonArray) throws NoSuchEntryException {
        T[] objects = (T[]) Array.newInstance(componentClazz, jsonArray.size());
        for (int i = 0; i < objects.length; i++){
            objects[i] = objectTo(componentClazz, jsonArray.get(i));
        }
        return objects;
    }
}
