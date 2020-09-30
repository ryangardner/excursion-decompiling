/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.BrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;
import java.util.Iterator;

public class GoogleBrowserClientRequestUrl
extends BrowserClientRequestUrl {
    @Key(value="approval_prompt")
    private String approvalPrompt;

    public GoogleBrowserClientRequestUrl(GoogleClientSecrets googleClientSecrets, String string2, Collection<String> collection) {
        this(googleClientSecrets.getDetails().getClientId(), string2, collection);
    }

    public GoogleBrowserClientRequestUrl(String string2, String string3, Collection<String> collection) {
        super("https://accounts.google.com/o/oauth2/auth", string2);
        this.setRedirectUri(string3);
        this.setScopes((Collection)collection);
    }

    @Override
    public GoogleBrowserClientRequestUrl clone() {
        return (GoogleBrowserClientRequestUrl)super.clone();
    }

    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }

    @Override
    public GoogleBrowserClientRequestUrl set(String string2, Object object) {
        return (GoogleBrowserClientRequestUrl)super.set(string2, object);
    }

    public GoogleBrowserClientRequestUrl setApprovalPrompt(String string2) {
        this.approvalPrompt = string2;
        return this;
    }

    @Override
    public GoogleBrowserClientRequestUrl setClientId(String string2) {
        return (GoogleBrowserClientRequestUrl)super.setClientId(string2);
    }

    @Override
    public GoogleBrowserClientRequestUrl setRedirectUri(String string2) {
        return (GoogleBrowserClientRequestUrl)super.setRedirectUri(string2);
    }

    @Override
    public GoogleBrowserClientRequestUrl setResponseTypes(Collection<String> collection) {
        return (GoogleBrowserClientRequestUrl)super.setResponseTypes((Collection)collection);
    }

    @Override
    public GoogleBrowserClientRequestUrl setScopes(Collection<String> collection) {
        Preconditions.checkArgument(collection.iterator().hasNext());
        return (GoogleBrowserClientRequestUrl)super.setScopes((Collection)collection);
    }

    @Override
    public GoogleBrowserClientRequestUrl setState(String string2) {
        return (GoogleBrowserClientRequestUrl)super.setState(string2);
    }
}

