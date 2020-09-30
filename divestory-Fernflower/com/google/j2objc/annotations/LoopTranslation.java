package com.google.j2objc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.LOCAL_VARIABLE})
public @interface LoopTranslation {
   LoopTranslation.LoopStyle value();

   public static enum LoopStyle {
      FAST_ENUMERATION,
      JAVA_ITERATOR;

      static {
         LoopTranslation.LoopStyle var0 = new LoopTranslation.LoopStyle("FAST_ENUMERATION", 1);
         FAST_ENUMERATION = var0;
      }
   }
}
