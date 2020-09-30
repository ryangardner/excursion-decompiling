package io.opencensus.trace.export;

import io.opencensus.internal.Utils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class RunningSpanStore {
   private static final RunningSpanStore NOOP_RUNNING_SPAN_STORE = new RunningSpanStore.NoopRunningSpanStore();

   protected RunningSpanStore() {
   }

   static RunningSpanStore getNoopRunningSpanStore() {
      return NOOP_RUNNING_SPAN_STORE;
   }

   public abstract Collection<SpanData> getRunningSpans(RunningSpanStore.Filter var1);

   public abstract RunningSpanStore.Summary getSummary();

   public abstract void setMaxNumberOfSpans(int var1);

   public abstract static class Filter {
      Filter() {
      }

      public static RunningSpanStore.Filter create(String var0, int var1) {
         boolean var2;
         if (var1 >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Utils.checkArgument(var2, "Negative maxSpansToReturn.");
         return new AutoValue_RunningSpanStore_Filter(var0, var1);
      }

      public abstract int getMaxSpansToReturn();

      public abstract String getSpanName();
   }

   private static final class NoopRunningSpanStore extends RunningSpanStore {
      private static final RunningSpanStore.Summary EMPTY_SUMMARY = RunningSpanStore.Summary.create(Collections.emptyMap());

      private NoopRunningSpanStore() {
      }

      // $FF: synthetic method
      NoopRunningSpanStore(Object var1) {
         this();
      }

      public Collection<SpanData> getRunningSpans(RunningSpanStore.Filter var1) {
         Utils.checkNotNull(var1, "filter");
         return Collections.emptyList();
      }

      public RunningSpanStore.Summary getSummary() {
         return EMPTY_SUMMARY;
      }

      public void setMaxNumberOfSpans(int var1) {
         boolean var2;
         if (var1 >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Utils.checkArgument(var2, "Invalid negative maxNumberOfElements");
      }
   }

   public abstract static class PerSpanNameSummary {
      PerSpanNameSummary() {
      }

      public static RunningSpanStore.PerSpanNameSummary create(int var0) {
         boolean var1;
         if (var0 >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         Utils.checkArgument(var1, "Negative numRunningSpans.");
         return new AutoValue_RunningSpanStore_PerSpanNameSummary(var0);
      }

      public abstract int getNumRunningSpans();
   }

   public abstract static class Summary {
      Summary() {
      }

      public static RunningSpanStore.Summary create(Map<String, RunningSpanStore.PerSpanNameSummary> var0) {
         return new AutoValue_RunningSpanStore_Summary(Collections.unmodifiableMap(new HashMap((Map)Utils.checkNotNull(var0, "perSpanNameSummary"))));
      }

      public abstract Map<String, RunningSpanStore.PerSpanNameSummary> getPerSpanNameSummary();
   }
}
