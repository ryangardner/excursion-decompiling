/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.Link;
import io.opencensus.trace.export.SpanData;
import java.util.List;

final class AutoValue_SpanData_Links
extends SpanData.Links {
    private final int droppedLinksCount;
    private final List<Link> links;

    AutoValue_SpanData_Links(List<Link> list, int n) {
        if (list == null) throw new NullPointerException("Null links");
        this.links = list;
        this.droppedLinksCount = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpanData.Links)) return false;
        if (!this.links.equals(((SpanData.Links)(object = (SpanData.Links)object)).getLinks())) return false;
        if (this.droppedLinksCount != ((SpanData.Links)object).getDroppedLinksCount()) return false;
        return bl;
    }

    @Override
    public int getDroppedLinksCount() {
        return this.droppedLinksCount;
    }

    @Override
    public List<Link> getLinks() {
        return this.links;
    }

    public int hashCode() {
        return (this.links.hashCode() ^ 1000003) * 1000003 ^ this.droppedLinksCount;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Links{links=");
        stringBuilder.append(this.links);
        stringBuilder.append(", droppedLinksCount=");
        stringBuilder.append(this.droppedLinksCount);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

