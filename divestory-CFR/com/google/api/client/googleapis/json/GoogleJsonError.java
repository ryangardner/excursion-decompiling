/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.json;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GoogleJsonError
extends GenericJson {
    @Key
    private int code;
    @Key
    private List<ErrorInfo> errors;
    @Key
    private String message;

    static {
        Data.nullOf(ErrorInfo.class);
    }

    public static GoogleJsonError parse(JsonFactory jsonFactory, HttpResponse httpResponse) throws IOException {
        return new JsonObjectParser.Builder(jsonFactory).setWrapperKeys(Collections.singleton("error")).build().parseAndClose(httpResponse.getContent(), httpResponse.getContentCharset(), GoogleJsonError.class);
    }

    @Override
    public GoogleJsonError clone() {
        return (GoogleJsonError)super.clone();
    }

    public final int getCode() {
        return this.code;
    }

    public final List<ErrorInfo> getErrors() {
        return this.errors;
    }

    public final String getMessage() {
        return this.message;
    }

    @Override
    public GoogleJsonError set(String string2, Object object) {
        return (GoogleJsonError)super.set(string2, object);
    }

    public final void setCode(int n) {
        this.code = n;
    }

    public final void setErrors(List<ErrorInfo> list) {
        this.errors = list;
    }

    public final void setMessage(String string2) {
        this.message = string2;
    }

    public static class ErrorInfo
    extends GenericJson {
        @Key
        private String domain;
        @Key
        private String location;
        @Key
        private String locationType;
        @Key
        private String message;
        @Key
        private String reason;

        @Override
        public ErrorInfo clone() {
            return (ErrorInfo)super.clone();
        }

        public final String getDomain() {
            return this.domain;
        }

        public final String getLocation() {
            return this.location;
        }

        public final String getLocationType() {
            return this.locationType;
        }

        public final String getMessage() {
            return this.message;
        }

        public final String getReason() {
            return this.reason;
        }

        @Override
        public ErrorInfo set(String string2, Object object) {
            return (ErrorInfo)super.set(string2, object);
        }

        public final void setDomain(String string2) {
            this.domain = string2;
        }

        public final void setLocation(String string2) {
            this.location = string2;
        }

        public final void setLocationType(String string2) {
            this.locationType = string2;
        }

        public final void setMessage(String string2) {
            this.message = string2;
        }

        public final void setReason(String string2) {
            this.reason = string2;
        }
    }

}

