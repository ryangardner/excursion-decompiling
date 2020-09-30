package com.google.api.client.json;

import java.lang.reflect.Field;
import java.util.Collection;

public class CustomizeJsonParser {
   public void handleUnrecognizedKey(Object var1, String var2) {
   }

   public Collection<Object> newInstanceForArray(Object var1, Field var2) {
      return null;
   }

   public Object newInstanceForObject(Object var1, Class<?> var2) {
      return null;
   }

   public boolean stopAt(Object var1, String var2) {
      return false;
   }
}
