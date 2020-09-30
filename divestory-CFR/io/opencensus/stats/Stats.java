/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.internal.Provider;
import io.opencensus.stats.NoopStats;
import io.opencensus.stats.StatsCollectionState;
import io.opencensus.stats.StatsComponent;
import io.opencensus.stats.StatsRecorder;
import io.opencensus.stats.ViewManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Stats {
    private static final Logger logger = Logger.getLogger(Stats.class.getName());
    private static final StatsComponent statsComponent = Stats.loadStatsComponent(StatsComponent.class.getClassLoader());

    private Stats() {
    }

    public static StatsCollectionState getState() {
        return statsComponent.getState();
    }

    public static StatsRecorder getStatsRecorder() {
        return statsComponent.getStatsRecorder();
    }

    public static ViewManager getViewManager() {
        return statsComponent.getViewManager();
    }

    static StatsComponent loadStatsComponent(@Nullable ClassLoader object) {
        try {
            return Provider.createInstance(Class.forName("io.opencensus.impl.stats.StatsComponentImpl", true, (ClassLoader)object), StatsComponent.class);
        }
        catch (ClassNotFoundException classNotFoundException) {
            logger.log(Level.FINE, "Couldn't load full implementation for StatsComponent, now trying to load lite implementation.", classNotFoundException);
            try {
                return Provider.createInstance(Class.forName("io.opencensus.impllite.stats.StatsComponentImplLite", true, (ClassLoader)object), StatsComponent.class);
            }
            catch (ClassNotFoundException classNotFoundException2) {
                logger.log(Level.FINE, "Couldn't load lite implementation for StatsComponent, now using default implementation for StatsComponent.", classNotFoundException2);
                return NoopStats.newNoopStatsComponent();
            }
        }
    }

    @Deprecated
    public static void setState(StatsCollectionState statsCollectionState) {
        statsComponent.setState(statsCollectionState);
    }
}

