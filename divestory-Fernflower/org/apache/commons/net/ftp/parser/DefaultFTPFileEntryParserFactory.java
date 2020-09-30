package org.apache.commons.net.ftp.parser;

import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFileEntryParser;

public class DefaultFTPFileEntryParserFactory implements FTPFileEntryParserFactory {
   private static final String JAVA_IDENTIFIER = "\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*";
   private static final String JAVA_QUALIFIED_NAME = "(\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*\\.)+\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*";
   private static final Pattern JAVA_QUALIFIED_NAME_PATTERN = Pattern.compile("(\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*\\.)+\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*");

   private FTPFileEntryParser createFileEntryParser(String var1, FTPClientConfig var2) {
      FTPFileEntryParser var4;
      label79: {
         if (JAVA_QUALIFIED_NAME_PATTERN.matcher(var1).matches()) {
            try {
               Class var3 = Class.forName(var1);

               ParserInitializationException var15;
               try {
                  var4 = (FTPFileEntryParser)var3.newInstance();
                  break label79;
               } catch (ClassCastException var7) {
                  StringBuilder var6 = new StringBuilder();
                  var6.append(var3.getName());
                  var6.append(" does not implement the interface ");
                  var6.append("org.apache.commons.net.ftp.FTPFileEntryParser.");
                  var15 = new ParserInitializationException(var6.toString(), var7);
                  throw var15;
               } catch (Exception var8) {
                  var15 = new ParserInitializationException("Error initializing parser", var8);
                  throw var15;
               } catch (ExceptionInInitializerError var9) {
                  ParserInitializationException var12 = new ParserInitializationException("Error initializing parser", var9);
                  throw var12;
               }
            } catch (ClassNotFoundException var10) {
            }
         }

         var4 = null;
      }

      Object var13 = var4;
      if (var4 == null) {
         String var14 = var1.toUpperCase(Locale.ENGLISH);
         if (var14.indexOf("UNIX") >= 0) {
            var13 = new UnixFTPEntryParser(var2, false);
         } else if (var14.indexOf("UNIX_LTRIM") >= 0) {
            var13 = new UnixFTPEntryParser(var2, true);
         } else if (var14.indexOf("VMS") >= 0) {
            var13 = new VMSVersioningFTPEntryParser(var2);
         } else if (var14.indexOf("WINDOWS") >= 0) {
            var13 = this.createNTFTPEntryParser(var2);
         } else if (var14.indexOf("OS/2") >= 0) {
            var13 = new OS2FTPEntryParser(var2);
         } else if (var14.indexOf("OS/400") < 0 && var14.indexOf("AS/400") < 0) {
            if (var14.indexOf("MVS") >= 0) {
               var13 = new MVSFTPEntryParser();
            } else if (var14.indexOf("NETWARE") >= 0) {
               var13 = new NetwareFTPEntryParser(var2);
            } else if (var14.indexOf("MACOS PETER") >= 0) {
               var13 = new MacOsPeterFTPEntryParser(var2);
            } else {
               if (var14.indexOf("TYPE: L8") < 0) {
                  StringBuilder var11 = new StringBuilder();
                  var11.append("Unknown parser type: ");
                  var11.append(var1);
                  throw new ParserInitializationException(var11.toString());
               }

               var13 = new UnixFTPEntryParser(var2);
            }
         } else {
            var13 = this.createOS400FTPEntryParser(var2);
         }
      }

      if (var13 instanceof Configurable) {
         ((Configurable)var13).configure(var2);
      }

      return (FTPFileEntryParser)var13;
   }

   private FTPFileEntryParser createNTFTPEntryParser(FTPClientConfig var1) {
      if (var1 != null && "WINDOWS".equals(var1.getServerSystemKey())) {
         return new NTFTPEntryParser(var1);
      } else {
         NTFTPEntryParser var2 = new NTFTPEntryParser(var1);
         boolean var3 = false;
         boolean var4 = var3;
         if (var1 != null) {
            var4 = var3;
            if ("UNIX_LTRIM".equals(var1.getServerSystemKey())) {
               var4 = true;
            }
         }

         return new CompositeFileEntryParser(new FTPFileEntryParser[]{var2, new UnixFTPEntryParser(var1, var4)});
      }
   }

   private FTPFileEntryParser createOS400FTPEntryParser(FTPClientConfig var1) {
      if (var1 != null && "OS/400".equals(var1.getServerSystemKey())) {
         return new OS400FTPEntryParser(var1);
      } else {
         OS400FTPEntryParser var2 = new OS400FTPEntryParser(var1);
         boolean var3 = false;
         boolean var4 = var3;
         if (var1 != null) {
            var4 = var3;
            if ("UNIX_LTRIM".equals(var1.getServerSystemKey())) {
               var4 = true;
            }
         }

         return new CompositeFileEntryParser(new FTPFileEntryParser[]{var2, new UnixFTPEntryParser(var1, var4)});
      }
   }

   public FTPFileEntryParser createFileEntryParser(String var1) {
      if (var1 != null) {
         return this.createFileEntryParser(var1, (FTPClientConfig)null);
      } else {
         throw new ParserInitializationException("Parser key cannot be null");
      }
   }

   public FTPFileEntryParser createFileEntryParser(FTPClientConfig var1) throws ParserInitializationException {
      return this.createFileEntryParser(var1.getServerSystemKey(), var1);
   }

   public FTPFileEntryParser createMVSEntryParser() {
      return new MVSFTPEntryParser();
   }

   public FTPFileEntryParser createNTFTPEntryParser() {
      return this.createNTFTPEntryParser((FTPClientConfig)null);
   }

   public FTPFileEntryParser createNetwareFTPEntryParser() {
      return new NetwareFTPEntryParser();
   }

   public FTPFileEntryParser createOS2FTPEntryParser() {
      return new OS2FTPEntryParser();
   }

   public FTPFileEntryParser createOS400FTPEntryParser() {
      return this.createOS400FTPEntryParser((FTPClientConfig)null);
   }

   public FTPFileEntryParser createUnixFTPEntryParser() {
      return new UnixFTPEntryParser();
   }

   public FTPFileEntryParser createVMSVersioningFTPEntryParser() {
      return new VMSVersioningFTPEntryParser();
   }
}
