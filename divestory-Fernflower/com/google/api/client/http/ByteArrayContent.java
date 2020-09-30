package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class ByteArrayContent extends AbstractInputStreamContent {
   private final byte[] byteArray;
   private final int length;
   private final int offset;

   public ByteArrayContent(String var1, byte[] var2) {
      this(var1, var2, 0, var2.length);
   }

   public ByteArrayContent(String var1, byte[] var2, int var3, int var4) {
      super(var1);
      this.byteArray = (byte[])Preconditions.checkNotNull(var2);
      boolean var5;
      if (var3 >= 0 && var4 >= 0 && var3 + var4 <= var2.length) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "offset %s, length %s, array length %s", var3, var4, var2.length);
      this.offset = var3;
      this.length = var4;
   }

   public static ByteArrayContent fromString(String var0, String var1) {
      return new ByteArrayContent(var0, StringUtils.getBytesUtf8(var1));
   }

   public InputStream getInputStream() {
      return new ByteArrayInputStream(this.byteArray, this.offset, this.length);
   }

   public long getLength() {
      return (long)this.length;
   }

   public boolean retrySupported() {
      return true;
   }

   public ByteArrayContent setCloseInputStream(boolean var1) {
      return (ByteArrayContent)super.setCloseInputStream(var1);
   }

   public ByteArrayContent setType(String var1) {
      return (ByteArrayContent)super.setType(var1);
   }
}
