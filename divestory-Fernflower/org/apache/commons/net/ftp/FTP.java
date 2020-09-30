package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;

public class FTP extends SocketClient {
   public static final int ASCII_FILE_TYPE = 0;
   public static final int BINARY_FILE_TYPE = 2;
   public static final int BLOCK_TRANSFER_MODE = 11;
   public static final int CARRIAGE_CONTROL_TEXT_FORMAT = 6;
   public static final int COMPRESSED_TRANSFER_MODE = 12;
   public static final String DEFAULT_CONTROL_ENCODING = "ISO-8859-1";
   public static final int DEFAULT_DATA_PORT = 20;
   public static final int DEFAULT_PORT = 21;
   public static final int EBCDIC_FILE_TYPE = 1;
   public static final int FILE_STRUCTURE = 7;
   public static final int LOCAL_FILE_TYPE = 3;
   public static final int NON_PRINT_TEXT_FORMAT = 4;
   public static final int PAGE_STRUCTURE = 9;
   public static final int RECORD_STRUCTURE = 8;
   public static final int REPLY_CODE_LEN = 3;
   public static final int STREAM_TRANSFER_MODE = 10;
   public static final int TELNET_TEXT_FORMAT = 5;
   private static final String __modes = "AEILNTCFRPSBC";
   protected ProtocolCommandSupport _commandSupport_;
   protected String _controlEncoding;
   protected BufferedReader _controlInput_;
   protected BufferedWriter _controlOutput_;
   protected boolean _newReplyString;
   protected int _replyCode;
   protected ArrayList<String> _replyLines;
   protected String _replyString;
   protected boolean strictMultilineParsing = false;

   public FTP() {
      this.setDefaultPort(21);
      this._replyLines = new ArrayList();
      this._newReplyString = false;
      this._replyString = null;
      this._controlEncoding = "ISO-8859-1";
      this._commandSupport_ = new ProtocolCommandSupport(this);
   }

   private String __buildMessage(String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var1);
      if (var2 != null) {
         var3.append(' ');
         var3.append(var2);
      }

      var3.append("\r\n");
      return var3.toString();
   }

   private void __getReply() throws IOException {
      this.__getReply(true);
   }

   private void __getReply(boolean var1) throws IOException {
      this._newReplyString = true;
      this._replyLines.clear();
      String var2 = this._controlInput_.readLine();
      if (var2 == null) {
         throw new FTPConnectionClosedException("Connection closed without indication.");
      } else {
         int var3 = var2.length();
         StringBuilder var4;
         if (var3 >= 3) {
            String var6;
            try {
               var6 = var2.substring(0, 3);
               this._replyCode = Integer.parseInt(var6);
            } catch (NumberFormatException var5) {
               var4 = new StringBuilder();
               var4.append("Could not parse response code.\nServer Reply: ");
               var4.append(var2);
               throw new MalformedServerReplyException(var4.toString());
            }

            this._replyLines.add(var2);
            if (var3 > 3 && var2.charAt(3) == '-') {
               while(true) {
                  var2 = this._controlInput_.readLine();
                  if (var2 == null) {
                     throw new FTPConnectionClosedException("Connection closed without indication.");
                  }

                  this._replyLines.add(var2);
                  if (this.isStrictMultilineParsing()) {
                     if (!this.__strictCheck(var2, var6)) {
                        break;
                     }
                  } else if (!this.__lenientCheck(var2)) {
                     break;
                  }
               }
            }

            if (var1) {
               this.fireReplyReceived(this._replyCode, this.getReplyString());
            }

            if (this._replyCode == 421) {
               throw new FTPConnectionClosedException("FTP response 421 received.  Server closed connection.");
            }
         } else {
            var4 = new StringBuilder();
            var4.append("Truncated server reply: ");
            var4.append(var2);
            throw new MalformedServerReplyException(var4.toString());
         }
      }
   }

   private boolean __lenientCheck(String var1) {
      int var2 = var1.length();
      boolean var3 = false;
      if (var2 <= 3 || var1.charAt(3) == '-' || !Character.isDigit(var1.charAt(0))) {
         var3 = true;
      }

      return var3;
   }

   private void __send(String var1) throws IOException, FTPConnectionClosedException, SocketException {
      try {
         this._controlOutput_.write(var1);
         this._controlOutput_.flush();
      } catch (SocketException var2) {
         if (!this.isConnected()) {
            throw new FTPConnectionClosedException("Connection unexpectedly closed.");
         } else {
            throw var2;
         }
      }
   }

   private boolean __strictCheck(String var1, String var2) {
      boolean var3;
      if (var1.startsWith(var2) && var1.charAt(3) == ' ') {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   protected void __getReplyNoReport() throws IOException {
      this.__getReply(false);
   }

   protected void __noop() throws IOException {
      this.__send(this.__buildMessage(FTPCmd.NOOP.getCommand(), (String)null));
      this.__getReplyNoReport();
   }

   protected void _connectAction_() throws IOException {
      this._connectAction_((Reader)null);
   }

   protected void _connectAction_(Reader var1) throws IOException {
      super._connectAction_();
      if (var1 == null) {
         this._controlInput_ = new CRLFLineReader(new InputStreamReader(this._input_, this.getControlEncoding()));
      } else {
         this._controlInput_ = new CRLFLineReader(var1);
      }

      this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(this._output_, this.getControlEncoding()));
      if (this.connectTimeout > 0) {
         int var2 = this._socket_.getSoTimeout();
         this._socket_.setSoTimeout(this.connectTimeout);

         try {
            this.__getReply();
            if (FTPReply.isPositivePreliminary(this._replyCode)) {
               this.__getReply();
            }
         } catch (SocketTimeoutException var6) {
            IOException var8 = new IOException("Timed out waiting for initial connect reply");
            var8.initCause(var6);
            throw var8;
         } finally {
            this._socket_.setSoTimeout(var2);
         }
      } else {
         this.__getReply();
         if (FTPReply.isPositivePreliminary(this._replyCode)) {
            this.__getReply();
         }
      }

   }

   public int abor() throws IOException {
      return this.sendCommand(FTPCmd.ABOR);
   }

   public int acct(String var1) throws IOException {
      return this.sendCommand(FTPCmd.ACCT, var1);
   }

   public int allo(int var1) throws IOException {
      return this.sendCommand(FTPCmd.ALLO, Integer.toString(var1));
   }

   public int allo(int var1, int var2) throws IOException {
      FTPCmd var3 = FTPCmd.ALLO;
      StringBuilder var4 = new StringBuilder();
      var4.append(Integer.toString(var1));
      var4.append(" R ");
      var4.append(Integer.toString(var2));
      return this.sendCommand(var3, var4.toString());
   }

   public int appe(String var1) throws IOException {
      return this.sendCommand(FTPCmd.APPE, var1);
   }

   public int cdup() throws IOException {
      return this.sendCommand(FTPCmd.CDUP);
   }

   public int cwd(String var1) throws IOException {
      return this.sendCommand(FTPCmd.CWD, var1);
   }

   public int dele(String var1) throws IOException {
      return this.sendCommand(FTPCmd.DELE, var1);
   }

   public void disconnect() throws IOException {
      super.disconnect();
      this._controlInput_ = null;
      this._controlOutput_ = null;
      this._newReplyString = false;
      this._replyString = null;
   }

   public int eprt(InetAddress var1, int var2) throws IOException {
      StringBuilder var3 = new StringBuilder();
      String var4 = var1.getHostAddress();
      int var5 = var4.indexOf("%");
      String var6 = var4;
      if (var5 > 0) {
         var6 = var4.substring(0, var5);
      }

      var3.append("|");
      if (var1 instanceof Inet4Address) {
         var3.append("1");
      } else if (var1 instanceof Inet6Address) {
         var3.append("2");
      }

      var3.append("|");
      var3.append(var6);
      var3.append("|");
      var3.append(var2);
      var3.append("|");
      return this.sendCommand(FTPCmd.EPRT, var3.toString());
   }

   public int epsv() throws IOException {
      return this.sendCommand(FTPCmd.EPSV);
   }

   public int feat() throws IOException {
      return this.sendCommand(FTPCmd.FEAT);
   }

   protected ProtocolCommandSupport getCommandSupport() {
      return this._commandSupport_;
   }

   public String getControlEncoding() {
      return this._controlEncoding;
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
         StringBuilder var1 = new StringBuilder(256);
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

   public int help() throws IOException {
      return this.sendCommand(FTPCmd.HELP);
   }

   public int help(String var1) throws IOException {
      return this.sendCommand(FTPCmd.HELP, var1);
   }

   public boolean isStrictMultilineParsing() {
      return this.strictMultilineParsing;
   }

   public int list() throws IOException {
      return this.sendCommand(FTPCmd.LIST);
   }

   public int list(String var1) throws IOException {
      return this.sendCommand(FTPCmd.LIST, var1);
   }

   public int mdtm(String var1) throws IOException {
      return this.sendCommand(FTPCmd.MDTM, var1);
   }

   public int mfmt(String var1, String var2) throws IOException {
      FTPCmd var3 = FTPCmd.MFMT;
      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append(" ");
      var4.append(var1);
      return this.sendCommand(var3, var4.toString());
   }

   public int mkd(String var1) throws IOException {
      return this.sendCommand(FTPCmd.MKD, var1);
   }

   public int mlsd() throws IOException {
      return this.sendCommand(FTPCmd.MLSD);
   }

   public int mlsd(String var1) throws IOException {
      return this.sendCommand(FTPCmd.MLSD, var1);
   }

   public int mlst() throws IOException {
      return this.sendCommand(FTPCmd.MLST);
   }

   public int mlst(String var1) throws IOException {
      return this.sendCommand(FTPCmd.MLST, var1);
   }

   public int mode(int var1) throws IOException {
      return this.sendCommand(FTPCmd.MODE, "AEILNTCFRPSBC".substring(var1, var1 + 1));
   }

   public int nlst() throws IOException {
      return this.sendCommand(FTPCmd.NLST);
   }

   public int nlst(String var1) throws IOException {
      return this.sendCommand(FTPCmd.NLST, var1);
   }

   public int noop() throws IOException {
      return this.sendCommand(FTPCmd.NOOP);
   }

   public int pass(String var1) throws IOException {
      return this.sendCommand(FTPCmd.PASS, var1);
   }

   public int pasv() throws IOException {
      return this.sendCommand(FTPCmd.PASV);
   }

   public int port(InetAddress var1, int var2) throws IOException {
      StringBuilder var3 = new StringBuilder(24);
      var3.append(var1.getHostAddress().replace('.', ','));
      var3.append(',');
      var3.append(var2 >>> 8);
      var3.append(',');
      var3.append(var2 & 255);
      return this.sendCommand(FTPCmd.PORT, var3.toString());
   }

   public int pwd() throws IOException {
      return this.sendCommand(FTPCmd.PWD);
   }

   public int quit() throws IOException {
      return this.sendCommand(FTPCmd.QUIT);
   }

   public int rein() throws IOException {
      return this.sendCommand(FTPCmd.REIN);
   }

   public int rest(String var1) throws IOException {
      return this.sendCommand(FTPCmd.REST, var1);
   }

   public int retr(String var1) throws IOException {
      return this.sendCommand(FTPCmd.RETR, var1);
   }

   public int rmd(String var1) throws IOException {
      return this.sendCommand(FTPCmd.RMD, var1);
   }

   public int rnfr(String var1) throws IOException {
      return this.sendCommand(FTPCmd.RNFR, var1);
   }

   public int rnto(String var1) throws IOException {
      return this.sendCommand(FTPCmd.RNTO, var1);
   }

   public int sendCommand(int var1) throws IOException {
      return this.sendCommand(var1, (String)null);
   }

   @Deprecated
   public int sendCommand(int var1, String var2) throws IOException {
      return this.sendCommand(FTPCommand.getCommand(var1), var2);
   }

   public int sendCommand(String var1) throws IOException {
      return this.sendCommand((String)var1, (String)null);
   }

   public int sendCommand(String var1, String var2) throws IOException {
      if (this._controlOutput_ != null) {
         var2 = this.__buildMessage(var1, var2);
         this.__send(var2);
         this.fireCommandSent(var1, var2);
         this.__getReply();
         return this._replyCode;
      } else {
         throw new IOException("Connection is not open");
      }
   }

   public int sendCommand(FTPCmd var1) throws IOException {
      return this.sendCommand((FTPCmd)var1, (String)null);
   }

   public int sendCommand(FTPCmd var1, String var2) throws IOException {
      return this.sendCommand(var1.getCommand(), var2);
   }

   public void setControlEncoding(String var1) {
      this._controlEncoding = var1;
   }

   public void setStrictMultilineParsing(boolean var1) {
      this.strictMultilineParsing = var1;
   }

   public int site(String var1) throws IOException {
      return this.sendCommand(FTPCmd.SITE, var1);
   }

   public int smnt(String var1) throws IOException {
      return this.sendCommand(FTPCmd.SMNT, var1);
   }

   public int stat() throws IOException {
      return this.sendCommand(FTPCmd.STAT);
   }

   public int stat(String var1) throws IOException {
      return this.sendCommand(FTPCmd.STAT, var1);
   }

   public int stor(String var1) throws IOException {
      return this.sendCommand(FTPCmd.STOR, var1);
   }

   public int stou() throws IOException {
      return this.sendCommand(FTPCmd.STOU);
   }

   public int stou(String var1) throws IOException {
      return this.sendCommand(FTPCmd.STOU, var1);
   }

   public int stru(int var1) throws IOException {
      return this.sendCommand(FTPCmd.STRU, "AEILNTCFRPSBC".substring(var1, var1 + 1));
   }

   public int syst() throws IOException {
      return this.sendCommand(FTPCmd.SYST);
   }

   public int type(int var1) throws IOException {
      return this.sendCommand(FTPCmd.TYPE, "AEILNTCFRPSBC".substring(var1, var1 + 1));
   }

   public int type(int var1, int var2) throws IOException {
      StringBuilder var3 = new StringBuilder();
      var3.append("AEILNTCFRPSBC".charAt(var1));
      var3.append(' ');
      if (var1 == 3) {
         var3.append(var2);
      } else {
         var3.append("AEILNTCFRPSBC".charAt(var2));
      }

      return this.sendCommand(FTPCmd.TYPE, var3.toString());
   }

   public int user(String var1) throws IOException {
      return this.sendCommand(FTPCmd.USER, var1);
   }
}
