package ru.clevertec.core;

import ru.clevertec.core.node.Node;

import java.lang.reflect.InvocationTargetException;

public interface NodeConverter {
    <T> T convert(Node node, ContainerData<T> containerData) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
