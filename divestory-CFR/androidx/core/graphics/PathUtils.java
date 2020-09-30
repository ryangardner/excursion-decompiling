/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Path
 *  android.graphics.PointF
 */
package androidx.core.graphics;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.core.graphics.PathSegment;
import java.util.ArrayList;
import java.util.Collection;

public final class PathUtils {
    private PathUtils() {
    }

    public static Collection<PathSegment> flatten(Path path) {
        return PathUtils.flatten(path, 0.5f);
    }

    public static Collection<PathSegment> flatten(Path arrf, float f) {
        arrf = arrf.approximate(f);
        int n = arrf.length / 3;
        ArrayList<PathSegment> arrayList = new ArrayList<PathSegment>(n);
        int n2 = 1;
        while (n2 < n) {
            int n3 = n2 * 3;
            int n4 = (n2 - 1) * 3;
            f = arrf[n3];
            float f2 = arrf[n3 + 1];
            float f3 = arrf[n3 + 2];
            float f4 = arrf[n4];
            float f5 = arrf[n4 + 1];
            float f6 = arrf[n4 + 2];
            if (f != f4 && (f2 != f5 || f3 != f6)) {
                arrayList.add(new PathSegment(new PointF(f5, f6), f4, new PointF(f2, f3), f));
            }
            ++n2;
        }
        return arrayList;
    }
}

