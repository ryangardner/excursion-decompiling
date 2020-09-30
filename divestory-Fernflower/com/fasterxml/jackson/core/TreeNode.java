package com.fasterxml.jackson.core;

import java.util.Iterator;

public interface TreeNode {
   JsonToken asToken();

   TreeNode at(JsonPointer var1);

   TreeNode at(String var1) throws IllegalArgumentException;

   Iterator<String> fieldNames();

   TreeNode get(int var1);

   TreeNode get(String var1);

   boolean isArray();

   boolean isContainerNode();

   boolean isMissingNode();

   boolean isObject();

   boolean isValueNode();

   JsonParser.NumberType numberType();

   TreeNode path(int var1);

   TreeNode path(String var1);

   int size();

   JsonParser traverse();

   JsonParser traverse(ObjectCodec var1);
}
