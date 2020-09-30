package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Annotation {
   private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES = Collections.unmodifiableMap(Collections.emptyMap());

   Annotation() {
   }

   public static Annotation fromDescription(String var0) {
      return new AutoValue_Annotation(var0, EMPTY_ATTRIBUTES);
   }

   public static Annotation fromDescriptionAndAttributes(String var0, Map<String, AttributeValue> var1) {
      return new AutoValue_Annotation(var0, Collections.unmodifiableMap(new HashMap((Map)Utils.checkNotNull(var1, "attributes"))));
   }

   public abstract Map<String, AttributeValue> getAttributes();

   public abstract String getDescription();
}
