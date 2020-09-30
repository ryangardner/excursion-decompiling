package javax.mail;

public class MessageContext {
   private Part part;

   public MessageContext(Part var1) {
      this.part = var1;
   }

   private static Message getMessage(Part var0) throws MessagingException {
      while(var0 != null) {
         if (var0 instanceof Message) {
            return (Message)var0;
         }

         Multipart var1 = ((BodyPart)var0).getParent();
         if (var1 == null) {
            return null;
         }

         var0 = var1.getParent();
      }

      return null;
   }

   public Message getMessage() {
      try {
         Message var1 = getMessage(this.part);
         return var1;
      } catch (MessagingException var2) {
         return null;
      }
   }

   public Part getPart() {
      return this.part;
   }

   public Session getSession() {
      Message var1 = this.getMessage();
      Session var2;
      if (var1 != null) {
         var2 = var1.session;
      } else {
         var2 = null;
      }

      return var2;
   }
}
