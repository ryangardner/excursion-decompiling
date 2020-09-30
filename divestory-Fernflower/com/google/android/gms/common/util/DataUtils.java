package com.google.android.gms.common.util;

import android.database.CharArrayBuffer;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;

public final class DataUtils {
   public static void copyStringToBuffer(String var0, CharArrayBuffer var1) {
      if (TextUtils.isEmpty(var0)) {
         var1.sizeCopied = 0;
      } else if (var1.data != null && var1.data.length >= var0.length()) {
         var0.getChars(0, var0.length(), var1.data, 0);
      } else {
         var1.data = var0.toCharArray();
      }

      var1.sizeCopied = var0.length();
   }

   public static byte[] loadImageBytes(Bitmap var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      var0.compress(CompressFormat.JPEG, 100, var1);
      return var1.toByteArray();
   }
}
