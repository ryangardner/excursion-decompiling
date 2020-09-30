/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ClipDescription
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.text.TextUtils
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputConnectionWrapper
 *  android.view.inputmethod.InputContentInfo
 */
package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;

public final class InputConnectionCompat {
    private static final String COMMIT_CONTENT_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_FLAGS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_FLAGS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_INTEROP_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_LINK_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_LINK_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_OPTS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_OPTS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    private static final String COMMIT_CONTENT_RESULT_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    public static boolean commitContent(InputConnection inputConnection, EditorInfo object, InputContentInfoCompat inputContentInfoCompat, int n, Bundle bundle) {
        int n2;
        int n3;
        Bundle bundle2;
        int n4;
        block9 : {
            ClipDescription clipDescription = inputContentInfoCompat.getDescription();
            bundle2 = EditorInfoCompat.getContentMimeTypes(object);
            n2 = ((String[])bundle2).length;
            n4 = 0;
            n3 = 0;
            while (n3 < n2) {
                if (!clipDescription.hasMimeType(bundle2[n3])) {
                    ++n3;
                    continue;
                }
                break block9;
            }
            return false;
        }
        n3 = 1;
        if (n3 == 0) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 25) {
            return inputConnection.commitContent((InputContentInfo)inputContentInfoCompat.unwrap(), n, bundle);
        }
        n2 = EditorInfoCompat.getProtocol(object);
        if (n2 != 2) {
            n3 = n4;
            if (n2 != 3) {
                n3 = n4;
                if (n2 != 4) {
                    return false;
                }
            }
        } else {
            n3 = 1;
        }
        bundle2 = new Bundle();
        object = n3 != 0 ? COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY : COMMIT_CONTENT_CONTENT_URI_KEY;
        bundle2.putParcelable((String)object, (Parcelable)inputContentInfoCompat.getContentUri());
        object = n3 != 0 ? COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY : COMMIT_CONTENT_DESCRIPTION_KEY;
        bundle2.putParcelable((String)object, (Parcelable)inputContentInfoCompat.getDescription());
        object = n3 != 0 ? COMMIT_CONTENT_LINK_URI_INTEROP_KEY : COMMIT_CONTENT_LINK_URI_KEY;
        bundle2.putParcelable((String)object, (Parcelable)inputContentInfoCompat.getLinkUri());
        object = n3 != 0 ? COMMIT_CONTENT_FLAGS_INTEROP_KEY : COMMIT_CONTENT_FLAGS_KEY;
        bundle2.putInt((String)object, n);
        object = n3 != 0 ? COMMIT_CONTENT_OPTS_INTEROP_KEY : COMMIT_CONTENT_OPTS_KEY;
        bundle2.putParcelable((String)object, (Parcelable)bundle);
        if (n3 != 0) {
            object = COMMIT_CONTENT_INTEROP_ACTION;
            return inputConnection.performPrivateCommand((String)object, bundle2);
        }
        object = COMMIT_CONTENT_ACTION;
        return inputConnection.performPrivateCommand((String)object, bundle2);
    }

    public static InputConnection createWrapper(InputConnection inputConnection, EditorInfo editorInfo, final OnCommitContentListener onCommitContentListener) {
        if (inputConnection == null) throw new IllegalArgumentException("inputConnection must be non-null");
        if (editorInfo == null) throw new IllegalArgumentException("editorInfo must be non-null");
        if (onCommitContentListener == null) throw new IllegalArgumentException("onCommitContentListener must be non-null");
        if (Build.VERSION.SDK_INT >= 25) {
            return new InputConnectionWrapper(inputConnection, false){

                public boolean commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle) {
                    if (!onCommitContentListener.onCommitContent(InputContentInfoCompat.wrap((Object)inputContentInfo), n, bundle)) return super.commitContent(inputContentInfo, n, bundle);
                    return true;
                }
            };
        }
        if (EditorInfoCompat.getContentMimeTypes(editorInfo).length != 0) return new InputConnectionWrapper(inputConnection, false){

            public boolean performPrivateCommand(String string2, Bundle bundle) {
                if (!InputConnectionCompat.handlePerformPrivateCommand(string2, bundle, onCommitContentListener)) return super.performPrivateCommand(string2, bundle);
                return true;
            }
        };
        return inputConnection;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    static boolean handlePerformPrivateCommand(String string2, Bundle object, OnCommitContentListener onCommitContentListener) {
        void var0_3;
        block9 : {
            int n;
            int n2 = 0;
            if (object == null) {
                return false;
            }
            if (TextUtils.equals((CharSequence)COMMIT_CONTENT_ACTION, (CharSequence)string2)) {
                n = 0;
            } else {
                if (!TextUtils.equals((CharSequence)COMMIT_CONTENT_INTEROP_ACTION, (CharSequence)string2)) return false;
                n = 1;
            }
            string2 = n != 0 ? COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY : COMMIT_CONTENT_RESULT_RECEIVER_KEY;
            ResultReceiver resultReceiver = (ResultReceiver)object.getParcelable(string2);
            string2 = n != 0 ? COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY : COMMIT_CONTENT_CONTENT_URI_KEY;
            try {
                Uri uri = (Uri)object.getParcelable(string2);
                string2 = n != 0 ? COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY : COMMIT_CONTENT_DESCRIPTION_KEY;
                ClipDescription clipDescription = (ClipDescription)object.getParcelable(string2);
                string2 = n != 0 ? COMMIT_CONTENT_LINK_URI_INTEROP_KEY : COMMIT_CONTENT_LINK_URI_KEY;
                Uri uri2 = (Uri)object.getParcelable(string2);
                string2 = n != 0 ? COMMIT_CONTENT_FLAGS_INTEROP_KEY : COMMIT_CONTENT_FLAGS_KEY;
                int n3 = object.getInt(string2);
                string2 = n != 0 ? COMMIT_CONTENT_OPTS_INTEROP_KEY : COMMIT_CONTENT_OPTS_KEY;
                string2 = (Bundle)object.getParcelable(string2);
                n = n2;
                if (uri != null) {
                    n = n2;
                    if (clipDescription != null) {
                        object = new InputContentInfoCompat(uri, clipDescription, uri2);
                        n = onCommitContentListener.onCommitContent((InputContentInfoCompat)object, n3, (Bundle)string2) ? 1 : 0;
                    }
                }
                if (resultReceiver == null) return n != 0;
            }
            catch (Throwable throwable) {
                object = resultReceiver;
            }
            resultReceiver.send(n, null);
            return n != 0;
            break block9;
            catch (Throwable throwable) {
                object = null;
            }
        }
        if (object == null) throw var0_3;
        object.send(0, null);
        throw var0_3;
    }

    public static interface OnCommitContentListener {
        public boolean onCommitContent(InputContentInfoCompat var1, int var2, Bundle var3);
    }

}

