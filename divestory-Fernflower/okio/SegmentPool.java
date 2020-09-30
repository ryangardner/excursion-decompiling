package okio;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u000eH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0007J\b\u0010\u0014\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u001e\u0010\f\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u000e0\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000f¨\u0006\u0015"},
   d2 = {"Lokio/SegmentPool;", "", "()V", "HASH_BUCKET_COUNT", "", "LOCK", "Lokio/Segment;", "MAX_SIZE", "getMAX_SIZE", "()I", "byteCount", "getByteCount", "hashBuckets", "", "Ljava/util/concurrent/atomic/AtomicReference;", "[Ljava/util/concurrent/atomic/AtomicReference;", "firstRef", "recycle", "", "segment", "take", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class SegmentPool {
   private static final int HASH_BUCKET_COUNT;
   public static final SegmentPool INSTANCE = new SegmentPool();
   private static final Segment LOCK;
   private static final int MAX_SIZE = 65536;
   private static final AtomicReference<Segment>[] hashBuckets;

   static {
      int var0 = 0;
      LOCK = new Segment(new byte[0], 0, 0, false, false);
      int var1 = Integer.highestOneBit(Runtime.getRuntime().availableProcessors() * 2 - 1);
      HASH_BUCKET_COUNT = var1;

      AtomicReference[] var2;
      for(var2 = new AtomicReference[var1]; var0 < var1; ++var0) {
         var2[var0] = new AtomicReference();
      }

      hashBuckets = var2;
   }

   private SegmentPool() {
   }

   private final AtomicReference<Segment> firstRef() {
      Thread var1 = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(var1, "Thread.currentThread()");
      int var2 = (int)(var1.getId() & (long)HASH_BUCKET_COUNT - 1L);
      return hashBuckets[var2];
   }

   @JvmStatic
   public static final void recycle(Segment var0) {
      Intrinsics.checkParameterIsNotNull(var0, "segment");
      boolean var1;
      if (var0.next == null && var0.prev == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         if (!var0.shared) {
            AtomicReference var2 = INSTANCE.firstRef();
            Segment var3 = (Segment)var2.get();
            if (var3 != LOCK) {
               int var4;
               if (var3 != null) {
                  var4 = var3.limit;
               } else {
                  var4 = 0;
               }

               if (var4 < MAX_SIZE) {
                  var0.next = var3;
                  var0.pos = 0;
                  var0.limit = var4 + 8192;
                  if (!var2.compareAndSet(var3, var0)) {
                     var0.next = (Segment)null;
                  }

               }
            }
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
      }
   }

   @JvmStatic
   public static final Segment take() {
      AtomicReference var0 = INSTANCE.firstRef();
      Segment var1 = (Segment)var0.getAndSet(LOCK);
      if (var1 == LOCK) {
         return new Segment();
      } else if (var1 == null) {
         var0.set((Object)null);
         return new Segment();
      } else {
         var0.set(var1.next);
         var1.next = (Segment)null;
         var1.limit = 0;
         return var1;
      }
   }

   public final int getByteCount() {
      Segment var1 = (Segment)this.firstRef().get();
      return var1 != null ? var1.limit : 0;
   }

   public final int getMAX_SIZE() {
      return MAX_SIZE;
   }
}
