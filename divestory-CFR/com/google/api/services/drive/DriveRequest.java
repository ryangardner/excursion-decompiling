/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.Drive;

public abstract class DriveRequest<T>
extends AbstractGoogleJsonClientRequest<T> {
    @Key
    private String alt;
    @Key
    private String fields;
    @Key
    private String key;
    @Key(value="oauth_token")
    private String oauthToken;
    @Key
    private Boolean prettyPrint;
    @Key
    private String quotaUser;
    @Key
    private String userIp;

    public DriveRequest(Drive drive, String string2, String string3, Object object, Class<T> class_) {
        super(drive, string2, string3, object, class_);
    }

    @Override
    public final Drive getAbstractGoogleClient() {
        return (Drive)super.getAbstractGoogleClient();
    }

    public String getAlt() {
        return this.alt;
    }

    public String getFields() {
        return this.fields;
    }

    public String getKey() {
        return this.key;
    }

    public String getOauthToken() {
        return this.oauthToken;
    }

    public Boolean getPrettyPrint() {
        return this.prettyPrint;
    }

    public String getQuotaUser() {
        return this.quotaUser;
    }

    public String getUserIp() {
        return this.userIp;
    }

    @Override
    public DriveRequest<T> set(String string2, Object object) {
        return (DriveRequest)super.set(string2, object);
    }

    public DriveRequest<T> setAlt(String string2) {
        this.alt = string2;
        return this;
    }

    @Override
    public DriveRequest<T> setDisableGZipContent(boolean bl) {
        return (DriveRequest)super.setDisableGZipContent(bl);
    }

    public DriveRequest<T> setFields(String string2) {
        this.fields = string2;
        return this;
    }

    public DriveRequest<T> setKey(String string2) {
        this.key = string2;
        return this;
    }

    public DriveRequest<T> setOauthToken(String string2) {
        this.oauthToken = string2;
        return this;
    }

    public DriveRequest<T> setPrettyPrint(Boolean bl) {
        this.prettyPrint = bl;
        return this;
    }

    public DriveRequest<T> setQuotaUser(String string2) {
        this.quotaUser = string2;
        return this;
    }

    @Override
    public DriveRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        return (DriveRequest)super.setRequestHeaders(httpHeaders);
    }

    public DriveRequest<T> setUserIp(String string2) {
        this.userIp = string2;
        return this;
    }
}

