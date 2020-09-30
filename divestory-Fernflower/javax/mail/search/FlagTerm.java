package javax.mail.search;

import javax.mail.Flags;
import javax.mail.Message;

public final class FlagTerm extends SearchTerm {
   private static final long serialVersionUID = -142991500302030647L;
   protected Flags flags;
   protected boolean set;

   public FlagTerm(Flags var1, boolean var2) {
      this.flags = var1;
      this.set = var2;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof FlagTerm)) {
         return false;
      } else {
         FlagTerm var2 = (FlagTerm)var1;
         return var2.set == this.set && var2.flags.equals(this.flags);
      }
   }

   public Flags getFlags() {
      return (Flags)this.flags.clone();
   }

   public boolean getTestSet() {
      return this.set;
   }

   public int hashCode() {
      int var1;
      if (this.set) {
         var1 = this.flags.hashCode();
      } else {
         var1 = this.flags.hashCode();
      }

      return var1;
   }

   public boolean match(Message var1) {
      boolean var10001;
      Flags var11;
      try {
         var11 = var1.getFlags();
         if (this.set) {
            if (var11.contains(this.flags)) {
               return true;
            }

            return false;
         }
      } catch (Exception var10) {
         var10001 = false;
         return false;
      }

      Flags.Flag[] var2;
      try {
         var2 = this.flags.getSystemFlags();
      } catch (Exception var9) {
         var10001 = false;
         return false;
      }

      int var3 = 0;

      String[] var12;
      while(true) {
         try {
            if (var3 >= var2.length) {
               var12 = this.flags.getUserFlags();
               break;
            }
         } catch (Exception var8) {
            var10001 = false;
            return false;
         }

         boolean var4;
         try {
            var4 = var11.contains(var2[var3]);
         } catch (Exception var7) {
            var10001 = false;
            return false;
         }

         if (var4) {
            return false;
         }

         ++var3;
      }

      var3 = 0;

      while(true) {
         try {
            if (var3 >= var12.length) {
               return true;
            }
         } catch (Exception var5) {
            var10001 = false;
            break;
         }

         try {
            if (var11.contains(var12[var3])) {
               return false;
            }
         } catch (Exception var6) {
            var10001 = false;
            break;
         }

         ++var3;
      }

      return false;
   }
}
