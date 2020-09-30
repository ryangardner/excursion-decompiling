package javax.mail.search;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;

public final class BodyTerm extends StringTerm {
   private static final long serialVersionUID = -4888862527916911385L;

   public BodyTerm(String var1) {
      super(var1);
   }

   private boolean matchPart(Part var1) {
      boolean var10001;
      String var11;
      label91: {
         try {
            if (var1.isMimeType("text/*")) {
               var11 = (String)var1.getContent();
               break label91;
            }
         } catch (Exception var9) {
            var10001 = false;
            return false;
         }

         int var2;
         Multipart var10;
         label89: {
            try {
               if (var1.isMimeType("multipart/*")) {
                  var10 = (Multipart)var1.getContent();
                  var2 = var10.getCount();
                  break label89;
               }
            } catch (Exception var8) {
               var10001 = false;
               return false;
            }

            try {
               if (var1.isMimeType("message/rfc822")) {
                  boolean var4 = this.matchPart((Part)var1.getContent());
                  return var4;
               }
            } catch (Exception var7) {
               var10001 = false;
            }

            return false;
         }

         for(int var3 = 0; var3 < var2; ++var3) {
            try {
               if (this.matchPart(var10.getBodyPart(var3))) {
                  return true;
               }
            } catch (Exception var6) {
               var10001 = false;
               return false;
            }
         }

         return false;
      }

      if (var11 == null) {
         return false;
      } else {
         try {
            return super.match(var11);
         } catch (Exception var5) {
            var10001 = false;
            return false;
         }
      }
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof BodyTerm) ? false : super.equals(var1);
   }

   public boolean match(Message var1) {
      return this.matchPart(var1);
   }
}
