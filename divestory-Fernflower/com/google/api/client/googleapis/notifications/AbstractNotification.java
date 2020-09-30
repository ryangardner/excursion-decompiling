package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;

public abstract class AbstractNotification {
   private String changed;
   private String channelExpiration;
   private String channelId;
   private String channelToken;
   private long messageNumber;
   private String resourceId;
   private String resourceState;
   private String resourceUri;

   protected AbstractNotification(long var1, String var3, String var4, String var5, String var6) {
      this.setMessageNumber(var1);
      this.setResourceState(var3);
      this.setResourceId(var4);
      this.setResourceUri(var5);
      this.setChannelId(var6);
   }

   protected AbstractNotification(AbstractNotification var1) {
      this(var1.getMessageNumber(), var1.getResourceState(), var1.getResourceId(), var1.getResourceUri(), var1.getChannelId());
      this.setChannelExpiration(var1.getChannelExpiration());
      this.setChannelToken(var1.getChannelToken());
      this.setChanged(var1.getChanged());
   }

   public final String getChanged() {
      return this.changed;
   }

   public final String getChannelExpiration() {
      return this.channelExpiration;
   }

   public final String getChannelId() {
      return this.channelId;
   }

   public final String getChannelToken() {
      return this.channelToken;
   }

   public final long getMessageNumber() {
      return this.messageNumber;
   }

   public final String getResourceId() {
      return this.resourceId;
   }

   public final String getResourceState() {
      return this.resourceState;
   }

   public final String getResourceUri() {
      return this.resourceUri;
   }

   public AbstractNotification setChanged(String var1) {
      this.changed = var1;
      return this;
   }

   public AbstractNotification setChannelExpiration(String var1) {
      this.channelExpiration = var1;
      return this;
   }

   public AbstractNotification setChannelId(String var1) {
      this.channelId = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public AbstractNotification setChannelToken(String var1) {
      this.channelToken = var1;
      return this;
   }

   public AbstractNotification setMessageNumber(long var1) {
      boolean var3;
      if (var1 >= 1L) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      this.messageNumber = var1;
      return this;
   }

   public AbstractNotification setResourceId(String var1) {
      this.resourceId = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public AbstractNotification setResourceState(String var1) {
      this.resourceState = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public AbstractNotification setResourceUri(String var1) {
      this.resourceUri = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public String toString() {
      return this.toStringHelper().toString();
   }

   protected Objects.ToStringHelper toStringHelper() {
      return Objects.toStringHelper(this).add("messageNumber", this.messageNumber).add("resourceState", this.resourceState).add("resourceId", this.resourceId).add("resourceUri", this.resourceUri).add("channelId", this.channelId).add("channelExpiration", this.channelExpiration).add("channelToken", this.channelToken).add("changed", this.changed);
   }
}
