package ru.clevertec.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class AbstractContainer<T> {

    protected final Type type;

    protected AbstractContainer() {
        Type superClass = getClass().getGenericSuperclass();

        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }


    public Type getType(){
        return type;
    }
}
