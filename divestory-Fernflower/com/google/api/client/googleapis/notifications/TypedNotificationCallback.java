package com.google.api.client.googleapis.notifications;

import com.google.api.client.http.HttpMediaType;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class TypedNotificationCallback<T> implements UnparsedNotificationCallback {
   private static final long serialVersionUID = 1L;

   protected abstract Class<T> getDataClass() throws IOException;

   protected abstract ObjectParser getObjectParser() throws IOException;

   protected abstract void onNotification(StoredChannel var1, TypedNotification<T> var2) throws IOException;

   public final void onNotification(StoredChannel var1, UnparsedNotification var2) throws IOException {
      TypedNotification var3 = new TypedNotification(var2);
      String var4 = var2.getContentType();
      if (var4 != null) {
         Charset var6 = (new HttpMediaType(var4)).getCharsetParameter();
         Class var5 = (Class)Preconditions.checkNotNull(this.getDataClass());
         var3.setContent(this.getObjectParser().parseAndClose(var2.getContentStream(), var6, var5));
      }

      this.onNotification(var1, var3);
   }
}
