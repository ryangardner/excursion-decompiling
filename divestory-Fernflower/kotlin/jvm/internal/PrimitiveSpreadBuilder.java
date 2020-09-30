package kotlin.jvm.internal;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00028\u0000¢\u0006\u0002\u0010\u0012J\b\u0010\u0003\u001a\u00020\u0004H\u0004J\u001d\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\u0004*\u00028\u0000H$¢\u0006\u0002\u0010\u0018R\u001a\u0010\u0006\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000bX\u0082\u0004¢\u0006\n\n\u0002\u0010\u000e\u0012\u0004\b\f\u0010\r¨\u0006\u0019"},
   d2 = {"Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "T", "", "size", "", "(I)V", "position", "getPosition", "()I", "setPosition", "spreads", "", "spreads$annotations", "()V", "[Ljava/lang/Object;", "addSpread", "", "spreadArgument", "(Ljava/lang/Object;)V", "toArray", "values", "result", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getSize", "(Ljava/lang/Object;)I", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class PrimitiveSpreadBuilder<T> {
   private int position;
   private final int size;
   private final T[] spreads;

   public PrimitiveSpreadBuilder(int var1) {
      this.size = var1;
      this.spreads = new Object[var1];
   }

   // $FF: synthetic method
   private static void spreads$annotations() {
   }

   public final void addSpread(T var1) {
      Intrinsics.checkParameterIsNotNull(var1, "spreadArgument");
      Object[] var2 = this.spreads;
      int var3 = this.position++;
      var2[var3] = var1;
   }

   protected final int getPosition() {
      return this.position;
   }

   protected abstract int getSize(T var1);

   protected final void setPosition(int var1) {
      this.position = var1;
   }

   protected final int size() {
      int var1 = this.size - 1;
      int var2 = 0;
      int var3 = 0;
      if (var1 >= 0) {
         int var4 = 0;

         while(true) {
            Object var5 = this.spreads[var4];
            if (var5 != null) {
               var2 = this.getSize(var5);
            } else {
               var2 = 1;
            }

            var3 += var2;
            var2 = var3;
            if (var4 == var1) {
               break;
            }

            ++var4;
         }
      }

      return var2;
   }

   protected final T toArray(T var1, T var2) {
      Intrinsics.checkParameterIsNotNull(var1, "values");
      Intrinsics.checkParameterIsNotNull(var2, "result");
      int var3 = this.size - 1;
      int var4 = 0;
      int var8;
      int var9;
      if (var3 >= 0) {
         int var5 = 0;
         int var6 = 0;
         var4 = 0;

         while(true) {
            Object var7 = this.spreads[var5];
            var8 = var6;
            var9 = var4;
            if (var7 != null) {
               var9 = var4;
               if (var6 < var5) {
                  var9 = var5 - var6;
                  System.arraycopy(var1, var6, var2, var4, var9);
                  var9 += var4;
               }

               var4 = this.getSize(var7);
               System.arraycopy(var7, 0, var2, var9, var4);
               var9 += var4;
               var8 = var5 + 1;
            }

            if (var5 == var3) {
               var4 = var8;
               break;
            }

            ++var5;
            var6 = var8;
            var4 = var9;
         }
      } else {
         var9 = 0;
      }

      var8 = this.size;
      if (var4 < var8) {
         System.arraycopy(var1, var4, var2, var9, var8 - var4);
      }

      return var2;
   }
}
