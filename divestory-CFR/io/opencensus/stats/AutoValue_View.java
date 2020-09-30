/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.Aggregation;
import io.opencensus.stats.Measure;
import io.opencensus.stats.View;
import io.opencensus.tags.TagKey;
import java.util.List;

final class AutoValue_View
extends View {
    private final Aggregation aggregation;
    private final List<TagKey> columns;
    private final String description;
    private final Measure measure;
    private final View.Name name;
    private final View.AggregationWindow window;

    AutoValue_View(View.Name name, String string2, Measure measure, Aggregation aggregation, List<TagKey> list, View.AggregationWindow aggregationWindow) {
        if (name == null) throw new NullPointerException("Null name");
        this.name = name;
        if (string2 == null) throw new NullPointerException("Null description");
        this.description = string2;
        if (measure == null) throw new NullPointerException("Null measure");
        this.measure = measure;
        if (aggregation == null) throw new NullPointerException("Null aggregation");
        this.aggregation = aggregation;
        if (list == null) throw new NullPointerException("Null columns");
        this.columns = list;
        if (aggregationWindow == null) throw new NullPointerException("Null window");
        this.window = aggregationWindow;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof View)) return false;
        if (!this.name.equals(((View)(object = (View)object)).getName())) return false;
        if (!this.description.equals(((View)object).getDescription())) return false;
        if (!this.measure.equals(((View)object).getMeasure())) return false;
        if (!this.aggregation.equals(((View)object).getAggregation())) return false;
        if (!this.columns.equals(((View)object).getColumns())) return false;
        if (!this.window.equals(((View)object).getWindow())) return false;
        return bl;
    }

    @Override
    public Aggregation getAggregation() {
        return this.aggregation;
    }

    @Override
    public List<TagKey> getColumns() {
        return this.columns;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Measure getMeasure() {
        return this.measure;
    }

    @Override
    public View.Name getName() {
        return this.name;
    }

    @Deprecated
    @Override
    public View.AggregationWindow getWindow() {
        return this.window;
    }

    public int hashCode() {
        return (((((this.name.hashCode() ^ 1000003) * 1000003 ^ this.description.hashCode()) * 1000003 ^ this.measure.hashCode()) * 1000003 ^ this.aggregation.hashCode()) * 1000003 ^ this.columns.hashCode()) * 1000003 ^ this.window.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View{name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", description=");
        stringBuilder.append(this.description);
        stringBuilder.append(", measure=");
        stringBuilder.append(this.measure);
        stringBuilder.append(", aggregation=");
        stringBuilder.append(this.aggregation);
        stringBuilder.append(", columns=");
        stringBuilder.append(this.columns);
        stringBuilder.append(", window=");
        stringBuilder.append(this.window);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

