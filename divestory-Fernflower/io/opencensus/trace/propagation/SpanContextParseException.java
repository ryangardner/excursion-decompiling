package io.opencensus.trace.propagation;

public final class SpanContextParseException extends Exception {
   private static final long serialVersionUID = 0L;

   public SpanContextParseException(String var1) {
      super(var1);
   }

   public SpanContextParseException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
