package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

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

   public static boolean commitContent(InputConnection var0, EditorInfo var1, InputContentInfoCompat var2, int var3, Bundle var4) {
      ClipDescription var5 = var2.getDescription();
      String[] var6 = EditorInfoCompat.getContentMimeTypes(var1);
      int var7 = var6.length;
      boolean var8 = false;
      int var9 = 0;

      boolean var12;
      while(true) {
         if (var9 >= var7) {
            var12 = false;
            break;
         }

         if (var5.hasMimeType(var6[var9])) {
            var12 = true;
            break;
         }

         ++var9;
      }

      if (!var12) {
         return false;
      } else if (VERSION.SDK_INT >= 25) {
         return var0.commitContent((InputContentInfo)var2.unwrap(), var3, var4);
      } else {
         var7 = EditorInfoCompat.getProtocol(var1);
         if (var7 != 2) {
            var12 = var8;
            if (var7 != 3) {
               var12 = var8;
               if (var7 != 4) {
                  return false;
               }
            }
         } else {
            var12 = true;
         }

         Bundle var11 = new Bundle();
         String var10;
         if (var12) {
            var10 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
         } else {
            var10 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
         }

         var11.putParcelable(var10, var2.getContentUri());
         if (var12) {
            var10 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
         } else {
            var10 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
         }

         var11.putParcelable(var10, var2.getDescription());
         if (var12) {
            var10 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
         } else {
            var10 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
         }

         var11.putParcelable(var10, var2.getLinkUri());
         if (var12) {
            var10 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
         } else {
            var10 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
         }

         var11.putInt(var10, var3);
         if (var12) {
            var10 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
         } else {
            var10 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
         }

         var11.putParcelable(var10, var4);
         if (var12) {
            var10 = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
         } else {
            var10 = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
         }

         return var0.performPrivateCommand(var10, var11);
      }
   }

   public static InputConnection createWrapper(InputConnection var0, EditorInfo var1, final InputConnectionCompat.OnCommitContentListener var2) {
      if (var0 != null) {
         if (var1 != null) {
            if (var2 != null) {
               if (VERSION.SDK_INT >= 25) {
                  return new InputConnectionWrapper(var0, false) {
                     public boolean commitContent(InputContentInfo var1, int var2x, Bundle var3) {
                        return var2.onCommitContent(InputContentInfoCompat.wrap(var1), var2x, var3) ? true : super.commitContent(var1, var2x, var3);
                     }
                  };
               } else {
                  return (InputConnection)(EditorInfoCompat.getContentMimeTypes(var1).length == 0 ? var0 : new InputConnectionWrapper(var0, false) {
                     public boolean performPrivateCommand(String var1, Bundle var2x) {
                        return InputConnectionCompat.handlePerformPrivateCommand(var1, var2x, var2) ? true : super.performPrivateCommand(var1, var2x);
                     }
                  });
               }
            } else {
               throw new IllegalArgumentException("onCommitContentListener must be non-null");
            }
         } else {
            throw new IllegalArgumentException("editorInfo must be non-null");
         }
      } else {
         throw new IllegalArgumentException("inputConnection must be non-null");
      }
   }

   static boolean handlePerformPrivateCommand(String var0, Bundle var1, InputConnectionCompat.OnCommitContentListener var2) {
      byte var3 = 0;
      if (var1 == null) {
         return false;
      } else {
         boolean var4;
         if (TextUtils.equals("androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", var0)) {
            var4 = false;
         } else {
            if (!TextUtils.equals("android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", var0)) {
               return false;
            }

            var4 = true;
         }

         if (var4) {
            var0 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
         } else {
            var0 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
         }

         ResultReceiver var5;
         try {
            var5 = (ResultReceiver)var1.getParcelable(var0);
         } finally {
            ;
         }

         if (var4) {
            var0 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
         } else {
            var0 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
         }

         byte var67;
         label832: {
            Throwable var10000;
            label833: {
               Uri var6;
               boolean var10001;
               try {
                  var6 = (Uri)var1.getParcelable(var0);
               } catch (Throwable var65) {
                  var10000 = var65;
                  var10001 = false;
                  break label833;
               }

               if (var4) {
                  var0 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
               } else {
                  var0 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
               }

               ClipDescription var7;
               try {
                  var7 = (ClipDescription)var1.getParcelable(var0);
               } catch (Throwable var64) {
                  var10000 = var64;
                  var10001 = false;
                  break label833;
               }

               if (var4) {
                  var0 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
               } else {
                  var0 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
               }

               Uri var8;
               try {
                  var8 = (Uri)var1.getParcelable(var0);
               } catch (Throwable var63) {
                  var10000 = var63;
                  var10001 = false;
                  break label833;
               }

               if (var4) {
                  var0 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
               } else {
                  var0 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
               }

               int var9;
               try {
                  var9 = var1.getInt(var0);
               } catch (Throwable var62) {
                  var10000 = var62;
                  var10001 = false;
                  break label833;
               }

               if (var4) {
                  var0 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
               } else {
                  var0 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
               }

               Bundle var68;
               try {
                  var68 = (Bundle)var1.getParcelable(var0);
               } catch (Throwable var61) {
                  var10000 = var61;
                  var10001 = false;
                  break label833;
               }

               var67 = var3;
               if (var6 == null) {
                  break label832;
               }

               var67 = var3;
               if (var7 == null) {
                  break label832;
               }

               label805:
               try {
                  InputContentInfoCompat var66 = new InputContentInfoCompat(var6, var7, var8);
                  var67 = var2.onCommitContent(var66, var9, var68);
                  break label832;
               } catch (Throwable var60) {
                  var10000 = var60;
                  var10001 = false;
                  break label805;
               }
            }

            Throwable var69 = var10000;
            if (var5 != null) {
               var5.send(0, (Bundle)null);
            }

            throw var69;
         }

         if (var5 != null) {
            var5.send(var67, (Bundle)null);
         }

         return (boolean)var67;
      }
   }

   public interface OnCommitContentListener {
      boolean onCommitContent(InputContentInfoCompat var1, int var2, Bundle var3);
   }
}
