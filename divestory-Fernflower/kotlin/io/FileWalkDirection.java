package kotlin.io;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"},
   d2 = {"Lkotlin/io/FileWalkDirection;", "", "(Ljava/lang/String;I)V", "TOP_DOWN", "BOTTOM_UP", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum FileWalkDirection {
   BOTTOM_UP,
   TOP_DOWN;

   static {
      FileWalkDirection var0 = new FileWalkDirection("TOP_DOWN", 0);
      TOP_DOWN = var0;
      FileWalkDirection var1 = new FileWalkDirection("BOTTOM_UP", 1);
      BOTTOM_UP = var1;
   }
}
