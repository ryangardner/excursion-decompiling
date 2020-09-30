package kotlin.collections;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0004HÆ\u0003J\u000e\u0010\r\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\nJ(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0004HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n¨\u0006\u0016"},
   d2 = {"Lkotlin/collections/IndexedValue;", "T", "", "index", "", "value", "(ILjava/lang/Object;)V", "getIndex", "()I", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "copy", "(ILjava/lang/Object;)Lkotlin/collections/IndexedValue;", "equals", "", "other", "hashCode", "toString", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class IndexedValue<T> {
   private final int index;
   private final T value;

   public IndexedValue(int var1, T var2) {
      this.index = var1;
      this.value = var2;
   }

   // $FF: synthetic method
   public static IndexedValue copy$default(IndexedValue var0, int var1, Object var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.index;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.value;
      }

      return var0.copy(var1, var2);
   }

   public final int component1() {
      return this.index;
   }

   public final T component2() {
      return this.value;
   }

   public final IndexedValue<T> copy(int var1, T var2) {
      return new IndexedValue(var1, var2);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (!(var1 instanceof IndexedValue)) {
            return false;
         }

         IndexedValue var2 = (IndexedValue)var1;
         if (this.index != var2.index || !Intrinsics.areEqual(this.value, var2.value)) {
            return false;
         }
      }

      return true;
   }

   public final int getIndex() {
      return this.index;
   }

   public final T getValue() {
      return this.value;
   }

   public int hashCode() {
      int var1 = this.index;
      Object var2 = this.value;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         var3 = 0;
      }

      return var1 * 31 + var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("IndexedValue(index=");
      var1.append(this.index);
      var1.append(", value=");
      var1.append(this.value);
      var1.append(")");
      return var1.toString();
   }
}
