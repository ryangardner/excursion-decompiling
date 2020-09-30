package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.LineOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessageAware;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.MultipartDataSource;

public class MimeMultipart extends Multipart {
   private static boolean bmparse;
   private static boolean ignoreMissingBoundaryParameter;
   private static boolean ignoreMissingEndBoundary;
   private boolean complete;
   protected DataSource ds;
   protected boolean parsed;
   private String preamble;

   static {
      label78: {
         String var0;
         boolean var10001;
         try {
            var0 = System.getProperty("mail.mime.multipart.ignoremissingendboundary");
         } catch (SecurityException var9) {
            var10001 = false;
            break label78;
         }

         boolean var1;
         boolean var2;
         label71: {
            label70: {
               var1 = false;
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("false")) {
                        break label70;
                     }
                  } catch (SecurityException var8) {
                     var10001 = false;
                     break label78;
                  }
               }

               var2 = true;
               break label71;
            }

            var2 = false;
         }

         try {
            ignoreMissingEndBoundary = var2;
            var0 = System.getProperty("mail.mime.multipart.ignoremissingboundaryparameter");
         } catch (SecurityException var7) {
            var10001 = false;
            break label78;
         }

         label59: {
            label58: {
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("false")) {
                        break label58;
                     }
                  } catch (SecurityException var6) {
                     var10001 = false;
                     break label78;
                  }
               }

               var2 = true;
               break label59;
            }

            var2 = false;
         }

         try {
            ignoreMissingBoundaryParameter = var2;
            var0 = System.getProperty("mail.mime.multipart.bmparse");
         } catch (SecurityException var5) {
            var10001 = false;
            break label78;
         }

         label47: {
            label46: {
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("false")) {
                        break label46;
                     }
                  } catch (SecurityException var4) {
                     var10001 = false;
                     break label78;
                  }
               }

               var2 = true;
               break label47;
            }

            var2 = var1;
         }

         try {
            bmparse = var2;
         } catch (SecurityException var3) {
            var10001 = false;
         }
      }

   }

   public MimeMultipart() {
      this("mixed");
   }

   public MimeMultipart(String var1) {
      this.ds = null;
      this.parsed = true;
      this.complete = true;
      this.preamble = null;
      String var2 = UniqueValue.getUniqueBoundaryValue();
      ContentType var3 = new ContentType("multipart", var1, (ParameterList)null);
      var3.setParameter("boundary", var2);
      this.contentType = var3.toString();
   }

   public MimeMultipart(DataSource var1) throws MessagingException {
      this.ds = null;
      this.parsed = true;
      this.complete = true;
      this.preamble = null;
      if (var1 instanceof MessageAware) {
         this.setParent(((MessageAware)var1).getMessageContext().getPart());
      }

      if (var1 instanceof MultipartDataSource) {
         this.setMultipartDataSource((MultipartDataSource)var1);
      } else {
         this.parsed = false;
         this.ds = var1;
         this.contentType = var1.getContentType();
      }
   }

   private void parsebm() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private static int readFully(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      int var4 = 0;
      int var5 = var2;
      var2 = var3;
      if (var3 == 0) {
         return 0;
      } else {
         while(var2 > 0) {
            var3 = var0.read(var1, var5, var2);
            if (var3 <= 0) {
               break;
            }

            var5 += var3;
            var4 += var3;
            var2 -= var3;
         }

         if (var4 <= 0) {
            var4 = -1;
         }

         return var4;
      }
   }

   private void skipFully(InputStream var1, long var2) throws IOException {
      while(var2 > 0L) {
         long var4 = var1.skip(var2);
         if (var4 <= 0L) {
            throw new EOFException("can't skip");
         }

         var2 -= var4;
      }

   }

   public void addBodyPart(BodyPart var1) throws MessagingException {
      synchronized(this){}

      try {
         this.parse();
         super.addBodyPart(var1);
      } finally {
         ;
      }

   }

   public void addBodyPart(BodyPart var1, int var2) throws MessagingException {
      synchronized(this){}

      try {
         this.parse();
         super.addBodyPart(var1, var2);
      } finally {
         ;
      }

   }

   protected InternetHeaders createInternetHeaders(InputStream var1) throws MessagingException {
      return new InternetHeaders(var1);
   }

   protected MimeBodyPart createMimeBodyPart(InputStream var1) throws MessagingException {
      return new MimeBodyPart(var1);
   }

   protected MimeBodyPart createMimeBodyPart(InternetHeaders var1, byte[] var2) throws MessagingException {
      return new MimeBodyPart(var1, var2);
   }

   public BodyPart getBodyPart(int var1) throws MessagingException {
      synchronized(this){}

      BodyPart var2;
      try {
         this.parse();
         var2 = super.getBodyPart(var1);
      } finally {
         ;
      }

      return var2;
   }

   public BodyPart getBodyPart(String var1) throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label163: {
         int var2;
         boolean var10001;
         try {
            this.parse();
            var2 = this.getCount();
         } catch (Throwable var18) {
            var10000 = var18;
            var10001 = false;
            break label163;
         }

         int var3 = 0;

         while(true) {
            if (var3 >= var2) {
               return null;
            }

            MimeBodyPart var4;
            String var5;
            try {
               var4 = (MimeBodyPart)this.getBodyPart(var3);
               var5 = var4.getContentID();
            } catch (Throwable var17) {
               var10000 = var17;
               var10001 = false;
               break;
            }

            if (var5 != null) {
               boolean var6;
               try {
                  var6 = var5.equals(var1);
               } catch (Throwable var16) {
                  var10000 = var16;
                  var10001 = false;
                  break;
               }

               if (var6) {
                  return var4;
               }
            }

            ++var3;
         }
      }

      Throwable var19 = var10000;
      throw var19;
   }

   public int getCount() throws MessagingException {
      synchronized(this){}

      int var1;
      try {
         this.parse();
         var1 = super.getCount();
      } finally {
         ;
      }

      return var1;
   }

   public String getPreamble() throws MessagingException {
      synchronized(this){}

      String var1;
      try {
         this.parse();
         var1 = this.preamble;
      } finally {
         ;
      }

      return var1;
   }

   public boolean isComplete() throws MessagingException {
      synchronized(this){}

      boolean var1;
      try {
         this.parse();
         var1 = this.complete;
      } finally {
         ;
      }

      return var1;
   }

   protected void parse() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void removeBodyPart(int var1) throws MessagingException {
      this.parse();
      super.removeBodyPart(var1);
   }

   public boolean removeBodyPart(BodyPart var1) throws MessagingException {
      this.parse();
      return super.removeBodyPart(var1);
   }

   public void setPreamble(String var1) throws MessagingException {
      synchronized(this){}

      try {
         this.preamble = var1;
      } finally {
         ;
      }

   }

   public void setSubType(String var1) throws MessagingException {
      synchronized(this){}

      try {
         ContentType var2 = new ContentType(this.contentType);
         var2.setSubType(var1);
         this.contentType = var2.toString();
      } finally {
         ;
      }

   }

   protected void updateHeaders() throws MessagingException {
      for(int var1 = 0; var1 < this.parts.size(); ++var1) {
         ((MimeBodyPart)this.parts.elementAt(var1)).updateHeaders();
      }

   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      synchronized(this){}

      Throwable var10000;
      label183: {
         boolean var10001;
         String var20;
         LineOutputStream var21;
         try {
            this.parse();
            StringBuilder var2 = new StringBuilder("--");
            ContentType var3 = new ContentType(this.contentType);
            var2.append(var3.getParameter("boundary"));
            var20 = var2.toString();
            var21 = new LineOutputStream(var1);
            if (this.preamble != null) {
               byte[] var4 = ASCIIUtility.getBytes(this.preamble);
               var21.write(var4);
               if (var4.length > 0 && var4[var4.length - 1] != 13 && var4[var4.length - 1] != 10) {
                  var21.writeln();
               }
            }
         } catch (Throwable var17) {
            var10000 = var17;
            var10001 = false;
            break label183;
         }

         int var5 = 0;

         while(true) {
            try {
               if (var5 >= this.parts.size()) {
                  StringBuilder var19 = new StringBuilder(String.valueOf(var20));
                  var19.append("--");
                  var21.writeln(var19.toString());
                  return;
               }
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break;
            }

            try {
               var21.writeln(var20);
               ((MimeBodyPart)this.parts.elementAt(var5)).writeTo(var1);
               var21.writeln();
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break;
            }

            ++var5;
         }
      }

      Throwable var18 = var10000;
      throw var18;
   }
}
