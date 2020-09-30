package org.apache.commons.net.nntp;

public class SimpleNNTPHeader {
   private final String __from;
   private final StringBuilder __headerFields;
   private int __newsgroupCount;
   private final StringBuilder __newsgroups;
   private final String __subject;

   public SimpleNNTPHeader(String var1, String var2) {
      this.__from = var1;
      this.__subject = var2;
      this.__newsgroups = new StringBuilder();
      this.__headerFields = new StringBuilder();
      this.__newsgroupCount = 0;
   }

   public void addHeaderField(String var1, String var2) {
      this.__headerFields.append(var1);
      this.__headerFields.append(": ");
      this.__headerFields.append(var2);
      this.__headerFields.append('\n');
   }

   public void addNewsgroup(String var1) {
      int var2 = this.__newsgroupCount++;
      if (var2 > 0) {
         this.__newsgroups.append(',');
      }

      this.__newsgroups.append(var1);
   }

   public String getFromAddress() {
      return this.__from;
   }

   public String getNewsgroups() {
      return this.__newsgroups.toString();
   }

   public String getSubject() {
      return this.__subject;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("From: ");
      var1.append(this.__from);
      var1.append("\nNewsgroups: ");
      var1.append(this.__newsgroups.toString());
      var1.append("\nSubject: ");
      var1.append(this.__subject);
      var1.append('\n');
      if (this.__headerFields.length() > 0) {
         var1.append(this.__headerFields.toString());
      }

      var1.append('\n');
      return var1.toString();
   }
}
