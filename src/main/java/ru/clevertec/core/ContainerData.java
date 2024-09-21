package ru.clevertec.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerData<T> {

    private Class<T> clazz;
    private Type type;

}
