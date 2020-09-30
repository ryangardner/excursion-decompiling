package io.opencensus.stats;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.data.AttachmentValue;
import io.opencensus.tags.TagContext;

public abstract class MeasureMap {
   public abstract MeasureMap put(Measure.MeasureDouble var1, double var2);

   public abstract MeasureMap put(Measure.MeasureLong var1, long var2);

   public MeasureMap putAttachment(String var1, AttachmentValue var2) {
      Utils.checkNotNull(var1, "key");
      Utils.checkNotNull(var2, "value");
      return this;
   }

   @Deprecated
   public MeasureMap putAttachment(String var1, String var2) {
      return this.putAttachment(var1, (AttachmentValue)AttachmentValue.AttachmentValueString.create(var2));
   }

   public abstract void record();

   public abstract void record(TagContext var1);
}
