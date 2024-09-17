package ru.clevertec.core;

public abstract class Node {
    protected NodeType nodeType;

    protected Node(){

    }

    protected Node(NodeType type){
        nodeType = type;
    }

   public boolean isArray(){
       return nodeType == NodeType.ARRAY;
   }

   public boolean isObject() {
       return nodeType == NodeType.OBJECT;
   }

   public boolean isValue() {
       return nodeType == NodeType.VALUE;
   }

}
