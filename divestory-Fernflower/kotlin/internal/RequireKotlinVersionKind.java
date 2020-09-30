package kotlin.internal;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0081\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/internal/RequireKotlinVersionKind;", "", "(Ljava/lang/String;I)V", "LANGUAGE_VERSION", "COMPILER_VERSION", "API_VERSION", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum RequireKotlinVersionKind {
   API_VERSION,
   COMPILER_VERSION,
   LANGUAGE_VERSION;

   static {
      RequireKotlinVersionKind var0 = new RequireKotlinVersionKind("LANGUAGE_VERSION", 0);
      LANGUAGE_VERSION = var0;
      RequireKotlinVersionKind var1 = new RequireKotlinVersionKind("COMPILER_VERSION", 1);
      COMPILER_VERSION = var1;
      RequireKotlinVersionKind var2 = new RequireKotlinVersionKind("API_VERSION", 2);
      API_VERSION = var2;
   }
}
