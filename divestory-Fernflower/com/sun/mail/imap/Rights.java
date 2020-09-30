package com.sun.mail.imap;

import java.util.Vector;

public class Rights implements Cloneable {
   private boolean[] rights;

   public Rights() {
      this.rights = new boolean[128];
   }

   public Rights(Rights.Right var1) {
      boolean[] var2 = new boolean[128];
      this.rights = var2;
      var2[var1.right] = true;
   }

   public Rights(Rights var1) {
      boolean[] var2 = new boolean[128];
      this.rights = var2;
      System.arraycopy(var1.rights, 0, var2, 0, var2.length);
   }

   public Rights(String var1) {
      this.rights = new boolean[128];

      for(int var2 = 0; var2 < var1.length(); ++var2) {
         this.add(Rights.Right.getInstance(var1.charAt(var2)));
      }

   }

   public void add(Rights.Right var1) {
      this.rights[var1.right] = true;
   }

   public void add(Rights var1) {
      int var2 = 0;

      while(true) {
         boolean[] var3 = var1.rights;
         if (var2 >= var3.length) {
            return;
         }

         if (var3[var2]) {
            this.rights[var2] = true;
         }

         ++var2;
      }
   }

   public Object clone() {
      Object var1 = null;

      Rights var2;
      try {
         var2 = (Rights)super.clone();
      } catch (CloneNotSupportedException var4) {
         var2 = (Rights)var1;
         return var2;
      }

      try {
         boolean[] var5 = new boolean[128];
         var2.rights = var5;
         System.arraycopy(this.rights, 0, var5, 0, this.rights.length);
      } catch (CloneNotSupportedException var3) {
      }

      return var2;
   }

   public boolean contains(Rights.Right var1) {
      return this.rights[var1.right];
   }

   public boolean contains(Rights var1) {
      int var2 = 0;

      while(true) {
         boolean[] var3 = var1.rights;
         if (var2 >= var3.length) {
            return true;
         }

         if (var3[var2] && !this.rights[var2]) {
            return false;
         }

         ++var2;
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Rights)) {
         return false;
      } else {
         Rights var2 = (Rights)var1;
         int var3 = 0;

         while(true) {
            boolean[] var4 = var2.rights;
            if (var3 >= var4.length) {
               return true;
            }

            if (var4[var3] != this.rights[var3]) {
               return false;
            }

            ++var3;
         }
      }
   }

   public Rights.Right[] getRights() {
      Vector var1 = new Vector();
      int var2 = 0;

      while(true) {
         boolean[] var3 = this.rights;
         if (var2 >= var3.length) {
            Rights.Right[] var4 = new Rights.Right[var1.size()];
            var1.copyInto(var4);
            return var4;
         }

         if (var3[var2]) {
            var1.addElement(Rights.Right.getInstance((char)var2));
         }

         ++var2;
      }
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         boolean[] var3 = this.rights;
         if (var1 >= var3.length) {
            return var2;
         }

         int var4 = var2;
         if (var3[var1]) {
            var4 = var2 + 1;
         }

         ++var1;
         var2 = var4;
      }
   }

   public void remove(Rights.Right var1) {
      this.rights[var1.right] = false;
   }

   public void remove(Rights var1) {
      int var2 = 0;

      while(true) {
         boolean[] var3 = var1.rights;
         if (var2 >= var3.length) {
            return;
         }

         if (var3[var2]) {
            this.rights[var2] = false;
         }

         ++var2;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = 0;

      while(true) {
         boolean[] var3 = this.rights;
         if (var2 >= var3.length) {
            return var1.toString();
         }

         if (var3[var2]) {
            var1.append((char)var2);
         }

         ++var2;
      }
   }

   public static final class Right {
      public static final Rights.Right ADMINISTER = getInstance('a');
      public static final Rights.Right CREATE = getInstance('c');
      public static final Rights.Right DELETE = getInstance('d');
      public static final Rights.Right INSERT = getInstance('i');
      public static final Rights.Right KEEP_SEEN = getInstance('s');
      public static final Rights.Right LOOKUP = getInstance('l');
      public static final Rights.Right POST = getInstance('p');
      public static final Rights.Right READ = getInstance('r');
      public static final Rights.Right WRITE = getInstance('w');
      private static Rights.Right[] cache = new Rights.Right[128];
      char right;

      private Right(char var1) {
         if (var1 < 128) {
            this.right = (char)var1;
         } else {
            throw new IllegalArgumentException("Right must be ASCII");
         }
      }

      public static Rights.Right getInstance(char var0) {
         synchronized(Rights.Right.class){}
         Throwable var10000;
         boolean var10001;
         if (var0 < 128) {
            label121: {
               Rights.Right var2;
               label115: {
                  Rights.Right[] var1;
                  try {
                     if (cache[var0] != null) {
                        break label115;
                     }

                     var1 = cache;
                     var2 = new Rights.Right(var0);
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label121;
                  }

                  var1[var0] = var2;
               }

               try {
                  var2 = cache[var0];
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label121;
               }

               return var2;
            }
         } else {
            label118:
            try {
               IllegalArgumentException var16 = new IllegalArgumentException("Right must be ASCII");
               throw var16;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label118;
            }
         }

         Throwable var15 = var10000;
         throw var15;
      }

      public String toString() {
         return String.valueOf(this.right);
      }
   }
}
