package javax.mail;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

public class Flags implements Cloneable, Serializable {
   private static final int ANSWERED_BIT = 1;
   private static final int DELETED_BIT = 2;
   private static final int DRAFT_BIT = 4;
   private static final int FLAGGED_BIT = 8;
   private static final int RECENT_BIT = 16;
   private static final int SEEN_BIT = 32;
   private static final int USER_BIT = Integer.MIN_VALUE;
   private static final long serialVersionUID = 6243590407214169028L;
   private int system_flags = 0;
   private Hashtable user_flags = null;

   public Flags() {
   }

   public Flags(String var1) {
      Hashtable var2 = new Hashtable(1);
      this.user_flags = var2;
      var2.put(var1.toLowerCase(Locale.ENGLISH), var1);
   }

   public Flags(Flags.Flag var1) {
      this.system_flags = var1.bit | 0;
   }

   public Flags(Flags var1) {
      this.system_flags = var1.system_flags;
      Hashtable var2 = var1.user_flags;
      if (var2 != null) {
         this.user_flags = (Hashtable)var2.clone();
      }

   }

   public void add(String var1) {
      if (this.user_flags == null) {
         this.user_flags = new Hashtable(1);
      }

      this.user_flags.put(var1.toLowerCase(Locale.ENGLISH), var1);
   }

   public void add(Flags.Flag var1) {
      int var2 = this.system_flags;
      this.system_flags = var1.bit | var2;
   }

   public void add(Flags var1) {
      this.system_flags |= var1.system_flags;
      if (var1.user_flags != null) {
         if (this.user_flags == null) {
            this.user_flags = new Hashtable(1);
         }

         Enumeration var2 = var1.user_flags.keys();

         while(var2.hasMoreElements()) {
            String var3 = (String)var2.nextElement();
            this.user_flags.put(var3, var1.user_flags.get(var3));
         }
      }

   }

   public Object clone() {
      Flags var1;
      try {
         var1 = (Flags)super.clone();
      } catch (CloneNotSupportedException var3) {
         var1 = null;
      }

      Hashtable var2 = this.user_flags;
      if (var2 != null && var1 != null) {
         var1.user_flags = (Hashtable)var2.clone();
      }

      return var1;
   }

   public boolean contains(String var1) {
      Hashtable var2 = this.user_flags;
      return var2 == null ? false : var2.containsKey(var1.toLowerCase(Locale.ENGLISH));
   }

   public boolean contains(Flags.Flag var1) {
      int var2 = this.system_flags;
      return (var1.bit & var2) != 0;
   }

   public boolean contains(Flags var1) {
      int var2 = var1.system_flags;
      if ((this.system_flags & var2) != var2) {
         return false;
      } else {
         Hashtable var3 = var1.user_flags;
         if (var3 != null) {
            if (this.user_flags == null) {
               return false;
            }

            Enumeration var4 = var3.keys();

            while(var4.hasMoreElements()) {
               if (!this.user_flags.containsKey(var4.nextElement())) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Flags)) {
         return false;
      } else {
         Flags var3 = (Flags)var1;
         if (var3.system_flags != this.system_flags) {
            return false;
         } else if (var3.user_flags == null && this.user_flags == null) {
            return true;
         } else {
            Hashtable var2 = var3.user_flags;
            if (var2 != null && this.user_flags != null && var2.size() == this.user_flags.size()) {
               Enumeration var4 = var3.user_flags.keys();

               do {
                  if (!var4.hasMoreElements()) {
                     return true;
                  }
               } while(this.user_flags.containsKey(var4.nextElement()));
            }

            return false;
         }
      }
   }

   public Flags.Flag[] getSystemFlags() {
      Vector var1 = new Vector();
      if ((this.system_flags & 1) != 0) {
         var1.addElement(Flags.Flag.ANSWERED);
      }

      if ((this.system_flags & 2) != 0) {
         var1.addElement(Flags.Flag.DELETED);
      }

      if ((this.system_flags & 4) != 0) {
         var1.addElement(Flags.Flag.DRAFT);
      }

      if ((this.system_flags & 8) != 0) {
         var1.addElement(Flags.Flag.FLAGGED);
      }

      if ((this.system_flags & 16) != 0) {
         var1.addElement(Flags.Flag.RECENT);
      }

      if ((this.system_flags & 32) != 0) {
         var1.addElement(Flags.Flag.SEEN);
      }

      if ((this.system_flags & Integer.MIN_VALUE) != 0) {
         var1.addElement(Flags.Flag.USER);
      }

      Flags.Flag[] var2 = new Flags.Flag[var1.size()];
      var1.copyInto(var2);
      return var2;
   }

   public String[] getUserFlags() {
      Vector var1 = new Vector();
      Hashtable var2 = this.user_flags;
      if (var2 != null) {
         Enumeration var3 = var2.elements();

         while(var3.hasMoreElements()) {
            var1.addElement(var3.nextElement());
         }
      }

      String[] var4 = new String[var1.size()];
      var1.copyInto(var4);
      return var4;
   }

   public int hashCode() {
      int var1 = this.system_flags;
      Hashtable var2 = this.user_flags;
      int var3 = var1;
      if (var2 != null) {
         Enumeration var4 = var2.keys();

         for(var3 = var1; var4.hasMoreElements(); var3 += ((String)var4.nextElement()).hashCode()) {
         }
      }

      return var3;
   }

   public void remove(String var1) {
      Hashtable var2 = this.user_flags;
      if (var2 != null) {
         var2.remove(var1.toLowerCase(Locale.ENGLISH));
      }

   }

   public void remove(Flags.Flag var1) {
      int var2 = this.system_flags;
      this.system_flags = var1.bit & var2;
   }

   public void remove(Flags var1) {
      this.system_flags &= var1.system_flags;
      Hashtable var2 = var1.user_flags;
      if (var2 != null) {
         if (this.user_flags == null) {
            return;
         }

         Enumeration var3 = var2.keys();

         while(var3.hasMoreElements()) {
            this.user_flags.remove(var3.nextElement());
         }
      }

   }

   public static final class Flag {
      public static final Flags.Flag ANSWERED = new Flags.Flag(1);
      public static final Flags.Flag DELETED = new Flags.Flag(2);
      public static final Flags.Flag DRAFT = new Flags.Flag(4);
      public static final Flags.Flag FLAGGED = new Flags.Flag(8);
      public static final Flags.Flag RECENT = new Flags.Flag(16);
      public static final Flags.Flag SEEN = new Flags.Flag(32);
      public static final Flags.Flag USER = new Flags.Flag(Integer.MIN_VALUE);
      private int bit;

      private Flag(int var1) {
         this.bit = var1;
      }
   }
}
