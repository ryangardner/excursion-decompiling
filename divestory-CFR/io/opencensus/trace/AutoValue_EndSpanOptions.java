/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.Status;
import javax.annotation.Nullable;

final class AutoValue_EndSpanOptions
extends EndSpanOptions {
    private final boolean sampleToLocalSpanStore;
    private final Status status;

    private AutoValue_EndSpanOptions(boolean bl, @Nullable Status status) {
        this.sampleToLocalSpanStore = bl;
        this.status = status;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof EndSpanOptions)) return false;
        if (this.sampleToLocalSpanStore != ((EndSpanOptions)(object = (EndSpanOptions)object)).getSampleToLocalSpanStore()) return false;
        Status status = this.status;
        if (status == null) {
            if (((EndSpanOptions)object).getStatus() != null) return false;
            return bl;
        }
        if (!status.equals(((EndSpanOptions)object).getStatus())) return false;
        return bl;
    }

    @Override
    public boolean getSampleToLocalSpanStore() {
        return this.sampleToLocalSpanStore;
    }

    @Nullable
    @Override
    public Status getStatus() {
        return this.status;
    }

    public int hashCode() {
        int n;
        int n2 = this.sampleToLocalSpanStore ? 1231 : 1237;
        Status status = this.status;
        if (status == null) {
            n = 0;
            return (n2 ^ 1000003) * 1000003 ^ n;
        }
        n = status.hashCode();
        return (n2 ^ 1000003) * 1000003 ^ n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EndSpanOptions{sampleToLocalSpanStore=");
        stringBuilder.append(this.sampleToLocalSpanStore);
        stringBuilder.append(", status=");
        stringBuilder.append(this.status);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static final class Builder
    extends EndSpanOptions.Builder {
        private Boolean sampleToLocalSpanStore;
        private Status status;

        Builder() {
        }

        @Override
        public EndSpanOptions build() {
            Comparable<Boolean> comparable = this.sampleToLocalSpanStore;
            CharSequence charSequence = "";
            if (comparable == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("");
                ((StringBuilder)charSequence).append(" sampleToLocalSpanStore");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            if (((String)charSequence).isEmpty()) {
                return new AutoValue_EndSpanOptions(this.sampleToLocalSpanStore, this.status);
            }
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append("Missing required properties:");
            ((StringBuilder)comparable).append((String)charSequence);
            throw new IllegalStateException(((StringBuilder)comparable).toString());
        }

        @Override
        public EndSpanOptions.Builder setSampleToLocalSpanStore(boolean bl) {
            this.sampleToLocalSpanStore = bl;
            return this;
        }

        @Override
        public EndSpanOptions.Builder setStatus(@Nullable Status status) {
            this.status = status;
            return this;
        }
    }

}

