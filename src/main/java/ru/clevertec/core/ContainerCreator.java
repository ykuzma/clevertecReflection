package ru.clevertec.core;

import java.lang.reflect.Type;

public interface ContainerCreator {

    <T> ContainerData<T> create(Class<T>clazz, Type type) throws NoSuchMethodException, ClassNotFoundException;
}
