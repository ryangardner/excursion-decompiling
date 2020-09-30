package org.apache.commons.net.smtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;

public class SMTP extends SocketClient {
   public static final int DEFAULT_PORT = 25;
   private static final String __DEFAULT_ENCODING = "ISO-8859-1";
   protected ProtocolCommandSupport _commandSupport_;
   private boolean _newReplyString;
   BufferedReader _reader;
   private int _replyCode;
   private final ArrayList<String> _replyLines;
   private String _replyString;
   BufferedWriter _writer;
   protected final String encoding;

   public SMTP() {
      this("ISO-8859-1");
   }

   public SMTP(String var1) {
      this.setDefaultPort(25);
      this._replyLines = new ArrayList();
      this._newReplyString = false;
      this._replyString = null;
      this._commandSupport_ = new ProtocolCommandSupport(this);
      this.encoding = var1;
   }

   private void __getReply() throws IOException {
      this._newReplyString = true;
      this._replyLines.clear();
      String var1 = this._reader.readLine();
      if (var1 == null) {
         throw new SMTPConnectionClosedException("Connection closed without indication.");
      } else {
         int var2 = var1.length();
         StringBuilder var3;
         if (var2 >= 3) {
            try {
               this._replyCode = Integer.parseInt(var1.substring(0, 3));
            } catch (NumberFormatException var4) {
               var3 = new StringBuilder();
               var3.append("Could not parse response code.\nServer Reply: ");
               var3.append(var1);
               throw new MalformedServerReplyException(var3.toString());
            }

            this._replyLines.add(var1);
            if (var2 > 3 && var1.charAt(3) == '-') {
               do {
                  do {
                     do {
                        var1 = this._reader.readLine();
                        if (var1 == null) {
                           throw new SMTPConnectionClosedException("Connection closed without indication.");
                        }

                        this._replyLines.add(var1);
                     } while(var1.length() < 4);
                  } while(var1.charAt(3) == '-');
               } while(!Character.isDigit(var1.charAt(0)));
            }

            this.fireReplyReceived(this._replyCode, this.getReplyString());
            if (this._replyCode == 421) {
               throw new SMTPConnectionClosedException("SMTP response 421 received.  Server closed connection.");
            }
         } else {
            var3 = new StringBuilder();
            var3.append("Truncated server reply: ");
            var3.append(var1);
            throw new MalformedServerReplyException(var3.toString());
         }
      }
   }

   private int __sendCommand(int var1, String var2, boolean var3) throws IOException {
      return this.__sendCommand(SMTPCommand.getCommand(var1), var2, var3);
   }

   private int __sendCommand(String var1, String var2, boolean var3) throws IOException {
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      if (var2 != null) {
         if (var3) {
            var4.append(' ');
         }

         var4.append(var2);
      }

      var4.append("\r\n");
      BufferedWriter var5 = this._writer;
      String var6 = var4.toString();
      var5.write(var6);
      this._writer.flush();
      this.fireCommandSent(var1, var6);
      this.__getReply();
      return this._replyCode;
   }

   protected void _connectAction_() throws IOException {
      super._connectAction_();
      this._reader = new CRLFLineReader(new InputStreamReader(this._input_, this.encoding));
      this._writer = new BufferedWriter(new OutputStreamWriter(this._output_, this.encoding));
      this.__getReply();
   }

   public int data() throws IOException {
      return this.sendCommand(3);
   }

   public void disconnect() throws IOException {
      super.disconnect();
      this._reader = null;
      this._writer = null;
      this._replyString = null;
      this._replyLines.clear();
      this._newReplyString = false;
   }

   public int expn(String var1) throws IOException {
      return this.sendCommand(9, var1);
   }

   protected ProtocolCommandSupport getCommandSupport() {
      return this._commandSupport_;
   }

   public int getReply() throws IOException {
      this.__getReply();
      return this._replyCode;
   }

   public int getReplyCode() {
      return this._replyCode;
   }

   public String getReplyString() {
      if (!this._newReplyString) {
         return this._replyString;
      } else {
         StringBuilder var1 = new StringBuilder();
         Iterator var2 = this._replyLines.iterator();

         while(var2.hasNext()) {
            var1.append((String)var2.next());
            var1.append("\r\n");
         }

         this._newReplyString = false;
         String var3 = var1.toString();
         this._replyString = var3;
         return var3;
      }
   }

   public String[] getReplyStrings() {
      ArrayList var1 = this._replyLines;
      return (String[])var1.toArray(new String[var1.size()]);
   }

   public int helo(String var1) throws IOException {
      return this.sendCommand(0, var1);
   }

   public int help() throws IOException {
      return this.sendCommand(10);
   }

   public int help(String var1) throws IOException {
      return this.sendCommand(10, var1);
   }

   public int mail(String var1) throws IOException {
      return this.__sendCommand(1, var1, false);
   }

   public int noop() throws IOException {
      return this.sendCommand(11);
   }

   public int quit() throws IOException {
      return this.sendCommand(13);
   }

   public int rcpt(String var1) throws IOException {
      return this.__sendCommand(2, var1, false);
   }

   public void removeProtocolCommandistener(ProtocolCommandListener var1) {
      this.removeProtocolCommandListener(var1);
   }

   public int rset() throws IOException {
      return this.sendCommand(7);
   }

   public int saml(String var1) throws IOException {
      return this.sendCommand(6, var1);
   }

   public int send(String var1) throws IOException {
      return this.sendCommand(4, var1);
   }

   public int sendCommand(int var1) throws IOException {
      return this.sendCommand(var1, (String)null);
   }

   public int sendCommand(int var1, String var2) throws IOException {
      return this.sendCommand(SMTPCommand.getCommand(var1), var2);
   }

   public int sendCommand(String var1) throws IOException {
      return this.sendCommand(var1, (String)null);
   }

   public int sendCommand(String var1, String var2) throws IOException {
      return this.__sendCommand(var1, var2, true);
   }

   public int soml(String var1) throws IOException {
      return this.sendCommand(5, var1);
   }

   public int turn() throws IOException {
      return this.sendCommand(12);
   }

   public int vrfy(String var1) throws IOException {
      return this.sendCommand(8, var1);
   }
}
