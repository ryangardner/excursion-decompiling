/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ProviderInfo
 *  android.content.pm.Signature
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.CancellationSignal
 *  android.os.Handler
 *  android.provider.BaseColumns
 */
package androidx.core.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.provider.FontRequest;
import androidx.core.provider.SelfDestructiveThread;
import androidx.core.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FontsContractCompat {
    private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
    public static final String PARCEL_FONT_RESULTS = "font_results";
    static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
    static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
    private static final SelfDestructiveThread sBackgroundThread;
    private static final Comparator<byte[]> sByteArrayComparator;
    static final Object sLock;
    static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>> sPendingReplies;
    static final LruCache<String, Typeface> sTypefaceCache;

    static {
        sTypefaceCache = new LruCache(16);
        sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
        sLock = new Object();
        sPendingReplies = new SimpleArrayMap();
        sByteArrayComparator = new Comparator<byte[]>(){

            @Override
            public int compare(byte[] arrby, byte[] arrby2) {
                int n;
                int n2;
                if (arrby.length != arrby2.length) {
                    n2 = arrby.length;
                    n = arrby2.length;
                    return n2 - n;
                }
                n = 0;
                while (n < arrby.length) {
                    if (arrby[n] != arrby2[n]) {
                        n2 = arrby[n];
                        n = arrby2[n];
                        return n2 - n;
                    }
                    ++n;
                }
                return 0;
            }
        };
    }

    private FontsContractCompat() {
    }

    public static Typeface buildTypeface(Context context, CancellationSignal cancellationSignal, FontInfo[] arrfontInfo) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, arrfontInfo, 0);
    }

    private static List<byte[]> convertToByteArrayList(Signature[] arrsignature) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        int n = 0;
        while (n < arrsignature.length) {
            arrayList.add(arrsignature[n].toByteArray());
            ++n;
        }
        return arrayList;
    }

    private static boolean equalsByteArrayList(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        int n = 0;
        while (n < list.size()) {
            if (!Arrays.equals(list.get(n), list2.get(n))) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static FontFamilyResult fetchFonts(Context context, CancellationSignal cancellationSignal, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfo = FontsContractCompat.getProvider(context.getPackageManager(), fontRequest, context.getResources());
        if (providerInfo != null) return new FontFamilyResult(0, FontsContractCompat.getFontFromProvider(context, fontRequest, providerInfo.authority, cancellationSignal));
        return new FontFamilyResult(1, null);
    }

    private static List<List<byte[]>> getCertificates(FontRequest fontRequest, Resources resources) {
        if (fontRequest.getCertificates() == null) return FontResourcesParserCompat.readCerts(resources, fontRequest.getCertificatesArrayResId());
        return fontRequest.getCertificates();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static FontInfo[] getFontFromProvider(Context var0, FontRequest var1_2, String var2_3, CancellationSignal var3_4) {
        var4_5 = new ArrayList<E>();
        var5_8 = new Uri.Builder().scheme("content").authority(var2_3).build();
        var6_9 = new Uri.Builder().scheme("content").authority(var2_3).appendPath("file").build();
        var7_10 = null;
        var2_3 = var7_10;
        try {
            block15 : {
                if (Build.VERSION.SDK_INT > 16) {
                    var2_3 = var7_10;
                    var0 = var0.getContentResolver();
                    var2_3 = var7_10;
                    var1_2 = var1_2.getQuery();
                    var2_3 = var7_10;
                    var0 = var0.query(var5_8, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{var1_2}, null, (CancellationSignal)var3_4);
                } else {
                    var2_3 = var7_10;
                    var0 = var0.getContentResolver();
                    var2_3 = var7_10;
                    var1_2 = var1_2.getQuery();
                    var2_3 = var7_10;
                    var0 = var0.query(var5_8, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{var1_2}, null);
                }
                var1_2 = var4_5;
                if (var0 == null) break block15;
                var1_2 = var4_5;
                var2_3 = var0;
                if (var0.getCount() <= 0) break block15;
                var2_3 = var0;
                var8_11 = var0.getColumnIndex("result_code");
                var2_3 = var0;
                var2_3 = var0;
                var3_4 = new ArrayList();
                var2_3 = var0;
                var9_12 = var0.getColumnIndex("_id");
                var2_3 = var0;
                var10_13 = var0.getColumnIndex("file_id");
                var2_3 = var0;
                var11_14 = var0.getColumnIndex("font_ttc_index");
                var2_3 = var0;
                var12_15 = var0.getColumnIndex("font_weight");
                var2_3 = var0;
                var13_16 = var0.getColumnIndex("font_italic");
                do {
                    var2_3 = var0;
                    if (!var0.moveToNext()) break;
                    if (var8_11 != -1) {
                        var2_3 = var0;
                        var14_17 = var0.getInt(var8_11);
                    } else {
                        var14_17 = 0;
                    }
                    if (var11_14 != -1) {
                        var2_3 = var0;
                        var15_18 = var0.getInt(var11_14);
                    } else {
                        var15_18 = 0;
                    }
                    if (var10_13 == -1) {
                        var2_3 = var0;
                        var1_2 = ContentUris.withAppendedId((Uri)var5_8, (long)var0.getLong(var9_12));
                    } else {
                        var2_3 = var0;
                        var1_2 = ContentUris.withAppendedId((Uri)var6_9, (long)var0.getLong(var10_13));
                    }
                    if (var12_15 != -1) {
                        var2_3 = var0;
                        var16_19 = var0.getInt(var12_15);
                    } else {
                        var16_19 = 400;
                    }
                    if (var13_16 == -1) ** GOTO lbl-1000
                    var2_3 = var0;
                    if (var0.getInt(var13_16) == 1) {
                        var17_20 = true;
                    } else lbl-1000: // 2 sources:
                    {
                        var17_20 = false;
                    }
                    var2_3 = var0;
                    var2_3 = var0;
                    var4_7 = new FontInfo((Uri)var1_2, var15_18, var16_19, var17_20, var14_17);
                    var2_3 = var0;
                    var3_4.add(var4_7);
                } while (true);
                var1_2 = var3_4;
            }
            if (var0 == null) return var1_2.toArray(new FontInfo[0]);
        }
        catch (Throwable var0_1) {
            if (var2_3 == null) throw var0_1;
            var2_3.close();
            throw var0_1;
        }
        var0.close();
        return var1_2.toArray(new FontInfo[0]);
    }

    static TypefaceResult getFontInternal(Context context, FontRequest object, int n) {
        int n2;
        block2 : {
            try {
                object = FontsContractCompat.fetchFonts(context, null, (FontRequest)object);
                int n3 = ((FontFamilyResult)object).getStatusCode();
                n2 = -3;
                if (n3 != 0) break block2;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return new TypefaceResult(null, -1);
            }
            if ((context = TypefaceCompat.createFromFontInfo(context, null, ((FontFamilyResult)object).getFonts(), n)) == null) return new TypefaceResult((Typeface)context, n2);
            n2 = 0;
            return new TypefaceResult((Typeface)context, n2);
        }
        if (((FontFamilyResult)object).getStatusCode() != 1) return new TypefaceResult(null, n2);
        n2 = -2;
        return new TypefaceResult(null, n2);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public static Typeface getFontSync(Context object, FontRequest object2, ResourcesCompat.FontCallback object3, Handler object4, boolean bl, int n, int n2) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(((FontRequest)object2).getIdentifier());
        charSequence.append("-");
        charSequence.append(n2);
        charSequence = charSequence.toString();
        Typeface typeface = sTypefaceCache.get((String)charSequence);
        if (typeface != null) {
            if (object3 == null) return typeface;
            ((ResourcesCompat.FontCallback)object3).onFontRetrieved(typeface);
            return typeface;
        }
        if (bl && n == -1) {
            object = FontsContractCompat.getFontInternal((Context)object, (FontRequest)object2, n2);
            if (object3 == null) return ((TypefaceResult)object).mTypeface;
            if (((TypefaceResult)object).mResult == 0) {
                ((ResourcesCompat.FontCallback)object3).callbackSuccessAsync(((TypefaceResult)object).mTypeface, (Handler)object4);
                return ((TypefaceResult)object).mTypeface;
            }
            ((ResourcesCompat.FontCallback)object3).callbackFailAsync(((TypefaceResult)object).mResult, (Handler)object4);
            return ((TypefaceResult)object).mTypeface;
        }
        object2 = new Callable<TypefaceResult>((FontRequest)object2, n2, (String)charSequence){
            final /* synthetic */ String val$id;
            final /* synthetic */ FontRequest val$request;
            final /* synthetic */ int val$style;
            {
                this.val$request = fontRequest;
                this.val$style = n;
                this.val$id = string2;
            }

            @Override
            public TypefaceResult call() throws Exception {
                TypefaceResult typefaceResult = FontsContractCompat.getFontInternal(Context.this, this.val$request, this.val$style);
                if (typefaceResult.mTypeface == null) return typefaceResult;
                sTypefaceCache.put(this.val$id, typefaceResult.mTypeface);
                return typefaceResult;
            }
        };
        object = null;
        if (bl) {
            object2 = ((TypefaceResult)FontsContractCompat.sBackgroundThread.postAndWait(object2, (int)n)).mTypeface;
            return object2;
        }
        object = object3 == null ? null : new SelfDestructiveThread.ReplyCallback<TypefaceResult>((Handler)object4){
            final /* synthetic */ Handler val$handler;
            {
                this.val$handler = handler;
            }

            @Override
            public void onReply(TypefaceResult typefaceResult) {
                if (typefaceResult == null) {
                    FontCallback.this.callbackFailAsync(1, this.val$handler);
                    return;
                }
                if (typefaceResult.mResult == 0) {
                    FontCallback.this.callbackSuccessAsync(typefaceResult.mTypeface, this.val$handler);
                    return;
                }
                FontCallback.this.callbackFailAsync(typefaceResult.mResult, this.val$handler);
            }
        };
        object3 = sLock;
        // MONITORENTER : object3
        object4 = sPendingReplies.get(charSequence);
        if (object4 != null) {
            if (object != null) {
                ((ArrayList)object4).add(object);
            }
            // MONITOREXIT : object3
            return null;
        }
        if (object != null) {
            object4 = new ArrayList();
            ((ArrayList)object4).add(object);
            sPendingReplies.put((String)charSequence, (ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>)object4);
        }
        // MONITOREXIT : object3
        sBackgroundThread.postAndReply(object2, new SelfDestructiveThread.ReplyCallback<TypefaceResult>(){

            /*
             * Enabled unnecessary exception pruning
             */
            @Override
            public void onReply(TypefaceResult typefaceResult) {
                ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>> arrayList;
                Object object = sLock;
                synchronized (object) {
                    arrayList = sPendingReplies.get(String.this);
                    if (arrayList == null) {
                        return;
                    }
                    sPendingReplies.remove(String.this);
                }
                int n = 0;
                while (n < arrayList.size()) {
                    arrayList.get(n).onReply(typefaceResult);
                    ++n;
                }
            }
        });
        return null;
        catch (InterruptedException interruptedException) {
            return object;
        }
    }

    public static ProviderInfo getProvider(PackageManager object, FontRequest object2, Resources object3) throws PackageManager.NameNotFoundException {
        String string2 = ((FontRequest)object2).getProviderAuthority();
        int n = 0;
        ProviderInfo providerInfo = object.resolveContentProvider(string2, 0);
        if (providerInfo == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No package found for authority: ");
            ((StringBuilder)object).append(string2);
            throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
        }
        if (!providerInfo.packageName.equals(((FontRequest)object2).getProviderPackage())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Found content provider ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(", but package was not ");
            ((StringBuilder)object).append(((FontRequest)object2).getProviderPackage());
            throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
        }
        object = FontsContractCompat.convertToByteArrayList(object.getPackageInfo((String)providerInfo.packageName, (int)64).signatures);
        Collections.sort(object, sByteArrayComparator);
        object3 = FontsContractCompat.getCertificates((FontRequest)object2, (Resources)object3);
        while (n < object3.size()) {
            object2 = new ArrayList((Collection)object3.get(n));
            Collections.sort(object2, sByteArrayComparator);
            if (FontsContractCompat.equalsByteArrayList((List<byte[]>)object, (List<byte[]>)object2)) {
                return providerInfo;
            }
            ++n;
        }
        return null;
    }

    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] arrfontInfo, CancellationSignal cancellationSignal) {
        HashMap<FontInfo, ByteBuffer> hashMap = new HashMap<FontInfo, ByteBuffer>();
        int n = arrfontInfo.length;
        int n2 = 0;
        while (n2 < n) {
            FontInfo fontInfo = arrfontInfo[n2];
            if (fontInfo.getResultCode() == 0 && !hashMap.containsKey(fontInfo = fontInfo.getUri())) {
                hashMap.put(fontInfo, TypefaceCompatUtil.mmap(context, cancellationSignal, (Uri)fontInfo));
            }
            ++n2;
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static void requestFont(Context context, FontRequest fontRequest, FontRequestCallback fontRequestCallback, Handler handler) {
        FontsContractCompat.requestFontInternal(context.getApplicationContext(), fontRequest, fontRequestCallback, handler);
    }

    private static void requestFontInternal(Context context, final FontRequest fontRequest, FontRequestCallback fontRequestCallback, Handler handler) {
        handler.post(new Runnable(new Handler(), fontRequestCallback){
            final /* synthetic */ FontRequestCallback val$callback;
            final /* synthetic */ Handler val$callerThreadHandler;
            {
                this.val$callerThreadHandler = handler;
                this.val$callback = fontRequestCallback;
            }

            /*
             * Loose catch block
             * Enabled unnecessary exception pruning
             */
            @Override
            public void run() {
                int n;
                block10 : {
                    Typeface typeface;
                    int n2;
                    block7 : {
                        block8 : {
                            block9 : {
                                typeface = FontsContractCompat.fetchFonts(Context.this, null, fontRequest);
                                if (typeface.getStatusCode() == 0) break block7;
                                int n3 = typeface.getStatusCode();
                                if (n3 == 1) break block8;
                                if (n3 == 2) break block9;
                                {
                                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                                        this.val$callerThreadHandler.post(new Runnable(){

                                            @Override
                                            public void run() {
                                                val$callback.onTypefaceRequestFailed(-1);
                                            }
                                        });
                                        return;
                                    }
                                }
                                this.val$callerThreadHandler.post(new Runnable(){

                                    @Override
                                    public void run() {
                                        val$callback.onTypefaceRequestFailed(-3);
                                    }
                                });
                                return;
                            }
                            this.val$callerThreadHandler.post(new Runnable(){

                                @Override
                                public void run() {
                                    val$callback.onTypefaceRequestFailed(-3);
                                }
                            });
                            return;
                        }
                        this.val$callerThreadHandler.post(new Runnable(){

                            @Override
                            public void run() {
                                val$callback.onTypefaceRequestFailed(-2);
                            }
                        });
                        return;
                    }
                    if ((typeface = typeface.getFonts()) != null && ((FontInfo[])typeface).length != 0) {
                        n2 = ((FontInfo[])typeface).length;
                    } else {
                        this.val$callerThreadHandler.post(new Runnable(){

                            @Override
                            public void run() {
                                val$callback.onTypefaceRequestFailed(1);
                            }
                        });
                        return;
                    }
                    for (n = 0; n < n2; ++n) {
                        FontInfo fontInfo = typeface[n];
                        if (fontInfo.getResultCode() == 0) continue;
                        n = fontInfo.getResultCode();
                        if (n < 0) {
                            this.val$callerThreadHandler.post(new Runnable(){

                                @Override
                                public void run() {
                                    val$callback.onTypefaceRequestFailed(-3);
                                }
                            });
                            return;
                        }
                        break block10;
                    }
                    if ((typeface = FontsContractCompat.buildTypeface(Context.this, null, (FontInfo[])typeface)) == null) {
                        this.val$callerThreadHandler.post(new Runnable(){

                            @Override
                            public void run() {
                                val$callback.onTypefaceRequestFailed(-3);
                            }
                        });
                        return;
                    }
                    this.val$callerThreadHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            val$callback.onTypefaceRetrieved(typeface);
                        }
                    });
                    return;
                }
                this.val$callerThreadHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        val$callback.onTypefaceRequestFailed(n);
                    }
                });
            }

        });
    }

    public static void resetCache() {
        sTypefaceCache.evictAll();
    }

    public static final class Columns
    implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        public FontFamilyResult(int n, FontInfo[] arrfontInfo) {
            this.mStatusCode = n;
            this.mFonts = arrfontInfo;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }
    }

    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        public FontInfo(Uri uri, int n, int n2, boolean bl, int n3) {
            this.mUri = Preconditions.checkNotNull(uri);
            this.mTtcIndex = n;
            this.mWeight = n2;
            this.mItalic = bl;
            this.mResultCode = n3;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }
    }

    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
        public static final int RESULT_OK = 0;

        public void onTypefaceRequestFailed(int n) {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface FontRequestFailReason {
        }

    }

    private static final class TypefaceResult {
        final int mResult;
        final Typeface mTypeface;

        TypefaceResult(Typeface typeface, int n) {
            this.mTypeface = typeface;
            this.mResult = n;
        }
    }

}

