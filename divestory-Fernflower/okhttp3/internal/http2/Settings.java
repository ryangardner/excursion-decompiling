package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0011\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0086\u0002J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0004J\u000e\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0000J\u0019\u0010\t\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004H\u0086\u0002J\u0006\u0010\u001a\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"},
   d2 = {"Lokhttp3/internal/http2/Settings;", "", "()V", "headerTableSize", "", "getHeaderTableSize", "()I", "initialWindowSize", "getInitialWindowSize", "set", "values", "", "clear", "", "get", "id", "getEnablePush", "", "defaultValue", "getMaxConcurrentStreams", "getMaxFrameSize", "getMaxHeaderListSize", "isSet", "merge", "other", "value", "size", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Settings {
   public static final int COUNT = 10;
   public static final Settings.Companion Companion = new Settings.Companion((DefaultConstructorMarker)null);
   public static final int DEFAULT_INITIAL_WINDOW_SIZE = 65535;
   public static final int ENABLE_PUSH = 2;
   public static final int HEADER_TABLE_SIZE = 1;
   public static final int INITIAL_WINDOW_SIZE = 7;
   public static final int MAX_CONCURRENT_STREAMS = 4;
   public static final int MAX_FRAME_SIZE = 5;
   public static final int MAX_HEADER_LIST_SIZE = 6;
   private int set;
   private final int[] values = new int[10];

   public final void clear() {
      this.set = 0;
      ArraysKt.fill$default(this.values, 0, 0, 0, 6, (Object)null);
   }

   public final int get(int var1) {
      return this.values[var1];
   }

   public final boolean getEnablePush(boolean var1) {
      if ((this.set & 4) != 0) {
         if (this.values[2] == 1) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   public final int getHeaderTableSize() {
      int var1;
      if ((this.set & 2) != 0) {
         var1 = this.values[1];
      } else {
         var1 = -1;
      }

      return var1;
   }

   public final int getInitialWindowSize() {
      int var1;
      if ((this.set & 128) != 0) {
         var1 = this.values[7];
      } else {
         var1 = 65535;
      }

      return var1;
   }

   public final int getMaxConcurrentStreams() {
      int var1;
      if ((this.set & 16) != 0) {
         var1 = this.values[4];
      } else {
         var1 = Integer.MAX_VALUE;
      }

      return var1;
   }

   public final int getMaxFrameSize(int var1) {
      if ((this.set & 32) != 0) {
         var1 = this.values[5];
      }

      return var1;
   }

   public final int getMaxHeaderListSize(int var1) {
      if ((this.set & 64) != 0) {
         var1 = this.values[6];
      }

      return var1;
   }

   public final boolean isSet(int var1) {
      boolean var2 = true;
      if ((1 << var1 & this.set) == 0) {
         var2 = false;
      }

      return var2;
   }

   public final void merge(Settings var1) {
      Intrinsics.checkParameterIsNotNull(var1, "other");

      for(int var2 = 0; var2 < 10; ++var2) {
         if (var1.isSet(var2)) {
            this.set(var2, var1.get(var2));
         }
      }

   }

   public final Settings set(int var1, int var2) {
      if (var1 >= 0) {
         int[] var3 = this.values;
         if (var1 < var3.length) {
            this.set |= 1 << var1;
            var3[var1] = var2;
         }
      }

      return this;
   }

   public final int size() {
      return Integer.bitCount(this.set);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\f"},
      d2 = {"Lokhttp3/internal/http2/Settings$Companion;", "", "()V", "COUNT", "", "DEFAULT_INITIAL_WINDOW_SIZE", "ENABLE_PUSH", "HEADER_TABLE_SIZE", "INITIAL_WINDOW_SIZE", "MAX_CONCURRENT_STREAMS", "MAX_FRAME_SIZE", "MAX_HEADER_LIST_SIZE", "okhttp"},
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
