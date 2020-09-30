package io.opencensus.metrics.data;

public abstract class AttachmentValue {
   public abstract String getValue();

   public abstract static class AttachmentValueString extends AttachmentValue {
      AttachmentValueString() {
      }

      public static AttachmentValue.AttachmentValueString create(String var0) {
         return new AutoValue_AttachmentValue_AttachmentValueString(var0);
      }
   }
}
