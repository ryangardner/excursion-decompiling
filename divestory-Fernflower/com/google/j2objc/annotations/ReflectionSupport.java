package com.google.j2objc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.PACKAGE})
public @interface ReflectionSupport {
   ReflectionSupport.Level value();

   public static enum Level {
      FULL,
      NATIVE_ONLY;

      static {
         ReflectionSupport.Level var0 = new ReflectionSupport.Level("FULL", 1);
         FULL = var0;
      }
   }
}
