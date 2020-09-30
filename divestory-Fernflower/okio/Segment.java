package okio;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0000\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B/\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0000J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0000J\u0006\u0010\u0013\u001a\u00020\u0000J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0006J\u0006\u0010\u0016\u001a\u00020\u0000J\u0016\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0006R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u0004\u0018\u00010\u00008\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u0004\u0018\u00010\u00008\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"},
   d2 = {"Lokio/Segment;", "", "()V", "data", "", "pos", "", "limit", "shared", "", "owner", "([BIIZZ)V", "next", "prev", "compact", "", "pop", "push", "segment", "sharedCopy", "split", "byteCount", "unsharedCopy", "writeTo", "sink", "Companion", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Segment {
   public static final Segment.Companion Companion = new Segment.Companion((DefaultConstructorMarker)null);
   public static final int SHARE_MINIMUM = 1024;
   public static final int SIZE = 8192;
   public final byte[] data;
   public int limit;
   public Segment next;
   public boolean owner;
   public int pos;
   public Segment prev;
   public boolean shared;

   public Segment() {
      this.data = new byte[8192];
      this.owner = true;
      this.shared = false;
   }

   public Segment(byte[] var1, int var2, int var3, boolean var4, boolean var5) {
      Intrinsics.checkParameterIsNotNull(var1, "data");
      super();
      this.data = var1;
      this.pos = var2;
      this.limit = var3;
      this.shared = var4;
      this.owner = var5;
   }

   public final void compact() {
      Segment var1 = this.prev;
      Segment var2 = (Segment)this;
      byte var3 = 0;
      boolean var4;
      if (var1 != var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var2 = this.prev;
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         if (var2.owner) {
            int var5 = this.limit - this.pos;
            var2 = this.prev;
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            int var6 = var2.limit;
            var2 = this.prev;
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            int var7;
            if (var2.shared) {
               var7 = var3;
            } else {
               var2 = this.prev;
               if (var2 == null) {
                  Intrinsics.throwNpe();
               }

               var7 = var2.pos;
            }

            if (var5 <= 8192 - var6 + var7) {
               var2 = this.prev;
               if (var2 == null) {
                  Intrinsics.throwNpe();
               }

               this.writeTo(var2, var5);
               this.pop();
               SegmentPool.recycle(this);
            }
         }
      } else {
         throw (Throwable)(new IllegalStateException("cannot compact".toString()));
      }
   }

   public final Segment pop() {
      Segment var1 = this.next;
      if (var1 == (Segment)this) {
         var1 = null;
      }

      Segment var2 = this.prev;
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      var2.next = this.next;
      var2 = this.next;
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      var2.prev = this.prev;
      var2 = (Segment)null;
      this.next = var2;
      this.prev = var2;
      return var1;
   }

   public final Segment push(Segment var1) {
      Intrinsics.checkParameterIsNotNull(var1, "segment");
      var1.prev = (Segment)this;
      var1.next = this.next;
      Segment var2 = this.next;
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      var2.prev = var1;
      this.next = var1;
      return var1;
   }

   public final Segment sharedCopy() {
      this.shared = true;
      return new Segment(this.data, this.pos, this.limit, true, false);
   }

   public final Segment split(int var1) {
      boolean var2;
      if (var1 > 0 && var1 <= this.limit - this.pos) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         Segment var3;
         if (var1 >= 1024) {
            var3 = this.sharedCopy();
         } else {
            var3 = SegmentPool.take();
            byte[] var4 = this.data;
            byte[] var5 = var3.data;
            int var6 = this.pos;
            ArraysKt.copyInto$default(var4, var5, 0, var6, var6 + var1, 2, (Object)null);
         }

         var3.limit = var3.pos + var1;
         this.pos += var1;
         Segment var7 = this.prev;
         if (var7 == null) {
            Intrinsics.throwNpe();
         }

         var7.push(var3);
         return var3;
      } else {
         throw (Throwable)(new IllegalArgumentException("byteCount out of range".toString()));
      }
   }

   public final Segment unsharedCopy() {
      byte[] var1 = this.data;
      var1 = Arrays.copyOf(var1, var1.length);
      Intrinsics.checkExpressionValueIsNotNull(var1, "java.util.Arrays.copyOf(this, size)");
      return new Segment(var1, this.pos, this.limit, false, true);
   }

   public final void writeTo(Segment var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      if (var1.owner) {
         int var3 = var1.limit;
         int var4;
         byte[] var5;
         if (var3 + var2 > 8192) {
            if (var1.shared) {
               throw (Throwable)(new IllegalArgumentException());
            }

            var4 = var1.pos;
            if (var3 + var2 - var4 > 8192) {
               throw (Throwable)(new IllegalArgumentException());
            }

            var5 = var1.data;
            ArraysKt.copyInto$default(var5, var5, 0, var4, var3, 2, (Object)null);
            var1.limit -= var1.pos;
            var1.pos = 0;
         }

         var5 = this.data;
         byte[] var6 = var1.data;
         var4 = var1.limit;
         var3 = this.pos;
         ArraysKt.copyInto(var5, var6, var4, var3, var3 + var2);
         var1.limit += var2;
         this.pos += var2;
      } else {
         throw (Throwable)(new IllegalStateException("only owner can write".toString()));
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"},
      d2 = {"Lokio/Segment$Companion;", "", "()V", "SHARE_MINIMUM", "", "SIZE", "okio"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }
}
