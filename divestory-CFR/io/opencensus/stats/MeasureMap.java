/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.data.AttachmentValue;
import io.opencensus.stats.Measure;
import io.opencensus.tags.TagContext;

public abstract class MeasureMap {
    public abstract MeasureMap put(Measure.MeasureDouble var1, double var2);

    public abstract MeasureMap put(Measure.MeasureLong var1, long var2);

    public MeasureMap putAttachment(String string2, AttachmentValue attachmentValue) {
        Utils.checkNotNull(string2, "key");
        Utils.checkNotNull(attachmentValue, "value");
        return this;
    }

    @Deprecated
    public MeasureMap putAttachment(String string2, String string3) {
        return this.putAttachment(string2, AttachmentValue.AttachmentValueString.create(string3));
    }

    public abstract void record();

    public abstract void record(TagContext var1);
}

