package ru.clevertec.core;

import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.ValueNode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ConverterValueNode implements NodeConverter{
    @Override
    public <T> T convert(Node node, ContainerData<T> containerData) {
        ValueNode valueNode = (ValueNode) node;
        Class<T> containerClass = containerData.getContainerClass();

        return (T) parseValue(valueNode.getValue(), containerClass);
    }

    private Object parseValue(String value, Class<?> clazz) {
        value = value.trim();

        if (clazz.equals(String.class)) {
            return value;
        } else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (value.equals("null")) {
            return null;
        } else if (clazz.equals(int.class)|| clazz.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (clazz.equals(UUID.class)) {
            return UUID.fromString(value);
        } else if (clazz.equals(OffsetDateTime.class)) {
            return OffsetDateTime.parse(value);
        } else if (clazz.equals(BigDecimal.class)) {
            return new BigDecimal(value);
        } else if (clazz.equals(BigInteger.class)) {
            return new BigInteger(value);
        } else if (clazz.equals(LocalDate.class)) {
            return LocalDate.parse(value);
        } else if (clazz.equals(LocalDateTime.class)) {
            return LocalDateTime.parse(value);
        }
        throw new IllegalArgumentException();
    }
}
