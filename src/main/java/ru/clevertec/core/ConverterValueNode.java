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


}
