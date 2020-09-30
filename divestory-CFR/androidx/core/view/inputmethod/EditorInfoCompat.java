/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.inputmethod.EditorInfo
 */
package androidx.core.view.inputmethod;

import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

public final class EditorInfoCompat {
    private static final String CONTENT_MIME_TYPES_INTEROP_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
    private static final String CONTENT_MIME_TYPES_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
    public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;

    public static String[] getContentMimeTypes(EditorInfo arrstring) {
        String[] arrstring2;
        if (Build.VERSION.SDK_INT >= 25) {
            arrstring = arrstring.contentMimeTypes;
            if (arrstring == null) return EMPTY_STRING_ARRAY;
            return arrstring;
        }
        if (arrstring.extras == null) {
            return EMPTY_STRING_ARRAY;
        }
        String[] arrstring3 = arrstring2 = arrstring.extras.getStringArray(CONTENT_MIME_TYPES_KEY);
        if (arrstring2 == null) {
            arrstring3 = arrstring.extras.getStringArray(CONTENT_MIME_TYPES_INTEROP_KEY);
        }
        if (arrstring3 == null) return EMPTY_STRING_ARRAY;
        return arrstring3;
    }

    static int getProtocol(EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT >= 25) {
            return 1;
        }
        if (editorInfo.extras == null) {
            return 0;
        }
        boolean bl = editorInfo.extras.containsKey(CONTENT_MIME_TYPES_KEY);
        boolean bl2 = editorInfo.extras.containsKey(CONTENT_MIME_TYPES_INTEROP_KEY);
        if (bl && bl2) {
            return 4;
        }
        if (bl) {
            return 3;
        }
        if (!bl2) return 0;
        return 2;
    }

    public static void setContentMimeTypes(EditorInfo editorInfo, String[] arrstring) {
        if (Build.VERSION.SDK_INT >= 25) {
            editorInfo.contentMimeTypes = arrstring;
            return;
        }
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        editorInfo.extras.putStringArray(CONTENT_MIME_TYPES_KEY, arrstring);
        editorInfo.extras.putStringArray(CONTENT_MIME_TYPES_INTEROP_KEY, arrstring);
    }
}

