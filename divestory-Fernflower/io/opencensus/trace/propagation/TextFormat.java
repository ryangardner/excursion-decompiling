package io.opencensus.trace.propagation;

import io.opencensus.internal.Utils;
import io.opencensus.trace.SpanContext;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class TextFormat {
   private static final TextFormat.NoopTextFormat NOOP_TEXT_FORMAT = new TextFormat.NoopTextFormat();

   static TextFormat getNoopTextFormat() {
      return NOOP_TEXT_FORMAT;
   }

   public abstract <C> SpanContext extract(C var1, TextFormat.Getter<C> var2) throws SpanContextParseException;

   public abstract List<String> fields();

   public abstract <C> void inject(SpanContext var1, C var2, TextFormat.Setter<C> var3);

   public abstract static class Getter<C> {
      @Nullable
      public abstract String get(C var1, String var2);
   }

   private static final class NoopTextFormat extends TextFormat {
      private NoopTextFormat() {
      }

      // $FF: synthetic method
      NoopTextFormat(Object var1) {
         this();
      }

      public <C> SpanContext extract(C var1, TextFormat.Getter<C> var2) {
         Utils.checkNotNull(var1, "carrier");
         Utils.checkNotNull(var2, "getter");
         return SpanContext.INVALID;
      }

      public List<String> fields() {
         return Collections.emptyList();
      }

      public <C> void inject(SpanContext var1, C var2, TextFormat.Setter<C> var3) {
         Utils.checkNotNull(var1, "spanContext");
         Utils.checkNotNull(var2, "carrier");
         Utils.checkNotNull(var3, "setter");
      }
   }

   public abstract static class Setter<C> {
      public abstract void put(C var1, String var2, String var3);
   }
}
