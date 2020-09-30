package io.opencensus.metrics.data;

import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Exemplar {
   Exemplar() {
   }

   public static Exemplar create(double var0, Timestamp var2, Map<String, AttachmentValue> var3) {
      Utils.checkNotNull(var3, "attachments");
      Map var4 = Collections.unmodifiableMap(new HashMap(var3));
      Iterator var5 = var4.entrySet().iterator();

      while(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         Utils.checkNotNull(var6.getKey(), "key of attachments");
         Utils.checkNotNull(var6.getValue(), "value of attachments");
      }

      return new AutoValue_Exemplar(var0, var2, var4);
   }

   public abstract Map<String, AttachmentValue> getAttachments();

   public abstract Timestamp getTimestamp();

   public abstract double getValue();
}
