package kotlin.jvm.internal;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\u0002J\f\u0010\f\u001a\u00020\u0004*\u00020\u0002H\u0014R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lkotlin/jvm/internal/ByteSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", "value", "", "toArray", "getSize", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class ByteSpreadBuilder extends PrimitiveSpreadBuilder<byte[]> {
   private final byte[] values;

   public ByteSpreadBuilder(int var1) {
      super(var1);
      this.values = new byte[var1];
   }

   public final void add(byte var1) {
      byte[] var2 = this.values;
      int var3 = this.getPosition();
      this.setPosition(var3 + 1);
      var2[var3] = (byte)var1;
   }

   protected int getSize(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "$this$getSize");
      return var1.length;
   }

   public final byte[] toArray() {
      return (byte[])this.toArray(this.values, new byte[this.size()]);
   }
}
