package com.syntak.library;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class Installation {
   private static final String INSTALLATION = "INSTALLATION";
   private static String sID;

   public static String id(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private static String readInstallationFile(File var0) throws IOException {
      RandomAccessFile var1 = new RandomAccessFile(var0, "r");
      byte[] var2 = new byte[(int)var1.length()];
      var1.readFully(var2);
      var1.close();
      return new String(var2);
   }

   private static void writeInstallationFile(File var0) throws IOException {
      FileOutputStream var1 = new FileOutputStream(var0);
      var1.write(UUID.randomUUID().toString().getBytes());
      var1.close();
   }
}
