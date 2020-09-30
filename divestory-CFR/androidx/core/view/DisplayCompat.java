/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.UiModeManager
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.graphics.Point
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.view.Display
 *  android.view.Display$Mode
 */
package androidx.core.view;

import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import androidx.core.util.Preconditions;
import java.lang.reflect.Method;
import java.util.ArrayList;

public final class DisplayCompat {
    private static final int DISPLAY_SIZE_4K_HEIGHT = 2160;
    private static final int DISPLAY_SIZE_4K_WIDTH = 3840;

    private DisplayCompat() {
    }

    private static Point getPhysicalDisplaySize(Context context, Display display) {
        Point point = Build.VERSION.SDK_INT < 28 ? DisplayCompat.parsePhysicalDisplaySizeFromSystemProperties("sys.display-size", display) : DisplayCompat.parsePhysicalDisplaySizeFromSystemProperties("vendor.display-size", display);
        if (point != null) {
            return point;
        }
        if (DisplayCompat.isSonyBravia4kTv(context)) {
            return new Point(3840, 2160);
        }
        context = new Point();
        if (Build.VERSION.SDK_INT >= 23) {
            display = display.getMode();
            context.x = display.getPhysicalWidth();
            context.y = display.getPhysicalHeight();
            return context;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize((Point)context);
            return context;
        }
        display.getSize((Point)context);
        return context;
    }

    public static ModeCompat[] getSupportedModes(Context context, Display arrmode) {
        context = DisplayCompat.getPhysicalDisplaySize(context, (Display)arrmode);
        if (Build.VERSION.SDK_INT < 23) {
            return new ModeCompat[]{new ModeCompat((Point)context)};
        }
        arrmode = arrmode.getSupportedModes();
        ArrayList<ModeCompat> arrayList = new ArrayList<ModeCompat>(arrmode.length);
        int n = 0;
        boolean bl = false;
        do {
            if (n >= arrmode.length) {
                if (bl) return arrayList.toArray(new ModeCompat[0]);
                arrayList.add(new ModeCompat((Point)context));
                return arrayList.toArray(new ModeCompat[0]);
            }
            if (DisplayCompat.physicalSizeEquals(arrmode[n], (Point)context)) {
                arrayList.add(n, new ModeCompat(arrmode[n], true));
                bl = true;
            } else {
                arrayList.add(n, new ModeCompat(arrmode[n], false));
            }
            ++n;
        } while (true);
    }

    private static String getSystemProperty(String string2) {
        try {
            Class<?> class_ = Class.forName("android.os.SystemProperties");
            return (String)class_.getMethod("get", String.class).invoke(class_, string2);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static boolean isSonyBravia4kTv(Context context) {
        if (!DisplayCompat.isTv(context)) return false;
        if (!"Sony".equals(Build.MANUFACTURER)) return false;
        if (!Build.MODEL.startsWith("BRAVIA")) return false;
        if (!context.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd")) return false;
        return true;
    }

    private static boolean isTv(Context context) {
        if ((context = (UiModeManager)context.getSystemService("uimode")) == null) return false;
        if (context.getCurrentModeType() != 4) return false;
        return true;
    }

    private static Point parseDisplaySize(String arrstring) throws NumberFormatException {
        if ((arrstring = arrstring.trim().split("x", -1)).length != 2) throw new NumberFormatException();
        int n = Integer.parseInt(arrstring[0]);
        int n2 = Integer.parseInt(arrstring[1]);
        if (n <= 0) throw new NumberFormatException();
        if (n2 <= 0) throw new NumberFormatException();
        return new Point(n, n2);
    }

    private static Point parsePhysicalDisplaySizeFromSystemProperties(String string2, Display display) {
        if (display.getDisplayId() != 0) return null;
        if (TextUtils.isEmpty((CharSequence)(string2 = DisplayCompat.getSystemProperty(string2)))) return null;
        try {
            return DisplayCompat.parseDisplaySize(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private static boolean physicalSizeEquals(Display.Mode mode, Point point) {
        if (mode.getPhysicalWidth() == point.x) {
            if (mode.getPhysicalHeight() == point.y) return true;
        }
        if (mode.getPhysicalWidth() != point.y) return false;
        if (mode.getPhysicalHeight() != point.x) return false;
        return true;
    }

    public static final class ModeCompat {
        private final boolean mIsNative;
        private final Display.Mode mMode;
        private final Point mPhysicalDisplaySize;

        ModeCompat(Point point) {
            Preconditions.checkNotNull(point, "physicalDisplaySize == null");
            this.mIsNative = true;
            this.mPhysicalDisplaySize = point;
            this.mMode = null;
        }

        ModeCompat(Display.Mode mode, boolean bl) {
            Preconditions.checkNotNull(mode, "Display.Mode == null, can't wrap a null reference");
            this.mIsNative = bl;
            this.mPhysicalDisplaySize = new Point(mode.getPhysicalWidth(), mode.getPhysicalHeight());
            this.mMode = mode;
        }

        public int getPhysicalHeight() {
            return this.mPhysicalDisplaySize.y;
        }

        public int getPhysicalWidth() {
            return this.mPhysicalDisplaySize.x;
        }

        public boolean isNative() {
            return this.mIsNative;
        }

        public Display.Mode toMode() {
            return this.mMode;
        }
    }

}

