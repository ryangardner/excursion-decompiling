/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Data;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BearerToken {
    static final Pattern INVALID_TOKEN_ERROR = Pattern.compile("\\s*error\\s*=\\s*\"?invalid_token\"?");
    static final String PARAM_NAME = "access_token";

    public static Credential.AccessMethod authorizationHeaderAccessMethod() {
        return new AuthorizationHeaderAccessMethod();
    }

    public static Credential.AccessMethod formEncodedBodyAccessMethod() {
        return new FormEncodedBodyAccessMethod();
    }

    public static Credential.AccessMethod queryParameterAccessMethod() {
        return new QueryParameterAccessMethod();
    }

    static final class AuthorizationHeaderAccessMethod
    implements Credential.AccessMethod {
        static final String HEADER_PREFIX = "Bearer ";

        AuthorizationHeaderAccessMethod() {
        }

        @Override
        public String getAccessTokenFromRequest(HttpRequest object) {
            if ((object = ((HttpRequest)object).getHeaders().getAuthorizationAsList()) == null) return null;
            Iterator iterator2 = object.iterator();
            do {
                if (!iterator2.hasNext()) return null;
            } while (!((String)(object = (String)iterator2.next())).startsWith(HEADER_PREFIX));
            return ((String)object).substring(7);
        }

        @Override
        public void intercept(HttpRequest object, String string2) throws IOException {
            object = ((HttpRequest)object).getHeaders();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(HEADER_PREFIX);
            stringBuilder.append(string2);
            ((HttpHeaders)object).setAuthorization(stringBuilder.toString());
        }
    }

    static final class FormEncodedBodyAccessMethod
    implements Credential.AccessMethod {
        FormEncodedBodyAccessMethod() {
        }

        private static Map<String, Object> getData(HttpRequest httpRequest) {
            return Data.mapOf(UrlEncodedContent.getContent(httpRequest).getData());
        }

        @Override
        public String getAccessTokenFromRequest(HttpRequest object) {
            if ((object = FormEncodedBodyAccessMethod.getData((HttpRequest)object).get(BearerToken.PARAM_NAME)) != null) return object.toString();
            return null;
        }

        @Override
        public void intercept(HttpRequest httpRequest, String string2) throws IOException {
            Preconditions.checkArgument("GET".equals(httpRequest.getRequestMethod()) ^ true, "HTTP GET method is not supported");
            FormEncodedBodyAccessMethod.getData(httpRequest).put(BearerToken.PARAM_NAME, string2);
        }
    }

    static final class QueryParameterAccessMethod
    implements Credential.AccessMethod {
        QueryParameterAccessMethod() {
        }

        @Override
        public String getAccessTokenFromRequest(HttpRequest object) {
            if ((object = ((HttpRequest)object).getUrl().get(BearerToken.PARAM_NAME)) != null) return object.toString();
            return null;
        }

        @Override
        public void intercept(HttpRequest httpRequest, String string2) throws IOException {
            httpRequest.getUrl().set(BearerToken.PARAM_NAME, string2);
        }
    }

}

