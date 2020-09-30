package javax.mail.internet;

import com.sun.mail.util.LineInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.mail.Header;
import javax.mail.MessagingException;

public class InternetHeaders {
   protected List headers;

   public InternetHeaders() {
      ArrayList var1 = new ArrayList(40);
      this.headers = var1;
      var1.add(new InternetHeaders.InternetHeader("Return-Path", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Received", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Resent-Date", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Resent-From", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Resent-Sender", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Resent-To", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Resent-Cc", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Resent-Bcc", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Resent-Message-Id", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Date", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("From", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Sender", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Reply-To", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("To", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Cc", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Bcc", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Message-Id", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("In-Reply-To", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("References", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Subject", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Comments", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Keywords", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Errors-To", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("MIME-Version", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Content-Type", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Content-Transfer-Encoding", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Content-MD5", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader(":", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Content-Length", (String)null));
      this.headers.add(new InternetHeaders.InternetHeader("Status", (String)null));
   }

   public InternetHeaders(InputStream var1) throws MessagingException {
      this.headers = new ArrayList(40);
      this.load(var1);
   }

   public void addHeader(String var1, String var2) {
      int var3 = this.headers.size();
      boolean var4;
      if (!var1.equalsIgnoreCase("Received") && !var1.equalsIgnoreCase("Return-Path")) {
         var4 = false;
      } else {
         var4 = true;
      }

      if (var4) {
         var3 = 0;
      }

      for(int var5 = this.headers.size() - 1; var5 >= 0; --var5) {
         InternetHeaders.InternetHeader var6 = (InternetHeaders.InternetHeader)this.headers.get(var5);
         if (var1.equalsIgnoreCase(var6.getName())) {
            if (!var4) {
               this.headers.add(var5 + 1, new InternetHeaders.InternetHeader(var1, var2));
               return;
            }

            var3 = var5;
         }

         if (var6.getName().equals(":")) {
            var3 = var5;
         }
      }

      this.headers.add(var3, new InternetHeaders.InternetHeader(var1, var2));
   }

   public void addHeaderLine(String var1) {
      boolean var10001;
      char var2;
      try {
         var2 = var1.charAt(0);
      } catch (NoSuchElementException | StringIndexOutOfBoundsException var8) {
         var10001 = false;
         return;
      }

      InternetHeaders.InternetHeader var4;
      if (var2 != ' ' && var2 != '\t') {
         try {
            List var9 = this.headers;
            var4 = new InternetHeaders.InternetHeader(var1);
            var9.add(var4);
         } catch (NoSuchElementException | StringIndexOutOfBoundsException var6) {
            var10001 = false;
         }
      } else {
         try {
            var4 = (InternetHeaders.InternetHeader)this.headers.get(this.headers.size() - 1);
            String var3 = var4.line;
            StringBuilder var5 = new StringBuilder(String.valueOf(var3));
            var5.append("\r\n");
            var5.append(var1);
            var4.line = var5.toString();
         } catch (NoSuchElementException | StringIndexOutOfBoundsException var7) {
            var10001 = false;
         }
      }

   }

   public Enumeration getAllHeaderLines() {
      return this.getNonMatchingHeaderLines((String[])null);
   }

   public Enumeration getAllHeaders() {
      return new InternetHeaders.matchEnum(this.headers, (String[])null, false, false);
   }

   public String getHeader(String var1, String var2) {
      String[] var6 = this.getHeader(var1);
      if (var6 == null) {
         return null;
      } else {
         int var3 = var6.length;
         int var4 = 1;
         if (var3 != 1 && var2 != null) {
            StringBuffer var5;
            for(var5 = new StringBuffer(var6[0]); var4 < var6.length; ++var4) {
               var5.append(var2);
               var5.append(var6[var4]);
            }

            return var5.toString();
         } else {
            return var6[0];
         }
      }
   }

   public String[] getHeader(String var1) {
      Iterator var2 = this.headers.iterator();
      ArrayList var3 = new ArrayList();

      while(var2.hasNext()) {
         InternetHeaders.InternetHeader var4 = (InternetHeaders.InternetHeader)var2.next();
         if (var1.equalsIgnoreCase(var4.getName()) && var4.line != null) {
            var3.add(var4.getValue());
         }
      }

      if (var3.size() == 0) {
         return null;
      } else {
         return (String[])var3.toArray(new String[var3.size()]);
      }
   }

   public Enumeration getMatchingHeaderLines(String[] var1) {
      return new InternetHeaders.matchEnum(this.headers, var1, true, true);
   }

   public Enumeration getMatchingHeaders(String[] var1) {
      return new InternetHeaders.matchEnum(this.headers, var1, true, false);
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) {
      return new InternetHeaders.matchEnum(this.headers, var1, false, true);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) {
      return new InternetHeaders.matchEnum(this.headers, var1, false, false);
   }

   public void load(InputStream var1) throws MessagingException {
      LineInputStream var2 = new LineInputStream(var1);
      StringBuffer var3 = new StringBuffer();
      String var4 = null;

      int var6;
      do {
         String var14;
         label73: {
            IOException var10000;
            label79: {
               String var5;
               boolean var10001;
               try {
                  var5 = var2.readLine();
               } catch (IOException var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label79;
               }

               label83: {
                  if (var5 != null) {
                     label82: {
                        try {
                           if (!var5.startsWith(" ") && !var5.startsWith("\t")) {
                              break label82;
                           }
                        } catch (IOException var12) {
                           var10000 = var12;
                           var10001 = false;
                           break label79;
                        }

                        var14 = var4;
                        if (var4 != null) {
                           try {
                              var3.append(var4);
                           } catch (IOException var11) {
                              var10000 = var11;
                              var10001 = false;
                              break label79;
                           }

                           var14 = null;
                        }

                        try {
                           var3.append("\r\n");
                           var3.append(var5);
                           break label83;
                        } catch (IOException var10) {
                           var10000 = var10;
                           var10001 = false;
                           break label79;
                        }
                     }
                  }

                  if (var4 != null) {
                     try {
                        this.addHeaderLine(var4);
                     } catch (IOException var9) {
                        var10000 = var9;
                        var10001 = false;
                        break label79;
                     }
                  } else {
                     try {
                        if (var3.length() > 0) {
                           this.addHeaderLine(var3.toString());
                           var3.setLength(0);
                        }
                     } catch (IOException var8) {
                        var10000 = var8;
                        var10001 = false;
                        break label79;
                     }
                  }

                  var14 = var5;
               }

               if (var5 == null) {
                  break;
               }

               try {
                  var6 = var5.length();
                  break label73;
               } catch (IOException var7) {
                  var10000 = var7;
                  var10001 = false;
               }
            }

            IOException var15 = var10000;
            throw new MessagingException("Error in input stream", var15);
         }

         var4 = var14;
      } while(var6 > 0);

   }

   public void removeHeader(String var1) {
      for(int var2 = 0; var2 < this.headers.size(); ++var2) {
         InternetHeaders.InternetHeader var3 = (InternetHeaders.InternetHeader)this.headers.get(var2);
         if (var1.equalsIgnoreCase(var3.getName())) {
            var3.line = null;
         }
      }

   }

   public void setHeader(String var1, String var2) {
      int var3 = 0;

      boolean var4;
      boolean var7;
      for(var4 = false; var3 < this.headers.size(); var4 = var7) {
         InternetHeaders.InternetHeader var5 = (InternetHeaders.InternetHeader)this.headers.get(var3);
         int var6 = var3;
         var7 = var4;
         if (var1.equalsIgnoreCase(var5.getName())) {
            if (var4) {
               this.headers.remove(var3);
               var6 = var3 - 1;
               var7 = var4;
            } else {
               label29: {
                  StringBuilder var8;
                  if (var5.line != null) {
                     int var9 = var5.line.indexOf(58);
                     if (var9 >= 0) {
                        var8 = new StringBuilder(String.valueOf(var5.line.substring(0, var9 + 1)));
                        var8.append(" ");
                        var8.append(var2);
                        var5.line = var8.toString();
                        break label29;
                     }
                  }

                  var8 = new StringBuilder(String.valueOf(var1));
                  var8.append(": ");
                  var8.append(var2);
                  var5.line = var8.toString();
               }

               var7 = true;
               var6 = var3;
            }
         }

         var3 = var6 + 1;
      }

      if (!var4) {
         this.addHeader(var1, var2);
      }

   }

   protected static final class InternetHeader extends Header {
      String line;

      public InternetHeader(String var1) {
         super("", "");
         int var2 = var1.indexOf(58);
         if (var2 < 0) {
            this.name = var1.trim();
         } else {
            this.name = var1.substring(0, var2).trim();
         }

         this.line = var1;
      }

      public InternetHeader(String var1, String var2) {
         super(var1, "");
         if (var2 != null) {
            StringBuilder var3 = new StringBuilder(String.valueOf(var1));
            var3.append(": ");
            var3.append(var2);
            this.line = var3.toString();
         } else {
            this.line = null;
         }

      }

      public String getValue() {
         int var1 = this.line.indexOf(58);
         int var2 = var1;
         if (var1 < 0) {
            return this.line;
         } else {
            while(true) {
               var1 = var2 + 1;
               if (var1 >= this.line.length()) {
                  break;
               }

               char var3 = this.line.charAt(var1);
               var2 = var1;
               if (var3 != ' ') {
                  var2 = var1;
                  if (var3 != '\t') {
                     var2 = var1;
                     if (var3 != '\r') {
                        var2 = var1;
                        if (var3 != '\n') {
                           break;
                        }
                     }
                  }
               }
            }

            return this.line.substring(var1);
         }
      }
   }

   static class matchEnum implements Enumeration {
      private Iterator e;
      private boolean match;
      private String[] names;
      private InternetHeaders.InternetHeader next_header;
      private boolean want_line;

      matchEnum(List var1, String[] var2, boolean var3, boolean var4) {
         this.e = var1.iterator();
         this.names = var2;
         this.match = var3;
         this.want_line = var4;
         this.next_header = null;
      }

      private InternetHeaders.InternetHeader nextMatch() {
         while(true) {
            boolean var1 = this.e.hasNext();
            String[] var2 = null;
            if (!var1) {
               return null;
            }

            InternetHeaders.InternetHeader var3 = (InternetHeaders.InternetHeader)this.e.next();
            if (var3.line != null) {
               if (this.names == null) {
                  if (this.match) {
                     var3 = var2;
                  }

                  return var3;
               }

               int var4 = 0;

               while(true) {
                  var2 = this.names;
                  if (var4 >= var2.length) {
                     if (!this.match) {
                        return var3;
                     }
                     break;
                  }

                  if (var2[var4].equalsIgnoreCase(var3.getName())) {
                     if (!this.match) {
                        break;
                     }

                     return var3;
                  }

                  ++var4;
               }
            }
         }
      }

      public boolean hasMoreElements() {
         if (this.next_header == null) {
            this.next_header = this.nextMatch();
         }

         return this.next_header != null;
      }

      public Object nextElement() {
         if (this.next_header == null) {
            this.next_header = this.nextMatch();
         }

         InternetHeaders.InternetHeader var1 = this.next_header;
         if (var1 != null) {
            this.next_header = null;
            return this.want_line ? var1.line : new Header(var1.getName(), var1.getValue());
         } else {
            throw new NoSuchElementException("No more headers");
         }
      }
   }
}
