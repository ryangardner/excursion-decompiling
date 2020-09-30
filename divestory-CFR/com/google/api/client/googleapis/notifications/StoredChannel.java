/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.notifications;

import com.google.api.client.googleapis.notifications.NotificationUtils;
import com.google.api.client.googleapis.notifications.UnparsedNotificationCallback;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class StoredChannel
implements Serializable {
    public static final String DEFAULT_DATA_STORE_ID = StoredChannel.class.getSimpleName();
    private static final long serialVersionUID = 1L;
    private String clientToken;
    private Long expiration;
    private final String id;
    private final Lock lock = new ReentrantLock();
    private final UnparsedNotificationCallback notificationCallback;
    private String topicId;

    public StoredChannel(UnparsedNotificationCallback unparsedNotificationCallback) {
        this(unparsedNotificationCallback, NotificationUtils.randomUuidString());
    }

    public StoredChannel(UnparsedNotificationCallback unparsedNotificationCallback, String string2) {
        this.notificationCallback = Preconditions.checkNotNull(unparsedNotificationCallback);
        this.id = Preconditions.checkNotNull(string2);
    }

    public static DataStore<StoredChannel> getDefaultDataStore(DataStoreFactory dataStoreFactory) throws IOException {
        return dataStoreFactory.getDataStore(DEFAULT_DATA_STORE_ID);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StoredChannel)) {
            return false;
        }
        object = (StoredChannel)object;
        return this.getId().equals(((StoredChannel)object).getId());
    }

    public String getClientToken() {
        this.lock.lock();
        try {
            String string2 = this.clientToken;
            return string2;
        }
        finally {
            this.lock.unlock();
        }
    }

    public Long getExpiration() {
        this.lock.lock();
        try {
            Long l = this.expiration;
            return l;
        }
        finally {
            this.lock.unlock();
        }
    }

    public String getId() {
        this.lock.lock();
        try {
            String string2 = this.id;
            return string2;
        }
        finally {
            this.lock.unlock();
        }
    }

    public UnparsedNotificationCallback getNotificationCallback() {
        this.lock.lock();
        try {
            UnparsedNotificationCallback unparsedNotificationCallback = this.notificationCallback;
            return unparsedNotificationCallback;
        }
        finally {
            this.lock.unlock();
        }
    }

    public String getTopicId() {
        this.lock.lock();
        try {
            String string2 = this.topicId;
            return string2;
        }
        finally {
            this.lock.unlock();
        }
    }

    public int hashCode() {
        return this.getId().hashCode();
    }

    public StoredChannel setClientToken(String string2) {
        this.lock.lock();
        try {
            this.clientToken = string2;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public StoredChannel setExpiration(Long l) {
        this.lock.lock();
        try {
            this.expiration = l;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public StoredChannel setTopicId(String string2) {
        this.lock.lock();
        try {
            this.topicId = string2;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public StoredChannel store(DataStore<StoredChannel> dataStore) throws IOException {
        this.lock.lock();
        try {
            dataStore.set(this.getId(), this);
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public StoredChannel store(DataStoreFactory dataStoreFactory) throws IOException {
        return this.store(StoredChannel.getDefaultDataStore(dataStoreFactory));
    }

    public String toString() {
        return Objects.toStringHelper(StoredChannel.class).add("notificationCallback", this.getNotificationCallback()).add("clientToken", this.getClientToken()).add("expiration", this.getExpiration()).add("id", this.getId()).add("topicId", this.getTopicId()).toString();
    }
}

