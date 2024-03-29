package kotlin.collections;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/collections/State;", "", "(Ljava/lang/String;I)V", "Ready", "NotReady", "Done", "Failed", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
enum State {
   Done,
   Failed,
   NotReady,
   Ready;

   static {
      State var0 = new State("Ready", 0);
      Ready = var0;
      State var1 = new State("NotReady", 1);
      NotReady = var1;
      State var2 = new State("Done", 2);
      Done = var2;
      State var3 = new State("Failed", 3);
      Failed = var3;
   }
}
