package org.apache.http.impl.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieIdentityComparator;

public class BasicCookieStore implements CookieStore, Serializable {
   private static final long serialVersionUID = -7581093305228232025L;
   private final TreeSet<Cookie> cookies = new TreeSet(new CookieIdentityComparator());

   public void addCookie(Cookie var1) {
      synchronized(this){}
      if (var1 != null) {
         try {
            this.cookies.remove(var1);
            Date var2 = new Date();
            if (!var1.isExpired(var2)) {
               this.cookies.add(var1);
            }
         } finally {
            ;
         }
      }

   }

   public void addCookies(Cookie[] var1) {
      synchronized(this){}
      if (var1 != null) {
         Throwable var10000;
         label98: {
            boolean var10001;
            int var2;
            try {
               var2 = var1.length;
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label98;
            }

            int var3 = 0;

            while(true) {
               if (var3 >= var2) {
                  return;
               }

               try {
                  this.addCookie(var1[var3]);
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break;
               }

               ++var3;
            }
         }

         Throwable var10 = var10000;
         throw var10;
      }
   }

   public void clear() {
      synchronized(this){}

      try {
         this.cookies.clear();
      } finally {
         ;
      }

   }

   public boolean clearExpired(Date var1) {
      synchronized(this){}
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else {
         Throwable var10000;
         label97: {
            boolean var10001;
            Iterator var3;
            try {
               var3 = this.cookies.iterator();
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label97;
            }

            while(true) {
               try {
                  do {
                     if (!var3.hasNext()) {
                        return var2;
                     }
                  } while(!((Cookie)var3.next()).isExpired(var1));

                  var3.remove();
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break;
               }

               var2 = true;
            }
         }

         Throwable var10 = var10000;
         throw var10;
      }
   }

   public List<Cookie> getCookies() {
      synchronized(this){}

      ArrayList var1;
      try {
         var1 = new ArrayList(this.cookies);
      } finally {
         ;
      }

      return var1;
   }

   public String toString() {
      synchronized(this){}

      String var1;
      try {
         var1 = this.cookies.toString();
      } finally {
         ;
      }

      return var1;
   }
}
