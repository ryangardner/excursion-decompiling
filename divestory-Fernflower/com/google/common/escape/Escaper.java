package com.google.common.escape;

import com.google.common.base.Function;
import com.google.errorprone.annotations.DoNotMock;

@DoNotMock("Use Escapers.nullEscaper() or another methods from the *Escapers classes")
public abstract class Escaper {
   private final Function<String, String> asFunction = new Function<String, String>() {
      public String apply(String var1) {
         return Escaper.this.escape(var1);
      }
   };

   protected Escaper() {
   }

   public final Function<String, String> asFunction() {
      return this.asFunction;
   }

   public abstract String escape(String var1);
}
