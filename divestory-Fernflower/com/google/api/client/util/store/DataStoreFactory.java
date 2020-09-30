package com.google.api.client.util.store;

import java.io.IOException;
import java.io.Serializable;

public interface DataStoreFactory {
   <V extends Serializable> DataStore<V> getDataStore(String var1) throws IOException;
}