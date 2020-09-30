package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.Date;
import org.apache.http.cookie.SetCookie2;

public class BasicClientCookie2 extends BasicClientCookie implements SetCookie2, Serializable {
   private static final long serialVersionUID = -7744598295706617057L;
   private String commentURL;
   private boolean discard;
   private int[] ports;

   public BasicClientCookie2(String var1, String var2) {
      super(var1, var2);
   }

   public Object clone() throws CloneNotSupportedException {
      BasicClientCookie2 var1 = (BasicClientCookie2)super.clone();
      int[] var2 = this.ports;
      if (var2 != null) {
         var1.ports = (int[])var2.clone();
      }

      return var1;
   }

   public String getCommentURL() {
      return this.commentURL;
   }

   public int[] getPorts() {
      return this.ports;
   }

   public boolean isExpired(Date var1) {
      boolean var2;
      if (!this.discard && !super.isExpired(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean isPersistent() {
      boolean var1;
      if (!this.discard && super.isPersistent()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setCommentURL(String var1) {
      this.commentURL = var1;
   }

   public void setDiscard(boolean var1) {
      this.discard = var1;
   }

   public void setPorts(int[] var1) {
      this.ports = var1;
   }
}
