package io.github.adamsondavid.json.util;

import java.util.HashSet;
import java.util.Set;

public class JavaPrimitiveWrapperTypes {

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    public static boolean isWrapperType(Class<?> clazz)
    {
        return WRAPPER_TYPES.contains(clazz);
    }

    public static <T> T castPrimitive(Class<T> clazz, Object object){
        if (clazz == String.class){
            return (T)String.valueOf(object);
        }

        if (object.getClass() == Boolean.class || object.getClass() == Void.class) {
            return (T) object;
        }

        Class<?> boxedClazz = boxType(clazz);

        if (object.getClass() == Character.class) {
            if (boxedClazz == Byte.class) {
                return (T)(Byte)(byte)(char)object;
            }

            if (boxedClazz == Short.class) {
                return (T)(Short)(short)(char)object;
            }

            if (boxedClazz == Integer.class) {
                return (T)(Integer)(int)(char)object;
            }

            if (boxedClazz == Long.class) {
                return (T)(Long)(long)(char)object;
            }

            if (boxedClazz == Float.class) {
                return (T)(Float)(float)(char)object;
            }

            if (boxedClazz == Double.class) {
                return (T)(Double)(double)(char)object;
            }
        }

        if (object.getClass() == Byte.class) {
            if (boxedClazz == Character.class) {
                return (T)(Character)(char)(byte)object;
            }

            if (boxedClazz == Short.class) {
                return (T)(Short)(short)(byte)object;
            }

            if (boxedClazz == Integer.class) {
                return (T)(Integer)(int)(byte)object;
            }

            if (boxedClazz == Long.class) {
                return (T)(Long)(long)(byte)object;
            }

            if (boxedClazz == Float.class) {
                return (T)(Float)(float)(byte)object;
            }

            if (boxedClazz == Double.class) {
                return (T)(Double)(double)(byte)object;
            }
        }

        if (object.getClass() == Short.class) {
            if (boxedClazz == Character.class) {
                return (T)(Character)(char)(short)object;
            }

            if (boxedClazz == Byte.class) {
                return (T)(Byte)(byte)(short)object;
            }

            if (boxedClazz == Integer.class) {
                return (T)(Integer)(int)(short)object;
            }

            if (boxedClazz == Long.class) {
                return (T)(Long)(long)(short)object;
            }

            if (boxedClazz == Float.class) {
                return (T)(Float)(float)(short)object;
            }

            if (boxedClazz == Double.class) {
                return (T)(Double)(double)(short)object;
            }
        }

        if (object.getClass() == Integer.class) {
            if (boxedClazz == Character.class) {
                return (T)(Character)(char)(int)object;
            }

            if (boxedClazz == Byte.class) {
                return (T)(Byte)(byte)(int)object;
            }

            if (boxedClazz == Short.class) {
                return (T)(Short)(short)(int)object;
            }

            if (boxedClazz == Long.class) {
                return (T)(Long)(long)(int)object;
            }

            if (boxedClazz == Float.class) {
                return (T)(Float)(float)(int)object;
            }

            if (boxedClazz == Double.class) {
                return (T)(Double)(double)(int)object;
            }
        }

        if (object.getClass() == Long.class) {
            if (boxedClazz == Character.class) {
                return (T)(Character)(char)(long)object;
            }

            if (boxedClazz == Byte.class) {
                return (T)(Byte)(byte)(long)object;
            }

            if (boxedClazz == Short.class) {
                return (T)(Short)(short)(long)object;
            }

            if (boxedClazz == Integer.class) {
                return (T)(Integer)(int)(long)object;
            }

            if (boxedClazz == Float.class) {
                return (T)(Float)(float)(long)object;
            }

            if (boxedClazz == Double.class) {
                return (T)(Double)(double)(long)object;
            }
        }

        if (object.getClass() == Float.class) {
            if (boxedClazz == Character.class) {
                return (T)(Character)(char)(float)object;
            }

            if (boxedClazz == Byte.class) {
                return (T)(Byte)(byte)(float)object;
            }

            if (boxedClazz == Short.class) {
                return (T)(Short)(short)(float)object;
            }

            if (boxedClazz == Integer.class) {
                return (T)(Integer)(int)(float)object;
            }

            if (boxedClazz == Long.class) {
                return (T)(Long)(long)(float)object;
            }

            if (boxedClazz == Double.class) {
                return (T)(Double)(double)(float)object;
            }
        }

        if (object.getClass() == Double.class) {
            if (boxedClazz == Character.class) {
                return (T)(Character)(char)(double)object;
            }

            if (boxedClazz == Byte.class) {
                return (T)(Byte)(byte)(double)object;
            }

            if (boxedClazz == Short.class) {
                return (T)(Short)(short)(double)object;
            }

            if (boxedClazz == Integer.class) {
                return (T)(Integer)(int)(double)object;
            }

            if (boxedClazz == Long.class) {
                return (T)(Long)(long)(double)object;
            }

            if (boxedClazz == Float.class) {
                return (T)(Float)(float)(double)object;
            }
        }

        return null;
    }

    private static Class<?> boxType(Class<?> type){
        if (type == boolean.class) {
            return Boolean.class;
        }

        if (type == char.class) {
            return Character.class;
        }

        if (type == byte.class) {
            return Byte.class;
        }

        if (type == short.class) {
            return Short.class;
        }

        if (type == int.class) {
            return Integer.class;
        }

        if (type == long.class) {
            return Long.class;
        }

        if (type == float.class) {
            return Float.class;
        }

        if (type == double.class) {
            return Double.class;
        }

        if (type == Void.class) {
            return Void.class;
        }

        return type;
    }

    private static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(String.class);

        ret.add(boolean.class);
        ret.add(Boolean.class);

        ret.add(char.class);
        ret.add(Character.class);

        ret.add(byte.class);
        ret.add(Byte.class);

        ret.add(short.class);
        ret.add(Short.class);

        ret.add(int.class);
        ret.add(Integer.class);

        ret.add(long.class);
        ret.add(Long.class);

        ret.add(float.class);
        ret.add(Float.class);

        ret.add(double.class);
        ret.add(Double.class);

        ret.add(void.class);
        ret.add(Void.class);
        return ret;
    }

}
