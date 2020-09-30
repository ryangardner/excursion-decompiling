package com.google.api.client.util.store;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface DataStore<V extends Serializable> {
   DataStore<V> clear() throws IOException;

   boolean containsKey(String var1) throws IOException;

   boolean containsValue(V var1) throws IOException;

   DataStore<V> delete(String var1) throws IOException;

   V get(String var1) throws IOException;

   DataStoreFactory getDataStoreFactory();

   String getId();

   boolean isEmpty() throws IOException;

   Set<String> keySet() throws IOException;

   DataStore<V> set(String var1, V var2) throws IOException;

   int size() throws IOException;

   Collection<V> values() throws IOException;
}
