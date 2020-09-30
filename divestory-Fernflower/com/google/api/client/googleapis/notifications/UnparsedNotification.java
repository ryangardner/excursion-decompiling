package com.google.api.client.googleapis.notifications;

import java.io.InputStream;

public class UnparsedNotification extends AbstractNotification {
   private InputStream contentStream;
   private String contentType;

   public UnparsedNotification(long var1, String var3, String var4, String var5, String var6) {
      super(var1, var3, var4, var5, var6);
   }

   public final InputStream getContentStream() {
      return this.contentStream;
   }

   public final String getContentType() {
      return this.contentType;
   }

   public UnparsedNotification setChanged(String var1) {
      return (UnparsedNotification)super.setChanged(var1);
   }

   public UnparsedNotification setChannelExpiration(String var1) {
      return (UnparsedNotification)super.setChannelExpiration(var1);
   }

   public UnparsedNotification setChannelId(String var1) {
      return (UnparsedNotification)super.setChannelId(var1);
   }

   public UnparsedNotification setChannelToken(String var1) {
      return (UnparsedNotification)super.setChannelToken(var1);
   }

   public UnparsedNotification setContentStream(InputStream var1) {
      this.contentStream = var1;
      return this;
   }

   public UnparsedNotification setContentType(String var1) {
      this.contentType = var1;
      return this;
   }

   public UnparsedNotification setMessageNumber(long var1) {
      return (UnparsedNotification)super.setMessageNumber(var1);
   }

   public UnparsedNotification setResourceId(String var1) {
      return (UnparsedNotification)super.setResourceId(var1);
   }

   public UnparsedNotification setResourceState(String var1) {
      return (UnparsedNotification)super.setResourceState(var1);
   }

   public UnparsedNotification setResourceUri(String var1) {
      return (UnparsedNotification)super.setResourceUri(var1);
   }

   public String toString() {
      return super.toStringHelper().add("contentType", this.contentType).toString();
   }
}
