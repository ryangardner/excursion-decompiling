package org.apache.http.protocol;

import java.util.List;
import org.apache.http.HttpResponseInterceptor;

public interface HttpResponseInterceptorList {
   void addResponseInterceptor(HttpResponseInterceptor var1);

   void addResponseInterceptor(HttpResponseInterceptor var1, int var2);

   void clearResponseInterceptors();

   HttpResponseInterceptor getResponseInterceptor(int var1);

   int getResponseInterceptorCount();

   void removeResponseInterceptorByClass(Class var1);

   void setInterceptors(List var1);
}
