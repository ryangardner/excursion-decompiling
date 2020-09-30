/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Timestamp;
import io.opencensus.stats.AggregationData;
import io.opencensus.stats.View;
import io.opencensus.stats.ViewData;
import io.opencensus.tags.TagValue;
import java.util.List;
import java.util.Map;

final class AutoValue_ViewData
extends ViewData {
    private final Map<List<TagValue>, AggregationData> aggregationMap;
    private final Timestamp end;
    private final Timestamp start;
    private final View view;
    private final ViewData.AggregationWindowData windowData;

    AutoValue_ViewData(View view, Map<List<TagValue>, AggregationData> map, ViewData.AggregationWindowData aggregationWindowData, Timestamp timestamp, Timestamp timestamp2) {
        if (view == null) throw new NullPointerException("Null view");
        this.view = view;
        if (map == null) throw new NullPointerException("Null aggregationMap");
        this.aggregationMap = map;
        if (aggregationWindowData == null) throw new NullPointerException("Null windowData");
        this.windowData = aggregationWindowData;
        if (timestamp == null) throw new NullPointerException("Null start");
        this.start = timestamp;
        if (timestamp2 == null) throw new NullPointerException("Null end");
        this.end = timestamp2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ViewData)) return false;
        if (!this.view.equals(((ViewData)(object = (ViewData)object)).getView())) return false;
        if (!this.aggregationMap.equals(((ViewData)object).getAggregationMap())) return false;
        if (!this.windowData.equals(((ViewData)object).getWindowData())) return false;
        if (!this.start.equals(((ViewData)object).getStart())) return false;
        if (!this.end.equals(((ViewData)object).getEnd())) return false;
        return bl;
    }

    @Override
    public Map<List<TagValue>, AggregationData> getAggregationMap() {
        return this.aggregationMap;
    }

    @Override
    public Timestamp getEnd() {
        return this.end;
    }

    @Override
    public Timestamp getStart() {
        return this.start;
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Deprecated
    @Override
    public ViewData.AggregationWindowData getWindowData() {
        return this.windowData;
    }

    public int hashCode() {
        return ((((this.view.hashCode() ^ 1000003) * 1000003 ^ this.aggregationMap.hashCode()) * 1000003 ^ this.windowData.hashCode()) * 1000003 ^ this.start.hashCode()) * 1000003 ^ this.end.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewData{view=");
        stringBuilder.append(this.view);
        stringBuilder.append(", aggregationMap=");
        stringBuilder.append(this.aggregationMap);
        stringBuilder.append(", windowData=");
        stringBuilder.append(this.windowData);
        stringBuilder.append(", start=");
        stringBuilder.append(this.start);
        stringBuilder.append(", end=");
        stringBuilder.append(this.end);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

