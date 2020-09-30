package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class StoredChannel implements Serializable {
   public static final String DEFAULT_DATA_STORE_ID = StoredChannel.class.getSimpleName();
   private static final long serialVersionUID = 1L;
   private String clientToken;
   private Long expiration;
   private final String id;
   private final Lock lock;
   private final UnparsedNotificationCallback notificationCallback;
   private String topicId;

   public StoredChannel(UnparsedNotificationCallback var1) {
      this(var1, NotificationUtils.randomUuidString());
   }

   public StoredChannel(UnparsedNotificationCallback var1, String var2) {
      this.lock = new ReentrantLock();
      this.notificationCallback = (UnparsedNotificationCallback)Preconditions.checkNotNull(var1);
      this.id = (String)Preconditions.checkNotNull(var2);
   }

   public static DataStore<StoredChannel> getDefaultDataStore(DataStoreFactory var0) throws IOException {
      return var0.getDataStore(DEFAULT_DATA_STORE_ID);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof StoredChannel)) {
         return false;
      } else {
         StoredChannel var2 = (StoredChannel)var1;
         return this.getId().equals(var2.getId());
      }
   }

   public String getClientToken() {
      this.lock.lock();

      String var1;
      try {
         var1 = this.clientToken;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public Long getExpiration() {
      this.lock.lock();

      Long var1;
      try {
         var1 = this.expiration;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public String getId() {
      this.lock.lock();

      String var1;
      try {
         var1 = this.id;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public UnparsedNotificationCallback getNotificationCallback() {
      this.lock.lock();

      UnparsedNotificationCallback var1;
      try {
         var1 = this.notificationCallback;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public String getTopicId() {
      this.lock.lock();

      String var1;
      try {
         var1 = this.topicId;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public int hashCode() {
      return this.getId().hashCode();
   }

   public StoredChannel setClientToken(String var1) {
      this.lock.lock();

      try {
         this.clientToken = var1;
      } finally {
         this.lock.unlock();
      }

      return this;
   }

   public StoredChannel setExpiration(Long var1) {
      this.lock.lock();

      try {
         this.expiration = var1;
      } finally {
         this.lock.unlock();
      }

      return this;
   }

   public StoredChannel setTopicId(String var1) {
      this.lock.lock();

      try {
         this.topicId = var1;
      } finally {
         this.lock.unlock();
      }

      return this;
   }

   public StoredChannel store(DataStore<StoredChannel> var1) throws IOException {
      this.lock.lock();

      try {
         var1.set(this.getId(), this);
      } finally {
         this.lock.unlock();
      }

      return this;
   }

   public StoredChannel store(DataStoreFactory var1) throws IOException {
      return this.store(getDefaultDataStore(var1));
   }

   public String toString() {
      return Objects.toStringHelper(StoredChannel.class).add("notificationCallback", this.getNotificationCallback()).add("clientToken", this.getClientToken()).add("expiration", this.getExpiration()).add("id", this.getId()).add("topicId", this.getTopicId()).toString();
   }
}
