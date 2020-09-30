package net.sbbi.upnp.xpath;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;

public class JXPathFilterSource extends InputSource {
   private static final Logger log = Logger.getLogger(JXPathFilterSource.class.getName());
   private final char buggyChar = (char)0;

   public JXPathFilterSource() {
   }

   public JXPathFilterSource(InputStream var1) {
      super(var1);
      if (var1 != null) {
         StringBuffer var2 = new StringBuffer();

         IOException var10000;
         label36: {
            byte[] var3;
            boolean var10001;
            try {
               var3 = new byte[512];
            } catch (IOException var8) {
               var10000 = var8;
               var10001 = false;
               break label36;
            }

            while(true) {
               int var4;
               try {
                  var4 = var1.read(var3);
               } catch (IOException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break;
               }

               if (var4 == -1) {
                  String var13 = var2.toString();
                  Logger var12 = log;
                  StringBuilder var10 = new StringBuilder("Readen raw xml doc:\n");
                  var10.append(var13);
                  var12.fine(var10.toString());
                  String var11 = var13;
                  if (var13.indexOf(0) != -1) {
                     var11 = var13.replace('\u0000', ' ');
                  }

                  this.setByteStream(new ByteArrayInputStream(var11.getBytes()));
                  return;
               }

               try {
                  String var5 = new String(var3, 0, var4);
                  var2.append(var5);
               } catch (IOException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break;
               }
            }
         }

         IOException var9 = var10000;
         log.log(Level.SEVERE, "IOException occured during XML reception", var9);
      }
   }

   public JXPathFilterSource(Reader var1) {
      super(var1);
      if (var1 != null) {
         StringBuffer var2 = new StringBuffer();

         IOException var10000;
         label36: {
            char[] var3;
            boolean var10001;
            try {
               var3 = new char[512];
            } catch (IOException var8) {
               var10000 = var8;
               var10001 = false;
               break label36;
            }

            while(true) {
               int var4;
               try {
                  var4 = var1.read(var3);
               } catch (IOException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break;
               }

               if (var4 == -1) {
                  String var12 = var2.toString();
                  Logger var10 = log;
                  StringBuilder var13 = new StringBuilder("Readen raw xml doc:\n");
                  var13.append(var12);
                  var10.fine(var13.toString());
                  String var11 = var12;
                  if (var12.indexOf(0) != -1) {
                     var11 = var12.replace('\u0000', ' ');
                  }

                  this.setCharacterStream(new CharArrayReader(var11.toCharArray()));
                  return;
               }

               try {
                  String var5 = new String(var3, 0, var4);
                  var2.append(var5);
               } catch (IOException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break;
               }
            }
         }

         IOException var9 = var10000;
         log.log(Level.SEVERE, "IOException occured during XML reception", var9);
      }
   }

   public JXPathFilterSource(String var1) {
      super(var1);
   }
}
