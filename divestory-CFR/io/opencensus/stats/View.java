/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Duration;
import io.opencensus.common.Function;
import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;
import io.opencensus.stats.Aggregation;
import io.opencensus.stats.AutoValue_View;
import io.opencensus.stats.AutoValue_View_AggregationWindow_Cumulative;
import io.opencensus.stats.AutoValue_View_AggregationWindow_Interval;
import io.opencensus.stats.AutoValue_View_Name;
import io.opencensus.stats.Measure;
import io.opencensus.tags.TagKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public abstract class View {
    static final int NAME_MAX_LENGTH = 255;
    private static final Comparator<TagKey> TAG_KEY_COMPARATOR = new Comparator<TagKey>(){

        @Override
        public int compare(TagKey tagKey, TagKey tagKey2) {
            return tagKey.getName().compareToIgnoreCase(tagKey2.getName());
        }
    };

    View() {
    }

    public static View create(Name name, String string2, Measure measure, Aggregation aggregation, List<TagKey> list) {
        boolean bl = new HashSet<TagKey>(list).size() == list.size();
        Utils.checkArgument(bl, "Columns have duplicate.");
        return View.create(name, string2, measure, aggregation, list, AggregationWindow.Cumulative.create());
    }

    @Deprecated
    public static View create(Name name, String string2, Measure measure, Aggregation aggregation, List<TagKey> list, AggregationWindow aggregationWindow) {
        boolean bl = new HashSet<TagKey>(list).size() == list.size();
        Utils.checkArgument(bl, "Columns have duplicate.");
        list = new ArrayList<TagKey>(list);
        Collections.sort(list, TAG_KEY_COMPARATOR);
        return new AutoValue_View(name, string2, measure, aggregation, Collections.unmodifiableList(list), aggregationWindow);
    }

    public abstract Aggregation getAggregation();

    public abstract List<TagKey> getColumns();

    public abstract String getDescription();

    public abstract Measure getMeasure();

    public abstract Name getName();

    @Deprecated
    public abstract AggregationWindow getWindow();

    @Deprecated
    public static abstract class AggregationWindow {
        private AggregationWindow() {
        }

        public abstract <T> T match(Function<? super Cumulative, T> var1, Function<? super Interval, T> var2, Function<? super AggregationWindow, T> var3);

        @Deprecated
        public static abstract class Cumulative
        extends AggregationWindow {
            private static final Cumulative CUMULATIVE = new AutoValue_View_AggregationWindow_Cumulative();

            Cumulative() {
            }

            public static Cumulative create() {
                return CUMULATIVE;
            }

            @Override
            public final <T> T match(Function<? super Cumulative, T> function, Function<? super Interval, T> function2, Function<? super AggregationWindow, T> function3) {
                return function.apply(this);
            }
        }

        @Deprecated
        public static abstract class Interval
        extends AggregationWindow {
            private static final Duration ZERO = Duration.create(0L, 0);

            Interval() {
            }

            public static Interval create(Duration duration) {
                boolean bl = duration.compareTo(ZERO) > 0;
                Utils.checkArgument(bl, "Duration must be positive");
                return new AutoValue_View_AggregationWindow_Interval(duration);
            }

            public abstract Duration getDuration();

            @Override
            public final <T> T match(Function<? super Cumulative, T> function, Function<? super Interval, T> function2, Function<? super AggregationWindow, T> function3) {
                return function2.apply(this);
            }
        }

    }

    public static abstract class Name {
        Name() {
        }

        public static Name create(String string2) {
            boolean bl = StringUtils.isPrintableString(string2) && string2.length() <= 255;
            Utils.checkArgument(bl, "Name should be a ASCII string with a length no greater than 255 characters.");
            return new AutoValue_View_Name(string2);
        }

        public abstract String asString();
    }

}

