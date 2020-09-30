/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.data;

import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import io.opencensus.metrics.data.AttachmentValue;
import io.opencensus.metrics.data.AutoValue_Exemplar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class Exemplar {
    Exemplar() {
    }

    public static Exemplar create(double d, Timestamp timestamp, Map<String, AttachmentValue> object) {
        Utils.checkNotNull(object, "attachments");
        Map<String, AttachmentValue> map = Collections.unmodifiableMap(new HashMap<String, AttachmentValue>((Map<String, AttachmentValue>)object));
        Iterator<Map.Entry<String, AttachmentValue>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            Utils.checkNotNull(object.getKey(), "key of attachments");
            Utils.checkNotNull(object.getValue(), "value of attachments");
        }
        return new AutoValue_Exemplar(d, timestamp, map);
    }

    public abstract Map<String, AttachmentValue> getAttachments();

    public abstract Timestamp getTimestamp();

    public abstract double getValue();
}

