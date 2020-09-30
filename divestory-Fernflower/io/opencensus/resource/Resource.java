package io.opencensus.resource;

import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public abstract class Resource {
   private static final Map<String, String> ENV_LABEL_MAP = parseResourceLabels(System.getenv("OC_RESOURCE_LABELS"));
   @Nullable
   private static final String ENV_TYPE = parseResourceType(System.getenv("OC_RESOURCE_TYPE"));
   private static final String ERROR_MESSAGE_INVALID_CHARS = " should be a ASCII string with a length greater than 0 and not exceed 255 characters.";
   private static final String ERROR_MESSAGE_INVALID_VALUE = " should be a ASCII string with a length not exceed 255 characters.";
   private static final String LABEL_KEY_VALUE_SPLITTER = "=";
   private static final String LABEL_LIST_SPLITTER = ",";
   static final int MAX_LENGTH = 255;
   private static final String OC_RESOURCE_LABELS_ENV = "OC_RESOURCE_LABELS";
   private static final String OC_RESOURCE_TYPE_ENV = "OC_RESOURCE_TYPE";

   Resource() {
   }

   public static Resource create(@Nullable String var0, Map<String, String> var1) {
      return createInternal(var0, Collections.unmodifiableMap(new LinkedHashMap((Map)Utils.checkNotNull(var1, "labels"))));
   }

   public static Resource createFromEnvironmentVariables() {
      return createInternal(ENV_TYPE, ENV_LABEL_MAP);
   }

   private static Resource createInternal(@Nullable String var0, Map<String, String> var1) {
      return new AutoValue_Resource(var0, var1);
   }

   private static boolean isValid(String var0) {
      boolean var1;
      if (var0.length() <= 255 && StringUtils.isPrintableString(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isValidAndNotEmpty(String var0) {
      boolean var1;
      if (!var0.isEmpty() && isValid(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Nullable
   private static Resource merge(@Nullable Resource var0, @Nullable Resource var1) {
      if (var1 == null) {
         return var0;
      } else if (var0 == null) {
         return var1;
      } else {
         String var2;
         if (var0.getType() != null) {
            var2 = var0.getType();
         } else {
            var2 = var1.getType();
         }

         LinkedHashMap var5 = new LinkedHashMap(var1.getLabels());
         Iterator var4 = var0.getLabels().entrySet().iterator();

         while(var4.hasNext()) {
            Entry var3 = (Entry)var4.next();
            var5.put(var3.getKey(), var3.getValue());
         }

         return createInternal(var2, Collections.unmodifiableMap(var5));
      }
   }

   @Nullable
   public static Resource mergeResources(List<Resource> var0) {
      Iterator var1 = var0.iterator();

      Resource var2;
      for(var2 = null; var1.hasNext(); var2 = merge(var2, (Resource)var1.next())) {
      }

      return var2;
   }

   static Map<String, String> parseResourceLabels(@Nullable String var0) {
      if (var0 == null) {
         return Collections.emptyMap();
      } else {
         HashMap var1 = new HashMap();
         String[] var2 = var0.split(",", -1);
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String[] var5 = var2[var4].split("=", -1);
            if (var5.length == 2) {
               var0 = var5[0].trim();
               String var6 = var5[1].trim().replaceAll("^\"|\"$", "");
               Utils.checkArgument(isValidAndNotEmpty(var0), "Label key should be a ASCII string with a length greater than 0 and not exceed 255 characters.");
               Utils.checkArgument(isValid(var6), "Label value should be a ASCII string with a length not exceed 255 characters.");
               var1.put(var0, var6);
            }
         }

         return Collections.unmodifiableMap(var1);
      }
   }

   @Nullable
   static String parseResourceType(@Nullable String var0) {
      String var1 = var0;
      if (var0 != null) {
         var1 = var0;
         if (!var0.isEmpty()) {
            Utils.checkArgument(isValidAndNotEmpty(var0), "Type should be a ASCII string with a length greater than 0 and not exceed 255 characters.");
            var1 = var0.trim();
         }
      }

      return var1;
   }

   public abstract Map<String, String> getLabels();

   @Nullable
   public abstract String getType();
}
