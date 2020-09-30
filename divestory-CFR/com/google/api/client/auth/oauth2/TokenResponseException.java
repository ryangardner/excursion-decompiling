/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Strings;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class TokenResponseException
extends HttpResponseException {
    private static final long serialVersionUID = 4020689092957439244L;
    private final transient TokenErrorResponse details;

    TokenResponseException(HttpResponseException.Builder builder, TokenErrorResponse tokenErrorResponse) {
        super(builder);
        this.details = tokenErrorResponse;
    }

    public static TokenResponseException from(JsonFactory object, HttpResponse object2) {
        HttpResponseException.Builder builder;
        Object object3;
        block9 : {
            Object object4;
            block8 : {
                block7 : {
                    Object var5_7;
                    block6 : {
                        builder = new HttpResponseException.Builder(((HttpResponse)object2).getStatusCode(), ((HttpResponse)object2).getStatusMessage(), ((HttpResponse)object2).getHeaders());
                        Preconditions.checkNotNull(object);
                        object3 = ((HttpResponse)object2).getContentType();
                        object4 = null;
                        var5_7 = null;
                        if (((HttpResponse)object2).isSuccessStatusCode() || object3 == null || ((HttpResponse)object2).getContent() == null || !HttpMediaType.equalsIgnoreParameters("application/json; charset=UTF-8", (String)object3)) break block6;
                        object3 = new JsonObjectParser((JsonFactory)object);
                        object = ((JsonObjectParser)object3).parseAndClose(((HttpResponse)object2).getContent(), ((HttpResponse)object2).getContentCharset(), TokenErrorResponse.class);
                        try {
                            object3 = ((GenericJson)object).toPrettyString();
                            break block7;
                        }
                        catch (IOException iOException) {
                            break block8;
                        }
                    }
                    try {
                        object3 = ((HttpResponse)object2).parseAsString();
                        object = var5_7;
                    }
                    catch (IOException iOException) {
                        object = null;
                    }
                }
                object4 = object3;
                object3 = object;
                object = object4;
                break block9;
            }
            ((Throwable)object3).printStackTrace();
            object3 = object;
            object = object4;
        }
        object2 = HttpResponseException.computeMessageBuffer((HttpResponse)object2);
        if (!Strings.isNullOrEmpty((String)object)) {
            ((StringBuilder)object2).append(StringUtils.LINE_SEPARATOR);
            ((StringBuilder)object2).append((String)object);
            builder.setContent((String)object);
        }
        builder.setMessage(((StringBuilder)object2).toString());
        return new TokenResponseException(builder, (TokenErrorResponse)object3);
    }

    public final TokenErrorResponse getDetails() {
        return this.details;
    }
}

