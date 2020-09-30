package kotlin.annotation;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/annotation/AnnotationRetention;", "", "(Ljava/lang/String;I)V", "SOURCE", "BINARY", "RUNTIME", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum AnnotationRetention {
   BINARY,
   RUNTIME,
   SOURCE;

   static {
      AnnotationRetention var0 = new AnnotationRetention("SOURCE", 0);
      SOURCE = var0;
      AnnotationRetention var1 = new AnnotationRetention("BINARY", 1);
      BINARY = var1;
      AnnotationRetention var2 = new AnnotationRetention("RUNTIME", 2);
      RUNTIME = var2;
   }
}
