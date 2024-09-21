package ru.clevertec.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerData<T> {

    private Class<T> containerClass;

    private Type typeElementContainer;
    private Method addInContainer;
    private Class<?> containerClassImpl;



    public ContainerData<T> setData(AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException {
        ParameterizedType containerType = (ParameterizedType)container.getType();
        String typeName = containerType.getRawType().getTypeName();
        Class<?> typeCollection = Class.forName(typeName);
        containerClass = (Class<T>)typeCollection;

        typeElementContainer = containerType.getActualTypeArguments()[0];

        getImplementation(containerClass);

        return new ContainerData<>();
    }

    private void getImplementation(Class<?> clazz) throws NoSuchMethodException {
        if(clazz.equals(List.class)) {
            containerClassImpl = ArrayList.class;
            addInContainer = containerClassImpl.getMethod("add", Object.class);
        } else if (clazz.equals(Set.class)) {
            containerClassImpl = HashSet.class;
            addInContainer = containerClassImpl.getMethod("add", Object.class);
        }

    }
}
