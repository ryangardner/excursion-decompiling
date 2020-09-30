package javax.mail.search;

import javax.mail.Message;

public final class MessageIDTerm extends StringTerm {
   private static final long serialVersionUID = -2121096296454691963L;

   public MessageIDTerm(String var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof MessageIDTerm) ? false : super.equals(var1);
   }

   public boolean match(Message var1) {
      String[] var4;
      try {
         var4 = var1.getHeader("Message-ID");
      } catch (Exception var3) {
         return false;
      }

      if (var4 == null) {
         return false;
      } else {
         for(int var2 = 0; var2 < var4.length; ++var2) {
            if (super.match(var4[var2])) {
               return true;
            }
         }

         return false;
      }
   }
}
