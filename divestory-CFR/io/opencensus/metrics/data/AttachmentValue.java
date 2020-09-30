/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.data;

import io.opencensus.metrics.data.AutoValue_AttachmentValue_AttachmentValueString;

public abstract class AttachmentValue {
    public abstract String getValue();

    public static abstract class AttachmentValueString
    extends AttachmentValue {
        AttachmentValueString() {
        }

        public static AttachmentValueString create(String string2) {
            return new AutoValue_AttachmentValue_AttachmentValueString(string2);
        }
    }

}

