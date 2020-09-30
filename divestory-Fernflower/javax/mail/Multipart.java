package javax.mail;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public abstract class Multipart {
   protected String contentType = "multipart/mixed";
   protected Part parent;
   protected Vector parts = new Vector();

   protected Multipart() {
   }

   public void addBodyPart(BodyPart var1) throws MessagingException {
      synchronized(this){}

      try {
         if (this.parts == null) {
            Vector var2 = new Vector();
            this.parts = var2;
         }

         this.parts.addElement(var1);
         var1.setParent(this);
      } finally {
         ;
      }

   }

   public void addBodyPart(BodyPart var1, int var2) throws MessagingException {
      synchronized(this){}

      try {
         if (this.parts == null) {
            Vector var3 = new Vector();
            this.parts = var3;
         }

         this.parts.insertElementAt(var1, var2);
         var1.setParent(this);
      } finally {
         ;
      }

   }

   public BodyPart getBodyPart(int var1) throws MessagingException {
      synchronized(this){}

      BodyPart var2;
      try {
         if (this.parts == null) {
            IndexOutOfBoundsException var5 = new IndexOutOfBoundsException("No such BodyPart");
            throw var5;
         }

         var2 = (BodyPart)this.parts.elementAt(var1);
      } finally {
         ;
      }

      return var2;
   }

   public String getContentType() {
      return this.contentType;
   }

   public int getCount() throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label78: {
         Vector var1;
         boolean var10001;
         try {
            var1 = this.parts;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label78;
         }

         if (var1 == null) {
            return 0;
         }

         int var2;
         try {
            var2 = this.parts.size();
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label78;
         }

         return var2;
      }

      Throwable var9 = var10000;
      throw var9;
   }

   public Part getParent() {
      synchronized(this){}

      Part var1;
      try {
         var1 = this.parent;
      } finally {
         ;
      }

      return var1;
   }

   public void removeBodyPart(int var1) throws MessagingException {
      synchronized(this){}

      try {
         if (this.parts == null) {
            IndexOutOfBoundsException var5 = new IndexOutOfBoundsException("No such BodyPart");
            throw var5;
         }

         BodyPart var2 = (BodyPart)this.parts.elementAt(var1);
         this.parts.removeElementAt(var1);
         var2.setParent((Multipart)null);
      } finally {
         ;
      }

   }

   public boolean removeBodyPart(BodyPart var1) throws MessagingException {
      synchronized(this){}

      boolean var2;
      try {
         if (this.parts == null) {
            MessagingException var5 = new MessagingException("No such body part");
            throw var5;
         }

         var2 = this.parts.removeElement(var1);
         var1.setParent((Multipart)null);
      } finally {
         ;
      }

      return var2;
   }

   protected void setMultipartDataSource(MultipartDataSource var1) throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label92: {
         boolean var10001;
         int var2;
         try {
            this.contentType = var1.getContentType();
            var2 = var1.getCount();
         } catch (Throwable var9) {
            var10000 = var9;
            var10001 = false;
            break label92;
         }

         int var3 = 0;

         while(true) {
            if (var3 >= var2) {
               return;
            }

            try {
               this.addBodyPart(var1.getBodyPart(var3));
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

   public void setParent(Part var1) {
      synchronized(this){}

      try {
         this.parent = var1;
      } finally {
         ;
      }

   }

   public abstract void writeTo(OutputStream var1) throws IOException, MessagingException;
}
