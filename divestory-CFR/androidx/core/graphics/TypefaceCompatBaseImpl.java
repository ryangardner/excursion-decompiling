/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.os.CancellationSignal
 *  android.util.Log
 */
package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.provider.FontsContractCompat;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

class TypefaceCompatBaseImpl {
    private static final int INVALID_KEY = 0;
    private static final String TAG = "TypefaceCompatBaseImpl";
    private ConcurrentHashMap<Long, FontResourcesParserCompat.FontFamilyFilesResourceEntry> mFontFamilies = new ConcurrentHashMap();

    TypefaceCompatBaseImpl() {
    }

    private void addFontFamily(Typeface typeface, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry) {
        long l = TypefaceCompatBaseImpl.getUniqueKey(typeface);
        if (l == 0L) return;
        this.mFontFamilies.put(l, fontFamilyFilesResourceEntry);
    }

    private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, int n) {
        return TypefaceCompatBaseImpl.findBestFont(fontFamilyFilesResourceEntry.getEntries(), n, new StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry>(){

            @Override
            public int getWeight(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
                return fontFileResourceEntry.getWeight();
            }

            @Override
            public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
                return fontFileResourceEntry.isItalic();
            }
        });
    }

    private static <T> T findBestFont(T[] arrT, int n, StyleExtractor<T> styleExtractor) {
        int n2 = (n & 1) == 0 ? 400 : 700;
        boolean bl = (n & 2) != 0;
        T t = null;
        int n3 = Integer.MAX_VALUE;
        int n4 = arrT.length;
        n = 0;
        while (n < n4) {
            int n5;
            block4 : {
                int n6;
                T t2;
                block3 : {
                    t2 = arrT[n];
                    n6 = Math.abs(styleExtractor.getWeight(t2) - n2);
                    n5 = styleExtractor.isItalic(t2) == bl ? 0 : 1;
                    n6 = n6 * 2 + n5;
                    if (t == null) break block3;
                    n5 = n3;
                    if (n3 <= n6) break block4;
                }
                t = t2;
                n5 = n6;
            }
            ++n;
            n3 = n5;
        }
        return t;
    }

    private static long getUniqueKey(Typeface typeface) {
        if (typeface == null) {
            return 0L;
        }
        try {
            Field field = Typeface.class.getDeclaredField("native_instance");
            field.setAccessible(true);
            return ((Number)field.get((Object)typeface)).longValue();
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Could not retrieve font from family.", (Throwable)illegalAccessException);
            return 0L;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)TAG, (String)"Could not retrieve font from family.", (Throwable)noSuchFieldException);
            return 0L;
        }
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int n) {
        FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = this.findBestEntry(fontFamilyFilesResourceEntry, n);
        if (fontFileResourceEntry == null) {
            return null;
        }
        context = TypefaceCompat.createFromResourcesFontFile(context, resources, fontFileResourceEntry.getResourceId(), fontFileResourceEntry.getFileName(), n);
        this.addFontFamily((Typeface)context, fontFamilyFilesResourceEntry);
        return context;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public Typeface createFromFontInfo(Context context, CancellationSignal object, FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        void var1_4;
        block7 : {
            int n2 = arrfontInfo.length;
            Object var6_11 = null;
            if (n2 < 1) {
                return null;
            }
            object = this.findBestInfo(arrfontInfo, n);
            object = context.getContentResolver().openInputStream(((FontsContractCompat.FontInfo)object).getUri());
            try {
                context = this.createFromInputStream(context, (InputStream)object);
            }
            catch (Throwable throwable) {
                break block7;
            }
            TypefaceCompatUtil.closeQuietly((Closeable)object);
            return context;
            catch (Throwable throwable) {
                object = var6_11;
            }
        }
        TypefaceCompatUtil.closeQuietly((Closeable)object);
        throw var1_4;
        catch (IOException iOException) {
            block8 : {
                object = null;
                break block8;
                catch (IOException iOException2) {}
            }
            TypefaceCompatUtil.closeQuietly((Closeable)object);
            return null;
        }
    }

    protected Typeface createFromInputStream(Context object, InputStream inputStream2) {
        block6 : {
            if ((object = TypefaceCompatUtil.getTempFile((Context)object)) == null) {
                return null;
            }
            boolean bl = TypefaceCompatUtil.copyToFile((File)object, inputStream2);
            if (bl) break block6;
            ((File)object).delete();
            return null;
        }
        try {
            inputStream2 = Typeface.createFromFile((String)((File)object).getPath());
            return inputStream2;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        catch (RuntimeException runtimeException) {
            return null;
        }
        finally {
            ((File)object).delete();
        }
    }

    public Typeface createFromResourcesFontFile(Context object, Resources resources, int n, String string2, int n2) {
        block6 : {
            if ((object = TypefaceCompatUtil.getTempFile((Context)object)) == null) {
                return null;
            }
            boolean bl = TypefaceCompatUtil.copyToFile((File)object, resources, n);
            if (bl) break block6;
            ((File)object).delete();
            return null;
        }
        try {
            resources = Typeface.createFromFile((String)((File)object).getPath());
            return resources;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        catch (RuntimeException runtimeException) {
            return null;
        }
        finally {
            ((File)object).delete();
        }
    }

    protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        return TypefaceCompatBaseImpl.findBestFont(arrfontInfo, n, new StyleExtractor<FontsContractCompat.FontInfo>(){

            @Override
            public int getWeight(FontsContractCompat.FontInfo fontInfo) {
                return fontInfo.getWeight();
            }

            @Override
            public boolean isItalic(FontsContractCompat.FontInfo fontInfo) {
                return fontInfo.isItalic();
            }
        });
    }

    FontResourcesParserCompat.FontFamilyFilesResourceEntry getFontFamily(Typeface typeface) {
        long l = TypefaceCompatBaseImpl.getUniqueKey(typeface);
        if (l != 0L) return this.mFontFamilies.get(l);
        return null;
    }

    private static interface StyleExtractor<T> {
        public int getWeight(T var1);

        public boolean isItalic(T var1);
    }

}

