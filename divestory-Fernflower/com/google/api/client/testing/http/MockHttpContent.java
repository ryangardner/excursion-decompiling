package com.google.api.client.testing.http;

import com.google.api.client.http.HttpContent;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.OutputStream;

public class MockHttpContent implements HttpContent {
   private byte[] content = new byte[0];
   private long length = -1L;
   private String type;

   public final byte[] getContent() {
      return this.content;
   }

   public long getLength() throws IOException {
      return this.length;
   }

   public String getType() {
      return this.type;
   }

   public boolean retrySupported() {
      return true;
   }

   public MockHttpContent setContent(byte[] var1) {
      this.content = (byte[])Preconditions.checkNotNull(var1);
      return this;
   }

   public MockHttpContent setLength(long var1) {
      boolean var3;
      if (var1 >= -1L) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      this.length = var1;
      return this;
   }

   public MockHttpContent setType(String var1) {
      this.type = var1;
      return this;
   }

   public void writeTo(OutputStream var1) throws IOException {
      var1.write(this.content);
      var1.flush();
   }
}
