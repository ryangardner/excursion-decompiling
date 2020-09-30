package org.apache.commons.net.pop3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;

public class POP3 extends SocketClient {
   public static final int AUTHORIZATION_STATE = 0;
   public static final int DEFAULT_PORT = 110;
   public static final int DISCONNECTED_STATE = -1;
   public static final int TRANSACTION_STATE = 1;
   public static final int UPDATE_STATE = 2;
   static final String _DEFAULT_ENCODING = "ISO-8859-1";
   static final String _ERROR = "-ERR";
   static final String _OK = "+OK";
   static final String _OK_INT = "+ ";
   private int __popState;
   protected ProtocolCommandSupport _commandSupport_;
   String _lastReplyLine;
   BufferedReader _reader;
   int _replyCode;
   List<String> _replyLines;
   BufferedWriter _writer;

   public POP3() {
      this.setDefaultPort(110);
      this.__popState = -1;
      this._reader = null;
      this._writer = null;
      this._replyLines = new ArrayList();
      this._commandSupport_ = new ProtocolCommandSupport(this);
   }

   private void __getReply() throws IOException {
      this._replyLines.clear();
      String var1 = this._reader.readLine();
      if (var1 != null) {
         if (var1.startsWith("+OK")) {
            this._replyCode = 0;
         } else if (var1.startsWith("-ERR")) {
            this._replyCode = 1;
         } else {
            if (!var1.startsWith("+ ")) {
               StringBuilder var2 = new StringBuilder();
               var2.append("Received invalid POP3 protocol response from server.");
               var2.append(var1);
               throw new MalformedServerReplyException(var2.toString());
            }

            this._replyCode = 2;
         }

         this._replyLines.add(var1);
         this._lastReplyLine = var1;
         this.fireReplyReceived(this._replyCode, this.getReplyString());
      } else {
         throw new EOFException("Connection closed without indication.");
      }
   }

   protected void _connectAction_() throws IOException {
      super._connectAction_();
      this._reader = new CRLFLineReader(new InputStreamReader(this._input_, "ISO-8859-1"));
      this._writer = new BufferedWriter(new OutputStreamWriter(this._output_, "ISO-8859-1"));
      this.__getReply();
      this.setState(0);
   }

   public void disconnect() throws IOException {
      super.disconnect();
      this._reader = null;
      this._writer = null;
      this._lastReplyLine = null;
      this._replyLines.clear();
      this.setState(-1);
   }

   public void getAdditionalReply() throws IOException {
      for(String var1 = this._reader.readLine(); var1 != null; var1 = this._reader.readLine()) {
         this._replyLines.add(var1);
         if (var1.equals(".")) {
            break;
         }
      }

   }

   protected ProtocolCommandSupport getCommandSupport() {
      return this._commandSupport_;
   }

   public String getReplyString() {
      StringBuilder var1 = new StringBuilder(256);
      Iterator var2 = this._replyLines.iterator();

      while(var2.hasNext()) {
         var1.append((String)var2.next());
         var1.append("\r\n");
      }

      return var1.toString();
   }

   public String[] getReplyStrings() {
      List var1 = this._replyLines;
      return (String[])var1.toArray(new String[var1.size()]);
   }

   public int getState() {
      return this.__popState;
   }

   public void removeProtocolCommandistener(ProtocolCommandListener var1) {
      this.removeProtocolCommandListener(var1);
   }

   public int sendCommand(int var1) throws IOException {
      return this.sendCommand(POP3Command._commands[var1], (String)null);
   }

   public int sendCommand(int var1, String var2) throws IOException {
      return this.sendCommand(POP3Command._commands[var1], var2);
   }

   public int sendCommand(String var1) throws IOException {
      return this.sendCommand(var1, (String)null);
   }

   public int sendCommand(String var1, String var2) throws IOException {
      if (this._writer != null) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1);
         if (var2 != null) {
            var3.append(' ');
            var3.append(var2);
         }

         var3.append("\r\n");
         var2 = var3.toString();
         this._writer.write(var2);
         this._writer.flush();
         this.fireCommandSent(var1, var2);
         this.__getReply();
         return this._replyCode;
      } else {
         throw new IllegalStateException("Socket is not connected");
      }
   }

   public void setState(int var1) {
      this.__popState = var1;
   }
}
