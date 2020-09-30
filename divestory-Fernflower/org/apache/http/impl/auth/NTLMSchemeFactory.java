package org.apache.http.impl.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.params.HttpParams;

public class NTLMSchemeFactory implements AuthSchemeFactory {
   public AuthScheme newInstance(HttpParams var1) {
      return new NTLMScheme(new NTLMEngineImpl());
   }
}
