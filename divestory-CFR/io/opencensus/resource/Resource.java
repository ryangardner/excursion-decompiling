/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.resource;

import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;
import io.opencensus.resource.AutoValue_Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class Resource {
    private static final Map<String, String> ENV_LABEL_MAP;
    @Nullable
    private static final String ENV_TYPE;
    private static final String ERROR_MESSAGE_INVALID_CHARS = " should be a ASCII string with a length greater than 0 and not exceed 255 characters.";
    private static final String ERROR_MESSAGE_INVALID_VALUE = " should be a ASCII string with a length not exceed 255 characters.";
    private static final String LABEL_KEY_VALUE_SPLITTER = "=";
    private static final String LABEL_LIST_SPLITTER = ",";
    static final int MAX_LENGTH = 255;
    private static final String OC_RESOURCE_LABELS_ENV = "OC_RESOURCE_LABELS";
    private static final String OC_RESOURCE_TYPE_ENV = "OC_RESOURCE_TYPE";

    static {
        ENV_TYPE = Resource.parseResourceType(System.getenv(OC_RESOURCE_TYPE_ENV));
        ENV_LABEL_MAP = Resource.parseResourceLabels(System.getenv(OC_RESOURCE_LABELS_ENV));
    }

    Resource() {
    }

    public static Resource create(@Nullable String string2, Map<String, String> map) {
        return Resource.createInternal(string2, Collections.unmodifiableMap(new LinkedHashMap<String, String>(Utils.checkNotNull(map, "labels"))));
    }

    public static Resource createFromEnvironmentVariables() {
        return Resource.createInternal(ENV_TYPE, ENV_LABEL_MAP);
    }

    private static Resource createInternal(@Nullable String string2, Map<String, String> map) {
        return new AutoValue_Resource(string2, map);
    }

    private static boolean isValid(String string2) {
        if (string2.length() > 255) return false;
        if (!StringUtils.isPrintableString(string2)) return false;
        return true;
    }

    private static boolean isValidAndNotEmpty(String string2) {
        if (string2.isEmpty()) return false;
        if (!Resource.isValid(string2)) return false;
        return true;
    }

    @Nullable
    private static Resource merge(@Nullable Resource object, @Nullable Resource object2) {
        if (object2 == null) {
            return object;
        }
        if (object == null) {
            return object2;
        }
        String string2 = ((Resource)object).getType() != null ? ((Resource)object).getType() : ((Resource)object2).getType();
        object2 = new LinkedHashMap<String, String>(((Resource)object2).getLabels());
        object = ((Resource)object).getLabels().entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            object2.put(entry.getKey(), entry.getValue());
        }
        return Resource.createInternal(string2, Collections.unmodifiableMap(object2));
    }

    @Nullable
    public static Resource mergeResources(List<Resource> object) {
        Iterator<Resource> iterator2 = object.iterator();
        object = null;
        while (iterator2.hasNext()) {
            object = Resource.merge(object, iterator2.next());
        }
        return object;
    }

    static Map<String, String> parseResourceLabels(@Nullable String string2) {
        if (string2 == null) {
            return Collections.emptyMap();
        }
        HashMap<String, String[]> hashMap = new HashMap<String, String[]>();
        String[] arrstring = string2.split(LABEL_LIST_SPLITTER, -1);
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            Object object = arrstring[n2].split(LABEL_KEY_VALUE_SPLITTER, -1);
            if (((String[])object).length == 2) {
                string2 = object[0].trim();
                object = object[1].trim().replaceAll("^\"|\"$", "");
                Utils.checkArgument(Resource.isValidAndNotEmpty(string2), "Label key should be a ASCII string with a length greater than 0 and not exceed 255 characters.");
                Utils.checkArgument(Resource.isValid((String)object), "Label value should be a ASCII string with a length not exceed 255 characters.");
                hashMap.put(string2, (String[])object);
            }
            ++n2;
        }
        return Collections.unmodifiableMap(hashMap);
    }

    @Nullable
    static String parseResourceType(@Nullable String string2) {
        String string3 = string2;
        if (string2 == null) return string3;
        string3 = string2;
        if (string2.isEmpty()) return string3;
        Utils.checkArgument(Resource.isValidAndNotEmpty(string2), "Type should be a ASCII string with a length greater than 0 and not exceed 255 characters.");
        return string2.trim();
    }

    public abstract Map<String, String> getLabels();

    @Nullable
    public abstract String getType();
}

