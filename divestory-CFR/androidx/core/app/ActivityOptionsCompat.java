/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ActivityOptions
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.Pair
 *  android.view.View
 */
package androidx.core.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

public class ActivityOptionsCompat {
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";

    protected ActivityOptionsCompat() {
    }

    public static ActivityOptionsCompat makeBasic() {
        if (Build.VERSION.SDK_INT < 23) return new ActivityOptionsCompat();
        return new ActivityOptionsCompatImpl(ActivityOptions.makeBasic());
    }

    public static ActivityOptionsCompat makeClipRevealAnimation(View view, int n, int n2, int n3, int n4) {
        if (Build.VERSION.SDK_INT < 23) return new ActivityOptionsCompat();
        return new ActivityOptionsCompatImpl(ActivityOptions.makeClipRevealAnimation((View)view, (int)n, (int)n2, (int)n3, (int)n4));
    }

    public static ActivityOptionsCompat makeCustomAnimation(Context context, int n, int n2) {
        if (Build.VERSION.SDK_INT < 16) return new ActivityOptionsCompat();
        return new ActivityOptionsCompatImpl(ActivityOptions.makeCustomAnimation((Context)context, (int)n, (int)n2));
    }

    public static ActivityOptionsCompat makeScaleUpAnimation(View view, int n, int n2, int n3, int n4) {
        if (Build.VERSION.SDK_INT < 16) return new ActivityOptionsCompat();
        return new ActivityOptionsCompatImpl(ActivityOptions.makeScaleUpAnimation((View)view, (int)n, (int)n2, (int)n3, (int)n4));
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity, View view, String string2) {
        if (Build.VERSION.SDK_INT < 21) return new ActivityOptionsCompat();
        return new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation((Activity)activity, (View)view, (String)string2));
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity, androidx.core.util.Pair<View, String> ... arrpair) {
        if (Build.VERSION.SDK_INT < 21) return new ActivityOptionsCompat();
        Pair[] arrpair2 = null;
        if (arrpair == null) return new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation((Activity)activity, arrpair2));
        Pair[] arrpair3 = new Pair[arrpair.length];
        int n = 0;
        do {
            arrpair2 = arrpair3;
            if (n >= arrpair.length) return new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation((Activity)activity, arrpair2));
            arrpair3[n] = Pair.create(arrpair[n].first, arrpair[n].second);
            ++n;
        } while (true);
    }

    public static ActivityOptionsCompat makeTaskLaunchBehind() {
        if (Build.VERSION.SDK_INT < 21) return new ActivityOptionsCompat();
        return new ActivityOptionsCompatImpl(ActivityOptions.makeTaskLaunchBehind());
    }

    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int n, int n2) {
        if (Build.VERSION.SDK_INT < 16) return new ActivityOptionsCompat();
        return new ActivityOptionsCompatImpl(ActivityOptions.makeThumbnailScaleUpAnimation((View)view, (Bitmap)bitmap, (int)n, (int)n2));
    }

    public Rect getLaunchBounds() {
        return null;
    }

    public void requestUsageTimeReport(PendingIntent pendingIntent) {
    }

    public ActivityOptionsCompat setLaunchBounds(Rect rect) {
        return this;
    }

    public Bundle toBundle() {
        return null;
    }

    public void update(ActivityOptionsCompat activityOptionsCompat) {
    }

    private static class ActivityOptionsCompatImpl
    extends ActivityOptionsCompat {
        private final ActivityOptions mActivityOptions;

        ActivityOptionsCompatImpl(ActivityOptions activityOptions) {
            this.mActivityOptions = activityOptions;
        }

        @Override
        public Rect getLaunchBounds() {
            if (Build.VERSION.SDK_INT >= 24) return this.mActivityOptions.getLaunchBounds();
            return null;
        }

        @Override
        public void requestUsageTimeReport(PendingIntent pendingIntent) {
            if (Build.VERSION.SDK_INT < 23) return;
            this.mActivityOptions.requestUsageTimeReport(pendingIntent);
        }

        @Override
        public ActivityOptionsCompat setLaunchBounds(Rect rect) {
            if (Build.VERSION.SDK_INT >= 24) return new ActivityOptionsCompatImpl(this.mActivityOptions.setLaunchBounds(rect));
            return this;
        }

        @Override
        public Bundle toBundle() {
            return this.mActivityOptions.toBundle();
        }

        @Override
        public void update(ActivityOptionsCompat activityOptionsCompat) {
            if (!(activityOptionsCompat instanceof ActivityOptionsCompatImpl)) return;
            activityOptionsCompat = (ActivityOptionsCompatImpl)activityOptionsCompat;
            this.mActivityOptions.update(((ActivityOptionsCompatImpl)activityOptionsCompat).mActivityOptions);
        }
    }

}

