package kotlin.annotation;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0011\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011¨\u0006\u0012"},
   d2 = {"Lkotlin/annotation/AnnotationTarget;", "", "(Ljava/lang/String;I)V", "CLASS", "ANNOTATION_CLASS", "TYPE_PARAMETER", "PROPERTY", "FIELD", "LOCAL_VARIABLE", "VALUE_PARAMETER", "CONSTRUCTOR", "FUNCTION", "PROPERTY_GETTER", "PROPERTY_SETTER", "TYPE", "EXPRESSION", "FILE", "TYPEALIAS", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum AnnotationTarget {
   ANNOTATION_CLASS,
   CLASS,
   CONSTRUCTOR,
   EXPRESSION,
   FIELD,
   FILE,
   FUNCTION,
   LOCAL_VARIABLE,
   PROPERTY,
   PROPERTY_GETTER,
   PROPERTY_SETTER,
   TYPE,
   TYPEALIAS,
   TYPE_PARAMETER,
   VALUE_PARAMETER;

   static {
      AnnotationTarget var0 = new AnnotationTarget("CLASS", 0);
      CLASS = var0;
      AnnotationTarget var1 = new AnnotationTarget("ANNOTATION_CLASS", 1);
      ANNOTATION_CLASS = var1;
      AnnotationTarget var2 = new AnnotationTarget("TYPE_PARAMETER", 2);
      TYPE_PARAMETER = var2;
      AnnotationTarget var3 = new AnnotationTarget("PROPERTY", 3);
      PROPERTY = var3;
      AnnotationTarget var4 = new AnnotationTarget("FIELD", 4);
      FIELD = var4;
      AnnotationTarget var5 = new AnnotationTarget("LOCAL_VARIABLE", 5);
      LOCAL_VARIABLE = var5;
      AnnotationTarget var6 = new AnnotationTarget("VALUE_PARAMETER", 6);
      VALUE_PARAMETER = var6;
      AnnotationTarget var7 = new AnnotationTarget("CONSTRUCTOR", 7);
      CONSTRUCTOR = var7;
      AnnotationTarget var8 = new AnnotationTarget("FUNCTION", 8);
      FUNCTION = var8;
      AnnotationTarget var9 = new AnnotationTarget("PROPERTY_GETTER", 9);
      PROPERTY_GETTER = var9;
      AnnotationTarget var10 = new AnnotationTarget("PROPERTY_SETTER", 10);
      PROPERTY_SETTER = var10;
      AnnotationTarget var11 = new AnnotationTarget("TYPE", 11);
      TYPE = var11;
      AnnotationTarget var12 = new AnnotationTarget("EXPRESSION", 12);
      EXPRESSION = var12;
      AnnotationTarget var13 = new AnnotationTarget("FILE", 13);
      FILE = var13;
      AnnotationTarget var14 = new AnnotationTarget("TYPEALIAS", 14);
      TYPEALIAS = var14;
   }
}
