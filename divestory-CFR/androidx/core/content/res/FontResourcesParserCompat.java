/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Base64
 *  android.util.Xml
 */
package androidx.core.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.R;
import androidx.core.provider.FontRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FontResourcesParserCompat {
    private static final int DEFAULT_TIMEOUT_MILLIS = 500;
    public static final int FETCH_STRATEGY_ASYNC = 1;
    public static final int FETCH_STRATEGY_BLOCKING = 0;
    public static final int INFINITE_TIMEOUT_VALUE = -1;
    private static final int ITALIC = 1;
    private static final int NORMAL_WEIGHT = 400;

    private FontResourcesParserCompat() {
    }

    private static int getType(TypedArray typedArray, int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            return typedArray.getType(n);
        }
        TypedValue typedValue = new TypedValue();
        typedArray.getValue(n, typedValue);
        return typedValue.type;
    }

    public static FamilyResourceEntry parse(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        int n;
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
        if (n != 2) throw new XmlPullParserException("No start tag found");
        return FontResourcesParserCompat.readFamilies(xmlPullParser, resources);
    }

    public static List<List<byte[]>> readCerts(Resources object, int n) {
        TypedArray typedArray;
        block9 : {
            if (n == 0) {
                return Collections.emptyList();
            }
            typedArray = object.obtainTypedArray(n);
            if (typedArray.length() != 0) break block9;
            object = Collections.emptyList();
            typedArray.recycle();
            return object;
        }
        ArrayList<List<byte[]>> arrayList = new ArrayList<List<byte[]>>();
        if (FontResourcesParserCompat.getType(typedArray, 0) != 1) {
            try {
                arrayList.add(FontResourcesParserCompat.toByteArrayList(object.getStringArray(n)));
                return arrayList;
            }
            finally {
                typedArray.recycle();
            }
        }
        n = 0;
        while (n < typedArray.length()) {
            int n2 = typedArray.getResourceId(n, 0);
            if (n2 != 0) {
                arrayList.add(FontResourcesParserCompat.toByteArrayList(object.getStringArray(n2)));
            }
            ++n;
        }
        return arrayList;
    }

    private static FamilyResourceEntry readFamilies(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        xmlPullParser.require(2, null, "font-family");
        if (xmlPullParser.getName().equals("font-family")) {
            return FontResourcesParserCompat.readFamily(xmlPullParser, resources);
        }
        FontResourcesParserCompat.skip(xmlPullParser);
        return null;
    }

    private static FamilyResourceEntry readFamily(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        TypedArray typedArray = resources.obtainAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), R.styleable.FontFamily);
        String string2 = typedArray.getString(R.styleable.FontFamily_fontProviderAuthority);
        String string3 = typedArray.getString(R.styleable.FontFamily_fontProviderPackage);
        Object object = typedArray.getString(R.styleable.FontFamily_fontProviderQuery);
        int n = typedArray.getResourceId(R.styleable.FontFamily_fontProviderCerts, 0);
        int n2 = typedArray.getInteger(R.styleable.FontFamily_fontProviderFetchStrategy, 1);
        int n3 = typedArray.getInteger(R.styleable.FontFamily_fontProviderFetchTimeout, 500);
        typedArray.recycle();
        if (string2 != null && string3 != null && object != null) {
            while (xmlPullParser.next() != 3) {
                FontResourcesParserCompat.skip(xmlPullParser);
            }
            return new ProviderResourceEntry(new FontRequest(string2, string3, (String)object, FontResourcesParserCompat.readCerts(resources, n)), n2, n3);
        }
        object = new ArrayList();
        do {
            if (xmlPullParser.next() == 3) {
                if (!object.isEmpty()) return new FontFamilyFilesResourceEntry(object.toArray(new FontFileResourceEntry[object.size()]));
                return null;
            }
            if (xmlPullParser.getEventType() != 2) continue;
            if (xmlPullParser.getName().equals("font")) {
                object.add(FontResourcesParserCompat.readFont(xmlPullParser, resources));
                continue;
            }
            FontResourcesParserCompat.skip(xmlPullParser);
        } while (true);
    }

    private static FontFileResourceEntry readFont(XmlPullParser xmlPullParser, Resources object) throws XmlPullParserException, IOException {
        TypedArray typedArray = object.obtainAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), R.styleable.FontFamilyFont);
        int n = typedArray.hasValue(R.styleable.FontFamilyFont_fontWeight) ? R.styleable.FontFamilyFont_fontWeight : R.styleable.FontFamilyFont_android_fontWeight;
        int n2 = typedArray.getInt(n, 400);
        n = typedArray.hasValue(R.styleable.FontFamilyFont_fontStyle) ? R.styleable.FontFamilyFont_fontStyle : R.styleable.FontFamilyFont_android_fontStyle;
        boolean bl = 1 == typedArray.getInt(n, 0);
        n = typedArray.hasValue(R.styleable.FontFamilyFont_ttcIndex) ? R.styleable.FontFamilyFont_ttcIndex : R.styleable.FontFamilyFont_android_ttcIndex;
        int n3 = typedArray.hasValue(R.styleable.FontFamilyFont_fontVariationSettings) ? R.styleable.FontFamilyFont_fontVariationSettings : R.styleable.FontFamilyFont_android_fontVariationSettings;
        object = typedArray.getString(n3);
        n3 = typedArray.getInt(n, 0);
        n = typedArray.hasValue(R.styleable.FontFamilyFont_font) ? R.styleable.FontFamilyFont_font : R.styleable.FontFamilyFont_android_font;
        int n4 = typedArray.getResourceId(n, 0);
        String string2 = typedArray.getString(n);
        typedArray.recycle();
        while (xmlPullParser.next() != 3) {
            FontResourcesParserCompat.skip(xmlPullParser);
        }
        return new FontFileResourceEntry(string2, n2, bl, (String)object, n3, n4);
    }

    private static void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n = 1;
        while (n > 0) {
            int n2 = xmlPullParser.next();
            if (n2 != 2) {
                if (n2 != 3) continue;
                --n;
                continue;
            }
            ++n;
        }
    }

    private static List<byte[]> toByteArrayList(String[] arrstring) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(Base64.decode((String)arrstring[n2], (int)0));
            ++n2;
        }
        return arrayList;
    }

    public static interface FamilyResourceEntry {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FetchStrategy {
    }

    public static final class FontFamilyFilesResourceEntry
    implements FamilyResourceEntry {
        private final FontFileResourceEntry[] mEntries;

        public FontFamilyFilesResourceEntry(FontFileResourceEntry[] arrfontFileResourceEntry) {
            this.mEntries = arrfontFileResourceEntry;
        }

        public FontFileResourceEntry[] getEntries() {
            return this.mEntries;
        }
    }

    public static final class FontFileResourceEntry {
        private final String mFileName;
        private boolean mItalic;
        private int mResourceId;
        private int mTtcIndex;
        private String mVariationSettings;
        private int mWeight;

        public FontFileResourceEntry(String string2, int n, boolean bl, String string3, int n2, int n3) {
            this.mFileName = string2;
            this.mWeight = n;
            this.mItalic = bl;
            this.mVariationSettings = string3;
            this.mTtcIndex = n2;
            this.mResourceId = n3;
        }

        public String getFileName() {
            return this.mFileName;
        }

        public int getResourceId() {
            return this.mResourceId;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public String getVariationSettings() {
            return this.mVariationSettings;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }
    }

    public static final class ProviderResourceEntry
    implements FamilyResourceEntry {
        private final FontRequest mRequest;
        private final int mStrategy;
        private final int mTimeoutMs;

        public ProviderResourceEntry(FontRequest fontRequest, int n, int n2) {
            this.mRequest = fontRequest;
            this.mStrategy = n;
            this.mTimeoutMs = n2;
        }

        public int getFetchStrategy() {
            return this.mStrategy;
        }

        public FontRequest getRequest() {
            return this.mRequest;
        }

        public int getTimeout() {
            return this.mTimeoutMs;
        }
    }

}

