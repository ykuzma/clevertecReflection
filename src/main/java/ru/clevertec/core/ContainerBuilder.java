package ru.clevertec.core;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContainerBuilder<T> {

    private Class<T> containerClass;

    private Type[] typeElementInContainer;
    private Method addInContainer;
    private Class<?> containerClassImpl;

    public ContainerBuilder<T> setContainerClass(Class<T> clazz) {
        containerClass = clazz;
        return this;
    }

    public ContainerBuilder<T> setTypeElementInContainer(Type[] typeElement) {
        typeElementInContainer = typeElement;
        return this;
    }


    public ContainerData<T> build() throws NoSuchMethodException {

        getImplementation(containerClass);
        return new ContainerData<>(containerClass, typeElementInContainer, addInContainer, containerClassImpl);
    }

    private void getImplementation(Class<?> clazz) throws NoSuchMethodException {
        if(clazz.equals(List.class)) {
            containerClassImpl = ArrayList.class;
            addInContainer = containerClassImpl.getMethod("add", Object.class);
        } else if (clazz.equals(Set.class)) {
            containerClassImpl = HashSet.class;
            addInContainer = containerClassImpl.getMethod("add", Object.class);
        } else if (clazz.equals(Map.class)) {
            containerClassImpl = HashMap.class;
            addInContainer = containerClassImpl.getMethod("put", Object.class, Object.class);
        }

    }
}
