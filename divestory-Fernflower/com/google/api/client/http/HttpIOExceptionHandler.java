package com.google.api.client.http;

import java.io.IOException;

public interface HttpIOExceptionHandler {
   boolean handleIOException(HttpRequest var1, boolean var2) throws IOException;
}
