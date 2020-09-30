/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.contrib.http.util;

import com.google.common.collect.ImmutableSet;
import io.opencensus.contrib.http.util.HttpViewConstants;
import io.opencensus.stats.Stats;
import io.opencensus.stats.View;
import io.opencensus.stats.ViewManager;
import java.util.Iterator;

public final class HttpViews {
    static final ImmutableSet<View> HTTP_CLIENT_VIEWS_SET;
    static final ImmutableSet<View> HTTP_SERVER_VIEWS_SET;

    static {
        HTTP_SERVER_VIEWS_SET = ImmutableSet.of(HttpViewConstants.HTTP_SERVER_COMPLETED_COUNT_VIEW, HttpViewConstants.HTTP_SERVER_SENT_BYTES_VIEW, HttpViewConstants.HTTP_SERVER_RECEIVED_BYTES_VIEW, HttpViewConstants.HTTP_SERVER_LATENCY_VIEW);
        HTTP_CLIENT_VIEWS_SET = ImmutableSet.of(HttpViewConstants.HTTP_CLIENT_COMPLETED_COUNT_VIEW, HttpViewConstants.HTTP_CLIENT_RECEIVED_BYTES_VIEW, HttpViewConstants.HTTP_CLIENT_SENT_BYTES_VIEW, HttpViewConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY_VIEW);
    }

    private HttpViews() {
    }

    public static final void registerAllClientViews() {
        HttpViews.registerAllClientViews(Stats.getViewManager());
    }

    static void registerAllClientViews(ViewManager viewManager) {
        Iterator iterator2 = HTTP_CLIENT_VIEWS_SET.iterator();
        while (iterator2.hasNext()) {
            viewManager.registerView((View)iterator2.next());
        }
    }

    public static final void registerAllServerViews() {
        HttpViews.registerAllServerViews(Stats.getViewManager());
    }

    static void registerAllServerViews(ViewManager viewManager) {
        Iterator iterator2 = HTTP_SERVER_VIEWS_SET.iterator();
        while (iterator2.hasNext()) {
            viewManager.registerView((View)iterator2.next());
        }
    }

    public static final void registerAllViews() {
        HttpViews.registerAllViews(Stats.getViewManager());
    }

    static void registerAllViews(ViewManager viewManager) {
        HttpViews.registerAllClientViews(viewManager);
        HttpViews.registerAllServerViews(viewManager);
    }
}

