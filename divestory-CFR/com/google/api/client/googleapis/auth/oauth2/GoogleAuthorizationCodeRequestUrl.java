/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;
import java.util.Iterator;

public class GoogleAuthorizationCodeRequestUrl
extends AuthorizationCodeRequestUrl {
    @Key(value="access_type")
    private String accessType;
    @Key(value="approval_prompt")
    private String approvalPrompt;

    public GoogleAuthorizationCodeRequestUrl(GoogleClientSecrets googleClientSecrets, String string2, Collection<String> collection) {
        this(googleClientSecrets.getDetails().getClientId(), string2, collection);
    }

    public GoogleAuthorizationCodeRequestUrl(String string2, String string3, String string4, Collection<String> collection) {
        super(string2, string3);
        this.setRedirectUri(string4);
        this.setScopes((Collection)collection);
    }

    public GoogleAuthorizationCodeRequestUrl(String string2, String string3, Collection<String> collection) {
        this("https://accounts.google.com/o/oauth2/auth", string2, string3, collection);
    }

    @Override
    public GoogleAuthorizationCodeRequestUrl clone() {
        return (GoogleAuthorizationCodeRequestUrl)super.clone();
    }

    public final String getAccessType() {
        return this.accessType;
    }

    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }

    @Override
    public GoogleAuthorizationCodeRequestUrl set(String string2, Object object) {
        return (GoogleAuthorizationCodeRequestUrl)super.set(string2, object);
    }

    public GoogleAuthorizationCodeRequestUrl setAccessType(String string2) {
        this.accessType = string2;
        return this;
    }

    public GoogleAuthorizationCodeRequestUrl setApprovalPrompt(String string2) {
        this.approvalPrompt = string2;
        return this;
    }

    @Override
    public GoogleAuthorizationCodeRequestUrl setClientId(String string2) {
        return (GoogleAuthorizationCodeRequestUrl)super.setClientId(string2);
    }

    @Override
    public GoogleAuthorizationCodeRequestUrl setRedirectUri(String string2) {
        Preconditions.checkNotNull(string2);
        return (GoogleAuthorizationCodeRequestUrl)super.setRedirectUri(string2);
    }

    @Override
    public GoogleAuthorizationCodeRequestUrl setResponseTypes(Collection<String> collection) {
        return (GoogleAuthorizationCodeRequestUrl)super.setResponseTypes((Collection)collection);
    }

    @Override
    public GoogleAuthorizationCodeRequestUrl setScopes(Collection<String> collection) {
        Preconditions.checkArgument(collection.iterator().hasNext());
        return (GoogleAuthorizationCodeRequestUrl)super.setScopes((Collection)collection);
    }

    @Override
    public GoogleAuthorizationCodeRequestUrl setState(String string2) {
        return (GoogleAuthorizationCodeRequestUrl)super.setState(string2);
    }
}

