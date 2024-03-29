package com.google.api.client.googleapis.notifications.json;

import com.google.api.client.googleapis.notifications.TypedNotificationCallback;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import java.io.IOException;

public abstract class JsonNotificationCallback<T> extends TypedNotificationCallback<T> {
   private static final long serialVersionUID = 1L;

   protected abstract JsonFactory getJsonFactory() throws IOException;

   protected final JsonObjectParser getObjectParser() throws IOException {
      return new JsonObjectParser(this.getJsonFactory());
   }
}
