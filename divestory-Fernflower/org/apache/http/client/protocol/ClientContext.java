package org.apache.http.client.protocol;

public interface ClientContext {
   String AUTHSCHEME_REGISTRY = "http.authscheme-registry";
   String AUTH_CACHE = "http.auth.auth-cache";
   @Deprecated
   String AUTH_SCHEME_PREF = "http.auth.scheme-pref";
   String COOKIESPEC_REGISTRY = "http.cookiespec-registry";
   String COOKIE_ORIGIN = "http.cookie-origin";
   String COOKIE_SPEC = "http.cookie-spec";
   String COOKIE_STORE = "http.cookie-store";
   String CREDS_PROVIDER = "http.auth.credentials-provider";
   String PROXY_AUTH_STATE = "http.auth.proxy-scope";
   String SCHEME_REGISTRY = "http.scheme-registry";
   String TARGET_AUTH_STATE = "http.auth.target-scope";
   String USER_TOKEN = "http.user-token";
}
