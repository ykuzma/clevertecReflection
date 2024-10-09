package ru.clevertec.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.exception.DeserializationException;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerData<T> {

    private Class<T> containerClass;
    private Type[] typeElementInContainer;
    private Method addInContainer;
    private Class<?> containerClassImpl;

    public static class ContainerBuilder<T> {

        private Class<T> containerClass;
        private Type[] typeElementInContainer;
        private Method addInContainer;
        private Class<?> containerClassImpl;

        public ContainerBuilder(Class<T> containerClass) {
            this.containerClass = containerClass;
        }


        public static Class<?> extractGenericClass(Type type) {
            Class<?> clazz;
            try {
                if (type instanceof ParameterizedType parameterizedType) {
                    clazz = Class.forName(parameterizedType.getRawType().getTypeName());
                } else {
                    clazz = Class.forName(type.getTypeName());
                }
            } catch (ClassNotFoundException e) {
                throw new DeserializationException(e.getMessage(), e);
            }
            return clazz;
        }

        public ContainerBuilder<T> setContainerClass(Class<T> clazz) {
            containerClass = clazz;
            return this;
        }

        public ContainerBuilder<T> setTypeElementInContainer(Type typeElement) {
            if (typeElement instanceof ParameterizedType type) {
                typeElementInContainer = type.getActualTypeArguments();
            } else {
                typeElementInContainer = new Type[]{typeElement};
            }
            return this;
        }

        public ContainerData<T> build() {
            try {
                getImplementation(containerClass);
            } catch (NoSuchMethodException e) {
                throw new DeserializationException(e.getMessage(), e);
            }
            return new ContainerData<>(containerClass, typeElementInContainer, addInContainer, containerClassImpl);
        }

        private void getImplementation(Class<?> clazz) throws NoSuchMethodException {
            if (clazz.equals(List.class)) {
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

}
