package com.google.api.client.util;

import java.util.Collection;

public final class Collections2 {
   private Collections2() {
   }

   static <T> Collection<T> cast(Iterable<T> var0) {
      return (Collection)var0;
   }
}
