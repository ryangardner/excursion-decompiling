package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.SetCookie;

public class BasicClientCookie implements SetCookie, ClientCookie, Cloneable, Serializable {
   private static final long serialVersionUID = -3869795591041535538L;
   private Map<String, String> attribs;
   private String cookieComment;
   private String cookieDomain;
   private Date cookieExpiryDate;
   private String cookiePath;
   private int cookieVersion;
   private boolean isSecure;
   private final String name;
   private String value;

   public BasicClientCookie(String var1, String var2) {
      if (var1 != null) {
         this.name = var1;
         this.attribs = new HashMap();
         this.value = var2;
      } else {
         throw new IllegalArgumentException("Name may not be null");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      BasicClientCookie var1 = (BasicClientCookie)super.clone();
      var1.attribs = new HashMap(this.attribs);
      return var1;
   }

   public boolean containsAttribute(String var1) {
      boolean var2;
      if (this.attribs.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String getAttribute(String var1) {
      return (String)this.attribs.get(var1);
   }

   public String getComment() {
      return this.cookieComment;
   }

   public String getCommentURL() {
      return null;
   }

   public String getDomain() {
      return this.cookieDomain;
   }

   public Date getExpiryDate() {
      return this.cookieExpiryDate;
   }

   public String getName() {
      return this.name;
   }

   public String getPath() {
      return this.cookiePath;
   }

   public int[] getPorts() {
      return null;
   }

   public String getValue() {
      return this.value;
   }

   public int getVersion() {
      return this.cookieVersion;
   }

   public boolean isExpired(Date var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Date may not be null");
      } else {
         Date var2 = this.cookieExpiryDate;
         boolean var3;
         if (var2 != null && var2.getTime() <= var1.getTime()) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   public boolean isPersistent() {
      boolean var1;
      if (this.cookieExpiryDate != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isSecure() {
      return this.isSecure;
   }

   public void setAttribute(String var1, String var2) {
      this.attribs.put(var1, var2);
   }

   public void setComment(String var1) {
      this.cookieComment = var1;
   }

   public void setDomain(String var1) {
      if (var1 != null) {
         this.cookieDomain = var1.toLowerCase(Locale.ENGLISH);
      } else {
         this.cookieDomain = null;
      }

   }

   public void setExpiryDate(Date var1) {
      this.cookieExpiryDate = var1;
   }

   public void setPath(String var1) {
      this.cookiePath = var1;
   }

   public void setSecure(boolean var1) {
      this.isSecure = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public void setVersion(int var1) {
      this.cookieVersion = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[version: ");
      var1.append(Integer.toString(this.cookieVersion));
      var1.append("]");
      var1.append("[name: ");
      var1.append(this.name);
      var1.append("]");
      var1.append("[value: ");
      var1.append(this.value);
      var1.append("]");
      var1.append("[domain: ");
      var1.append(this.cookieDomain);
      var1.append("]");
      var1.append("[path: ");
      var1.append(this.cookiePath);
      var1.append("]");
      var1.append("[expiry: ");
      var1.append(this.cookieExpiryDate);
      var1.append("]");
      return var1.toString();
   }
}
