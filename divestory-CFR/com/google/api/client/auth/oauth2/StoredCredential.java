/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Objects;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class StoredCredential
implements Serializable {
    public static final String DEFAULT_DATA_STORE_ID = StoredCredential.class.getSimpleName();
    private static final long serialVersionUID = 1L;
    private String accessToken;
    private Long expirationTimeMilliseconds;
    private final Lock lock = new ReentrantLock();
    private String refreshToken;

    public StoredCredential() {
    }

    public StoredCredential(Credential credential) {
        this.setAccessToken(credential.getAccessToken());
        this.setRefreshToken(credential.getRefreshToken());
        this.setExpirationTimeMilliseconds(credential.getExpirationTimeMilliseconds());
    }

    public static DataStore<StoredCredential> getDefaultDataStore(DataStoreFactory dataStoreFactory) throws IOException {
        return dataStoreFactory.getDataStore(DEFAULT_DATA_STORE_ID);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof StoredCredential)) {
            return false;
        }
        object = (StoredCredential)object;
        if (!Objects.equal(this.getAccessToken(), ((StoredCredential)object).getAccessToken())) return false;
        if (!Objects.equal(this.getRefreshToken(), ((StoredCredential)object).getRefreshToken())) return false;
        if (!Objects.equal(this.getExpirationTimeMilliseconds(), ((StoredCredential)object).getExpirationTimeMilliseconds())) return false;
        return bl;
    }

    public String getAccessToken() {
        this.lock.lock();
        try {
            String string2 = this.accessToken;
            return string2;
        }
        finally {
            this.lock.unlock();
        }
    }

    public Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            Long l = this.expirationTimeMilliseconds;
            return l;
        }
        finally {
            this.lock.unlock();
        }
    }

    public String getRefreshToken() {
        this.lock.lock();
        try {
            String string2 = this.refreshToken;
            return string2;
        }
        finally {
            this.lock.unlock();
        }
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.getAccessToken(), this.getRefreshToken(), this.getExpirationTimeMilliseconds()});
    }

    public StoredCredential setAccessToken(String string2) {
        this.lock.lock();
        try {
            this.accessToken = string2;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public StoredCredential setExpirationTimeMilliseconds(Long l) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = l;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public StoredCredential setRefreshToken(String string2) {
        this.lock.lock();
        try {
            this.refreshToken = string2;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public String toString() {
        return Objects.toStringHelper(StoredCredential.class).add("accessToken", this.getAccessToken()).add("refreshToken", this.getRefreshToken()).add("expirationTimeMilliseconds", this.getExpirationTimeMilliseconds()).toString();
    }
}

