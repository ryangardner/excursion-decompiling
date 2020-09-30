package io.opencensus.trace.export;

import io.opencensus.internal.Utils;
import io.opencensus.trace.Status;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public abstract class SampledSpanStore {
   protected SampledSpanStore() {
   }

   static SampledSpanStore newNoopSampledSpanStore() {
      return new SampledSpanStore.NoopSampledSpanStore();
   }

   public abstract Collection<SpanData> getErrorSampledSpans(SampledSpanStore.ErrorFilter var1);

   public abstract Collection<SpanData> getLatencySampledSpans(SampledSpanStore.LatencyFilter var1);

   public abstract Set<String> getRegisteredSpanNamesForCollection();

   public abstract SampledSpanStore.Summary getSummary();

   @Deprecated
   public abstract void registerSpanNamesForCollection(Collection<String> var1);

   @Deprecated
   public abstract void unregisterSpanNamesForCollection(Collection<String> var1);

   public abstract static class ErrorFilter {
      ErrorFilter() {
      }

      public static SampledSpanStore.ErrorFilter create(String var0, @Nullable Status.CanonicalCode var1, int var2) {
         boolean var3 = true;
         boolean var4;
         if (var1 != null) {
            if (var1 != Status.CanonicalCode.OK) {
               var4 = true;
            } else {
               var4 = false;
            }

            Utils.checkArgument(var4, "Invalid canonical code.");
         }

         if (var2 >= 0) {
            var4 = var3;
         } else {
            var4 = false;
         }

         Utils.checkArgument(var4, "Negative maxSpansToReturn.");
         return new AutoValue_SampledSpanStore_ErrorFilter(var0, var1, var2);
      }

      @Nullable
      public abstract Status.CanonicalCode getCanonicalCode();

      public abstract int getMaxSpansToReturn();

      public abstract String getSpanName();
   }

   public static enum LatencyBucketBoundaries {
      MICROSx100_MILLIx1(TimeUnit.MICROSECONDS.toNanos(100L), TimeUnit.MILLISECONDS.toNanos(1L)),
      MICROSx10_MICROSx100(TimeUnit.MICROSECONDS.toNanos(10L), TimeUnit.MICROSECONDS.toNanos(100L)),
      MILLIx100_SECONDx1(TimeUnit.MILLISECONDS.toNanos(100L), TimeUnit.SECONDS.toNanos(1L)),
      MILLIx10_MILLIx100(TimeUnit.MILLISECONDS.toNanos(10L), TimeUnit.MILLISECONDS.toNanos(100L)),
      MILLIx1_MILLIx10(TimeUnit.MILLISECONDS.toNanos(1L), TimeUnit.MILLISECONDS.toNanos(10L)),
      SECONDx100_MAX,
      SECONDx10_SECONDx100(TimeUnit.SECONDS.toNanos(10L), TimeUnit.SECONDS.toNanos(100L)),
      SECONDx1_SECONDx10(TimeUnit.SECONDS.toNanos(1L), TimeUnit.SECONDS.toNanos(10L)),
      ZERO_MICROSx10(0L, TimeUnit.MICROSECONDS.toNanos(10L));

      private final long latencyLowerNs;
      private final long latencyUpperNs;

      static {
         SampledSpanStore.LatencyBucketBoundaries var0 = new SampledSpanStore.LatencyBucketBoundaries("SECONDx100_MAX", 8, TimeUnit.SECONDS.toNanos(100L), Long.MAX_VALUE);
         SECONDx100_MAX = var0;
      }

      private LatencyBucketBoundaries(long var3, long var5) {
         this.latencyLowerNs = var3;
         this.latencyUpperNs = var5;
      }

      public long getLatencyLowerNs() {
         return this.latencyLowerNs;
      }

      public long getLatencyUpperNs() {
         return this.latencyUpperNs;
      }
   }

   public abstract static class LatencyFilter {
      LatencyFilter() {
      }

      public static SampledSpanStore.LatencyFilter create(String var0, long var1, long var3, int var5) {
         boolean var6 = true;
         boolean var7;
         if (var5 >= 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         Utils.checkArgument(var7, "Negative maxSpansToReturn.");
         if (var1 >= 0L) {
            var7 = true;
         } else {
            var7 = false;
         }

         Utils.checkArgument(var7, "Negative latencyLowerNs");
         if (var3 >= 0L) {
            var7 = var6;
         } else {
            var7 = false;
         }

         Utils.checkArgument(var7, "Negative latencyUpperNs");
         return new AutoValue_SampledSpanStore_LatencyFilter(var0, var1, var3, var5);
      }

      public abstract long getLatencyLowerNs();

      public abstract long getLatencyUpperNs();

      public abstract int getMaxSpansToReturn();

      public abstract String getSpanName();
   }

   private static final class NoopSampledSpanStore extends SampledSpanStore {
      private static final SampledSpanStore.PerSpanNameSummary EMPTY_PER_SPAN_NAME_SUMMARY = SampledSpanStore.PerSpanNameSummary.create(Collections.emptyMap(), Collections.emptyMap());
      private final Set<String> registeredSpanNames;

      private NoopSampledSpanStore() {
         this.registeredSpanNames = new HashSet();
      }

      // $FF: synthetic method
      NoopSampledSpanStore(Object var1) {
         this();
      }

      public Collection<SpanData> getErrorSampledSpans(SampledSpanStore.ErrorFilter var1) {
         Utils.checkNotNull(var1, "errorFilter");
         return Collections.emptyList();
      }

      public Collection<SpanData> getLatencySampledSpans(SampledSpanStore.LatencyFilter var1) {
         Utils.checkNotNull(var1, "latencyFilter");
         return Collections.emptyList();
      }

      public Set<String> getRegisteredSpanNamesForCollection() {
         // $FF: Couldn't be decompiled
      }

      public SampledSpanStore.Summary getSummary() {
         HashMap var1 = new HashMap();
         Set var2 = this.registeredSpanNames;
         synchronized(var2){}

         Throwable var10000;
         boolean var10001;
         label210: {
            Iterator var3;
            try {
               var3 = this.registeredSpanNames.iterator();
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label210;
            }

            while(true) {
               try {
                  if (var3.hasNext()) {
                     var1.put((String)var3.next(), EMPTY_PER_SPAN_NAME_SUMMARY);
                     continue;
                  }
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break;
               }

               try {
                  return SampledSpanStore.Summary.create(var1);
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break;
               }
            }
         }

         while(true) {
            Throwable var24 = var10000;

            try {
               throw var24;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               continue;
            }
         }
      }

      public void registerSpanNamesForCollection(Collection<String> param1) {
         // $FF: Couldn't be decompiled
      }

      public void unregisterSpanNamesForCollection(Collection<String> param1) {
         // $FF: Couldn't be decompiled
      }
   }

   public abstract static class PerSpanNameSummary {
      PerSpanNameSummary() {
      }

      public static SampledSpanStore.PerSpanNameSummary create(Map<SampledSpanStore.LatencyBucketBoundaries, Integer> var0, Map<Status.CanonicalCode, Integer> var1) {
         return new AutoValue_SampledSpanStore_PerSpanNameSummary(Collections.unmodifiableMap(new HashMap((Map)Utils.checkNotNull(var0, "numbersOfLatencySampledSpans"))), Collections.unmodifiableMap(new HashMap((Map)Utils.checkNotNull(var1, "numbersOfErrorSampledSpans"))));
      }

      public abstract Map<Status.CanonicalCode, Integer> getNumbersOfErrorSampledSpans();

      public abstract Map<SampledSpanStore.LatencyBucketBoundaries, Integer> getNumbersOfLatencySampledSpans();
   }

   public abstract static class Summary {
      Summary() {
      }

      public static SampledSpanStore.Summary create(Map<String, SampledSpanStore.PerSpanNameSummary> var0) {
         return new AutoValue_SampledSpanStore_Summary(Collections.unmodifiableMap(new HashMap((Map)Utils.checkNotNull(var0, "perSpanNameSummary"))));
      }

      public abstract Map<String, SampledSpanStore.PerSpanNameSummary> getPerSpanNameSummary();
   }
}
