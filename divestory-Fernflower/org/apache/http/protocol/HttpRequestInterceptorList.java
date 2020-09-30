package org.apache.http.protocol;

import java.util.List;
import org.apache.http.HttpRequestInterceptor;

public interface HttpRequestInterceptorList {
   void addRequestInterceptor(HttpRequestInterceptor var1);

   void addRequestInterceptor(HttpRequestInterceptor var1, int var2);

   void clearRequestInterceptors();

   HttpRequestInterceptor getRequestInterceptor(int var1);

   int getRequestInterceptorCount();

   void removeRequestInterceptorByClass(Class var1);

   void setInterceptors(List var1);
}
