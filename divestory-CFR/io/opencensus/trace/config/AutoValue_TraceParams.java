/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.config;

import io.opencensus.trace.Sampler;
import io.opencensus.trace.config.TraceParams;

final class AutoValue_TraceParams
extends TraceParams {
    private final int maxNumberOfAnnotations;
    private final int maxNumberOfAttributes;
    private final int maxNumberOfLinks;
    private final int maxNumberOfMessageEvents;
    private final Sampler sampler;

    private AutoValue_TraceParams(Sampler sampler, int n, int n2, int n3, int n4) {
        this.sampler = sampler;
        this.maxNumberOfAttributes = n;
        this.maxNumberOfAnnotations = n2;
        this.maxNumberOfMessageEvents = n3;
        this.maxNumberOfLinks = n4;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof TraceParams)) return false;
        if (!this.sampler.equals(((TraceParams)(object = (TraceParams)object)).getSampler())) return false;
        if (this.maxNumberOfAttributes != ((TraceParams)object).getMaxNumberOfAttributes()) return false;
        if (this.maxNumberOfAnnotations != ((TraceParams)object).getMaxNumberOfAnnotations()) return false;
        if (this.maxNumberOfMessageEvents != ((TraceParams)object).getMaxNumberOfMessageEvents()) return false;
        if (this.maxNumberOfLinks != ((TraceParams)object).getMaxNumberOfLinks()) return false;
        return bl;
    }

    @Override
    public int getMaxNumberOfAnnotations() {
        return this.maxNumberOfAnnotations;
    }

    @Override
    public int getMaxNumberOfAttributes() {
        return this.maxNumberOfAttributes;
    }

    @Override
    public int getMaxNumberOfLinks() {
        return this.maxNumberOfLinks;
    }

    @Override
    public int getMaxNumberOfMessageEvents() {
        return this.maxNumberOfMessageEvents;
    }

    @Override
    public Sampler getSampler() {
        return this.sampler;
    }

    public int hashCode() {
        return ((((this.sampler.hashCode() ^ 1000003) * 1000003 ^ this.maxNumberOfAttributes) * 1000003 ^ this.maxNumberOfAnnotations) * 1000003 ^ this.maxNumberOfMessageEvents) * 1000003 ^ this.maxNumberOfLinks;
    }

    @Override
    public TraceParams.Builder toBuilder() {
        return new Builder(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TraceParams{sampler=");
        stringBuilder.append(this.sampler);
        stringBuilder.append(", maxNumberOfAttributes=");
        stringBuilder.append(this.maxNumberOfAttributes);
        stringBuilder.append(", maxNumberOfAnnotations=");
        stringBuilder.append(this.maxNumberOfAnnotations);
        stringBuilder.append(", maxNumberOfMessageEvents=");
        stringBuilder.append(this.maxNumberOfMessageEvents);
        stringBuilder.append(", maxNumberOfLinks=");
        stringBuilder.append(this.maxNumberOfLinks);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static final class Builder
    extends TraceParams.Builder {
        private Integer maxNumberOfAnnotations;
        private Integer maxNumberOfAttributes;
        private Integer maxNumberOfLinks;
        private Integer maxNumberOfMessageEvents;
        private Sampler sampler;

        Builder() {
        }

        private Builder(TraceParams traceParams) {
            this.sampler = traceParams.getSampler();
            this.maxNumberOfAttributes = traceParams.getMaxNumberOfAttributes();
            this.maxNumberOfAnnotations = traceParams.getMaxNumberOfAnnotations();
            this.maxNumberOfMessageEvents = traceParams.getMaxNumberOfMessageEvents();
            this.maxNumberOfLinks = traceParams.getMaxNumberOfLinks();
        }

        @Override
        TraceParams autoBuild() {
            Object object = this.sampler;
            Object object2 = "";
            if (object == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("");
                ((StringBuilder)object2).append(" sampler");
                object2 = ((StringBuilder)object2).toString();
            }
            object = object2;
            if (this.maxNumberOfAttributes == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" maxNumberOfAttributes");
                object = ((StringBuilder)object).toString();
            }
            object2 = object;
            if (this.maxNumberOfAnnotations == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" maxNumberOfAnnotations");
                object2 = ((StringBuilder)object2).toString();
            }
            object = object2;
            if (this.maxNumberOfMessageEvents == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" maxNumberOfMessageEvents");
                object = ((StringBuilder)object).toString();
            }
            object2 = object;
            if (this.maxNumberOfLinks == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" maxNumberOfLinks");
                object2 = ((StringBuilder)object2).toString();
            }
            if (((String)object2).isEmpty()) {
                return new AutoValue_TraceParams(this.sampler, this.maxNumberOfAttributes, this.maxNumberOfAnnotations, this.maxNumberOfMessageEvents, this.maxNumberOfLinks);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Missing required properties:");
            ((StringBuilder)object).append((String)object2);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        @Override
        public TraceParams.Builder setMaxNumberOfAnnotations(int n) {
            this.maxNumberOfAnnotations = n;
            return this;
        }

        @Override
        public TraceParams.Builder setMaxNumberOfAttributes(int n) {
            this.maxNumberOfAttributes = n;
            return this;
        }

        @Override
        public TraceParams.Builder setMaxNumberOfLinks(int n) {
            this.maxNumberOfLinks = n;
            return this;
        }

        @Override
        public TraceParams.Builder setMaxNumberOfMessageEvents(int n) {
            this.maxNumberOfMessageEvents = n;
            return this;
        }

        @Override
        public TraceParams.Builder setSampler(Sampler sampler) {
            if (sampler == null) throw new NullPointerException("Null sampler");
            this.sampler = sampler;
            return this;
        }
    }

}

