package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"},
   d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(D)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class TestTimeSource extends AbstractLongTimeSource {
   private long reading;

   public TestTimeSource() {
      super(TimeUnit.NANOSECONDS);
   }

   private final void overflow_LRDsOJo/* $FF was: overflow-LRDsOJo*/(double var1) {
      StringBuilder var3 = new StringBuilder();
      var3.append("TestTimeSource will overflow if its reading ");
      var3.append(this.reading);
      var3.append("ns is advanced by ");
      var3.append(Duration.toString-impl(var1));
      var3.append('.');
      throw (Throwable)(new IllegalStateException(var3.toString()));
   }

   public final void plusAssign_LRDsOJo/* $FF was: plusAssign-LRDsOJo*/(double var1) {
      double var3 = Duration.toDouble-impl(var1, this.getUnit());
      long var5 = (long)var3;
      long var11;
      if (var5 != Long.MIN_VALUE && var5 != Long.MAX_VALUE) {
         long var7 = this.reading;
         long var9 = var7 + var5;
         var11 = var9;
         if ((var5 ^ var7) >= 0L) {
            var11 = var9;
            if ((var7 ^ var9) < 0L) {
               this.overflow-LRDsOJo(var1);
               var11 = var9;
            }
         }
      } else {
         var3 += (double)this.reading;
         if (var3 > (double)Long.MAX_VALUE || var3 < (double)Long.MIN_VALUE) {
            this.overflow-LRDsOJo(var1);
         }

         var11 = (long)var3;
      }

      this.reading = var11;
   }

   protected long read() {
      return this.reading;
   }
}
