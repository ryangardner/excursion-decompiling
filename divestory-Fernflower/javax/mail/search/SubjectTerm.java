package javax.mail.search;

import javax.mail.Message;

public final class SubjectTerm extends StringTerm {
   private static final long serialVersionUID = 7481568618055573432L;

   public SubjectTerm(String var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof SubjectTerm) ? false : super.equals(var1);
   }

   public boolean match(Message var1) {
      String var3;
      try {
         var3 = var1.getSubject();
      } catch (Exception var2) {
         return false;
      }

      return var3 == null ? false : super.match(var3);
   }
}
