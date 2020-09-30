package org.apache.commons.net.nntp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;

public class NNTP extends SocketClient {
   public static final int DEFAULT_PORT = 119;
   private static final String __DEFAULT_ENCODING = "ISO-8859-1";
   protected ProtocolCommandSupport _commandSupport_;
   boolean _isAllowedToPost;
   protected BufferedReader _reader_;
   int _replyCode;
   String _replyString;
   protected BufferedWriter _writer_;

   public NNTP() {
      this.setDefaultPort(119);
      this._replyString = null;
      this._reader_ = null;
      this._writer_ = null;
      this._isAllowedToPost = false;
      this._commandSupport_ = new ProtocolCommandSupport(this);
   }

   private void __getReply() throws IOException {
      String var1 = this._reader_.readLine();
      this._replyString = var1;
      if (var1 != null) {
         StringBuilder var4;
         if (var1.length() >= 3) {
            int var2;
            try {
               var2 = Integer.parseInt(this._replyString.substring(0, 3));
               this._replyCode = var2;
            } catch (NumberFormatException var3) {
               var4 = new StringBuilder();
               var4.append("Could not parse response code.\nServer Reply: ");
               var4.append(this._replyString);
               throw new MalformedServerReplyException(var4.toString());
            }

            var4 = new StringBuilder();
            var4.append(this._replyString);
            var4.append("\r\n");
            this.fireReplyReceived(var2, var4.toString());
            if (this._replyCode == 400) {
               throw new NNTPConnectionClosedException("NNTP response 400 received.  Server closed connection.");
            }
         } else {
            var4 = new StringBuilder();
            var4.append("Truncated server reply: ");
            var4.append(this._replyString);
            throw new MalformedServerReplyException(var4.toString());
         }
      } else {
         throw new NNTPConnectionClosedException("Connection closed without indication.");
      }
   }

   protected void _connectAction_() throws IOException {
      super._connectAction_();
      this._reader_ = new CRLFLineReader(new InputStreamReader(this._input_, "ISO-8859-1"));
      this._writer_ = new BufferedWriter(new OutputStreamWriter(this._output_, "ISO-8859-1"));
      this.__getReply();
      boolean var1;
      if (this._replyCode == 200) {
         var1 = true;
      } else {
         var1 = false;
      }

      this._isAllowedToPost = var1;
   }

   public int article() throws IOException {
      return this.sendCommand(0);
   }

   @Deprecated
   public int article(int var1) throws IOException {
      return this.article((long)var1);
   }

   public int article(long var1) throws IOException {
      return this.sendCommand(0, Long.toString(var1));
   }

   public int article(String var1) throws IOException {
      return this.sendCommand(0, var1);
   }

   public int authinfoPass(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      var2.append("PASS ");
      var2.append(var1);
      return this.sendCommand(15, var2.toString());
   }

   public int authinfoUser(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      var2.append("USER ");
      var2.append(var1);
      return this.sendCommand(15, var2.toString());
   }

   public int body() throws IOException {
      return this.sendCommand(1);
   }

   @Deprecated
   public int body(int var1) throws IOException {
      return this.body((long)var1);
   }

   public int body(long var1) throws IOException {
      return this.sendCommand(1, Long.toString(var1));
   }

   public int body(String var1) throws IOException {
      return this.sendCommand(1, var1);
   }

   public void disconnect() throws IOException {
      super.disconnect();
      this._reader_ = null;
      this._writer_ = null;
      this._replyString = null;
      this._isAllowedToPost = false;
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
      return this._replyString;
   }

   public int group(String var1) throws IOException {
      return this.sendCommand(2, var1);
   }

   public int head() throws IOException {
      return this.sendCommand(3);
   }

   @Deprecated
   public int head(int var1) throws IOException {
      return this.head((long)var1);
   }

   public int head(long var1) throws IOException {
      return this.sendCommand(3, Long.toString(var1));
   }

   public int head(String var1) throws IOException {
      return this.sendCommand(3, var1);
   }

   public int help() throws IOException {
      return this.sendCommand(4);
   }

   public int ihave(String var1) throws IOException {
      return this.sendCommand(5, var1);
   }

   public boolean isAllowedToPost() {
      return this._isAllowedToPost;
   }

   public int last() throws IOException {
      return this.sendCommand(6);
   }

   public int list() throws IOException {
      return this.sendCommand(7);
   }

   public int listActive(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder("ACTIVE ");
      var2.append(var1);
      return this.sendCommand(7, var2.toString());
   }

   public int newgroups(String var1, String var2, boolean var3, String var4) throws IOException {
      StringBuilder var5 = new StringBuilder();
      var5.append(var1);
      var5.append(' ');
      var5.append(var2);
      if (var3) {
         var5.append(' ');
         var5.append("GMT");
      }

      if (var4 != null) {
         var5.append(" <");
         var5.append(var4);
         var5.append('>');
      }

      return this.sendCommand(8, var5.toString());
   }

   public int newnews(String var1, String var2, String var3, boolean var4, String var5) throws IOException {
      StringBuilder var6 = new StringBuilder();
      var6.append(var1);
      var6.append(' ');
      var6.append(var2);
      var6.append(' ');
      var6.append(var3);
      if (var4) {
         var6.append(' ');
         var6.append("GMT");
      }

      if (var5 != null) {
         var6.append(" <");
         var6.append(var5);
         var6.append('>');
      }

      return this.sendCommand(9, var6.toString());
   }

   public int next() throws IOException {
      return this.sendCommand(10);
   }

   public int post() throws IOException {
      return this.sendCommand(11);
   }

   public int quit() throws IOException {
      return this.sendCommand(12);
   }

   public int sendCommand(int var1) throws IOException {
      return this.sendCommand(var1, (String)null);
   }

   public int sendCommand(int var1, String var2) throws IOException {
      return this.sendCommand(NNTPCommand.getCommand(var1), var2);
   }

   public int sendCommand(String var1) throws IOException {
      return this.sendCommand(var1, (String)null);
   }

   public int sendCommand(String var1, String var2) throws IOException {
      StringBuilder var3 = new StringBuilder();
      var3.append(var1);
      if (var2 != null) {
         var3.append(' ');
         var3.append(var2);
      }

      var3.append("\r\n");
      BufferedWriter var4 = this._writer_;
      String var5 = var3.toString();
      var4.write(var5);
      this._writer_.flush();
      this.fireCommandSent(var1, var5);
      this.__getReply();
      return this._replyCode;
   }

   public int stat() throws IOException {
      return this.sendCommand(14);
   }

   @Deprecated
   public int stat(int var1) throws IOException {
      return this.stat((long)var1);
   }

   public int stat(long var1) throws IOException {
      return this.sendCommand(14, Long.toString(var1));
   }

   public int stat(String var1) throws IOException {
      return this.sendCommand(14, var1);
   }

   public int xhdr(String var1, String var2) throws IOException {
      StringBuilder var3 = new StringBuilder(var1);
      var3.append(" ");
      var3.append(var2);
      return this.sendCommand(17, var3.toString());
   }

   public int xover(String var1) throws IOException {
      return this.sendCommand(16, var1);
   }
}
