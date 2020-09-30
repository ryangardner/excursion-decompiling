package javax.mail.internet;

import com.sun.mail.util.LineOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.mail.MessagingException;

public class PreencodedMimeBodyPart extends MimeBodyPart {
   private String encoding;

   public PreencodedMimeBodyPart(String var1) {
      this.encoding = var1;
   }

   public String getEncoding() throws MessagingException {
      return this.encoding;
   }

   protected void updateHeaders() throws MessagingException {
      super.updateHeaders();
      MimeBodyPart.setEncoding(this, this.encoding);
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      LineOutputStream var2;
      if (var1 instanceof LineOutputStream) {
         var2 = (LineOutputStream)var1;
      } else {
         var2 = new LineOutputStream(var1);
      }

      Enumeration var3 = this.getAllHeaderLines();

      while(var3.hasMoreElements()) {
         var2.writeln((String)var3.nextElement());
      }

      var2.writeln();
      this.getDataHandler().writeTo(var1);
      var1.flush();
   }
}
