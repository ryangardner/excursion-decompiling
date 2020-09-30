package com.google.api.client.googleapis.notifications;

public class TypedNotification<T> extends AbstractNotification {
   private T content;

   public TypedNotification(long var1, String var3, String var4, String var5, String var6) {
      super(var1, var3, var4, var5, var6);
   }

   public TypedNotification(UnparsedNotification var1) {
      super(var1);
   }

   public final T getContent() {
      return this.content;
   }

   public TypedNotification<T> setChanged(String var1) {
      return (TypedNotification)super.setChanged(var1);
   }

   public TypedNotification<T> setChannelExpiration(String var1) {
      return (TypedNotification)super.setChannelExpiration(var1);
   }

   public TypedNotification<T> setChannelId(String var1) {
      return (TypedNotification)super.setChannelId(var1);
   }

   public TypedNotification<T> setChannelToken(String var1) {
      return (TypedNotification)super.setChannelToken(var1);
   }

   public TypedNotification<T> setContent(T var1) {
      this.content = var1;
      return this;
   }

   public TypedNotification<T> setMessageNumber(long var1) {
      return (TypedNotification)super.setMessageNumber(var1);
   }

   public TypedNotification<T> setResourceId(String var1) {
      return (TypedNotification)super.setResourceId(var1);
   }

   public TypedNotification<T> setResourceState(String var1) {
      return (TypedNotification)super.setResourceState(var1);
   }

   public TypedNotification<T> setResourceUri(String var1) {
      return (TypedNotification)super.setResourceUri(var1);
   }

   public String toString() {
      return super.toStringHelper().add("content", this.content).toString();
   }
}
