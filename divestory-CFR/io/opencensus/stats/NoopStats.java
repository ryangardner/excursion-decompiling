/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Function;
import io.opencensus.common.Functions;
import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import io.opencensus.stats.Measure;
import io.opencensus.stats.MeasureMap;
import io.opencensus.stats.StatsCollectionState;
import io.opencensus.stats.StatsComponent;
import io.opencensus.stats.StatsRecorder;
import io.opencensus.stats.View;
import io.opencensus.stats.ViewData;
import io.opencensus.stats.ViewManager;
import io.opencensus.tags.TagContext;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

final class NoopStats {
    private NoopStats() {
    }

    static StatsRecorder getNoopStatsRecorder() {
        return NoopStatsRecorder.INSTANCE;
    }

    static MeasureMap newNoopMeasureMap() {
        return new NoopMeasureMap();
    }

    static StatsComponent newNoopStatsComponent() {
        return new NoopStatsComponent();
    }

    static ViewManager newNoopViewManager() {
        return new NoopViewManager();
    }

    private static final class NoopMeasureMap
    extends MeasureMap {
        private static final Logger logger = Logger.getLogger(NoopMeasureMap.class.getName());
        private boolean hasUnsupportedValues;

        private NoopMeasureMap() {
        }

        @Override
        public MeasureMap put(Measure.MeasureDouble measureDouble, double d) {
            if (!(d < 0.0)) return this;
            this.hasUnsupportedValues = true;
            return this;
        }

        @Override
        public MeasureMap put(Measure.MeasureLong measureLong, long l) {
            if (l >= 0L) return this;
            this.hasUnsupportedValues = true;
            return this;
        }

        @Override
        public void record() {
        }

        @Override
        public void record(TagContext tagContext) {
            Utils.checkNotNull(tagContext, "tags");
            if (!this.hasUnsupportedValues) return;
            logger.log(Level.WARNING, "Dropping values, value to record must be non-negative.");
        }
    }

    private static final class NoopStatsComponent
    extends StatsComponent {
        private volatile boolean isRead;
        private final ViewManager viewManager = NoopStats.newNoopViewManager();

        private NoopStatsComponent() {
        }

        @Override
        public StatsCollectionState getState() {
            this.isRead = true;
            return StatsCollectionState.DISABLED;
        }

        @Override
        public StatsRecorder getStatsRecorder() {
            return NoopStats.getNoopStatsRecorder();
        }

        @Override
        public ViewManager getViewManager() {
            return this.viewManager;
        }

        @Deprecated
        @Override
        public void setState(StatsCollectionState statsCollectionState) {
            Utils.checkNotNull(statsCollectionState, "state");
            Utils.checkState(this.isRead ^ true, "State was already read, cannot set state.");
        }
    }

    private static final class NoopStatsRecorder
    extends StatsRecorder {
        static final StatsRecorder INSTANCE = new NoopStatsRecorder();

        private NoopStatsRecorder() {
        }

        @Override
        public MeasureMap newMeasureMap() {
            return NoopStats.newNoopMeasureMap();
        }
    }

    private static final class NoopViewManager
    extends ViewManager {
        private static final Timestamp ZERO_TIMESTAMP = Timestamp.create(0L, 0);
        @Nullable
        private volatile Set<View> exportedViews;
        private final Map<View.Name, View> registeredViews = new HashMap<View.Name, View>();

        private NoopViewManager() {
        }

        private static Set<View> filterExportedViews(Collection<View> object) {
            HashSet<Object> hashSet = new HashSet<Object>();
            Iterator<View> iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                if (((View)object).getWindow() instanceof View.AggregationWindow.Interval) continue;
                hashSet.add(object);
            }
            return Collections.unmodifiableSet(hashSet);
        }

        @Override
        public Set<View> getAllExportedViews() {
            Object object = this.exportedViews;
            Set<View> set = object;
            if (object != null) return set;
            object = this.registeredViews;
            synchronized (object) {
                this.exportedViews = set = NoopViewManager.filterExportedViews(this.registeredViews.values());
                return set;
            }
        }

        @Nullable
        @Override
        public ViewData getView(View.Name object) {
            Utils.checkNotNull(object, "name");
            Map<View.Name, View> map = this.registeredViews;
            synchronized (map) {
                object = this.registeredViews.get(object);
                if (object != null) return ViewData.create((View)object, Collections.emptyMap(), ((View)object).getWindow().match(Functions.returnConstant(ViewData.AggregationWindowData.CumulativeData.create(ZERO_TIMESTAMP, ZERO_TIMESTAMP)), Functions.returnConstant(ViewData.AggregationWindowData.IntervalData.create(ZERO_TIMESTAMP)), Functions.throwAssertionError()));
                return null;
            }
        }

        @Override
        public void registerView(View view) {
            Utils.checkNotNull(view, "newView");
            Map<View.Name, View> map = this.registeredViews;
            synchronized (map) {
                this.exportedViews = null;
                View view2 = this.registeredViews.get(view.getName());
                boolean bl = view2 == null || view.equals(view2);
                Utils.checkArgument(bl, "A different view with the same name already exists.");
                if (view2 != null) return;
                this.registeredViews.put(view.getName(), view);
                return;
            }
        }
    }

}

