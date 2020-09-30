package org.apache.commons.net.nntp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.io.DotTerminatedMessageReader;
import org.apache.commons.net.io.DotTerminatedMessageWriter;
import org.apache.commons.net.io.Util;

public class NNTPClient extends NNTP {
   private void __ai2ap(ArticleInfo var1, ArticlePointer var2) {
      if (var2 != null) {
         var2.articleId = var1.articleId;
         var2.articleNumber = (int)var1.articleNumber;
      }

   }

   private ArticleInfo __ap2ai(ArticlePointer var1) {
      return var1 == null ? null : new ArticleInfo();
   }

   static Article __parseArticleEntry(String var0) {
      Article var1 = new Article();
      var1.setSubject(var0);
      String[] var3 = var0.split("\t");
      if (var3.length > 6) {
         try {
            var1.setArticleNumber(Long.parseLong(var3[0]));
            var1.setSubject(var3[1]);
            var1.setFrom(var3[2]);
            var1.setDate(var3[3]);
            var1.setArticleId(var3[4]);
            var1.addReference(var3[5]);
         } catch (NumberFormatException var2) {
         }
      }

      return var1;
   }

   private void __parseArticlePointer(String var1, ArticleInfo var2) throws MalformedServerReplyException {
      String[] var3 = var1.split(" ");
      if (var3.length >= 3) {
         try {
            var2.articleNumber = Long.parseLong(var3[1]);
            var2.articleId = var3[2];
            return;
         } catch (NumberFormatException var4) {
         }
      }

      StringBuilder var5 = new StringBuilder();
      var5.append("Could not parse article pointer.\nServer reply: ");
      var5.append(var1);
      throw new MalformedServerReplyException(var5.toString());
   }

   private static void __parseGroupReply(String var0, NewsgroupInfo var1) throws MalformedServerReplyException {
      String[] var2 = var0.split(" ");
      if (var2.length >= 5) {
         try {
            var1._setArticleCount(Long.parseLong(var2[1]));
            var1._setFirstArticle(Long.parseLong(var2[2]));
            var1._setLastArticle(Long.parseLong(var2[3]));
            var1._setNewsgroup(var2[4]);
            var1._setPostingPermission(0);
            return;
         } catch (NumberFormatException var3) {
         }
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("Could not parse newsgroup info.\nServer reply: ");
      var4.append(var0);
      throw new MalformedServerReplyException(var4.toString());
   }

   static NewsgroupInfo __parseNewsgroupListEntry(String var0) {
      String[] var1 = var0.split(" ");
      if (var1.length < 4) {
         return null;
      } else {
         NewsgroupInfo var10 = new NewsgroupInfo();
         var10._setNewsgroup(var1[0]);

         label84: {
            boolean var10001;
            long var2;
            long var4;
            try {
               var2 = Long.parseLong(var1[1]);
               var4 = Long.parseLong(var1[2]);
               var10._setFirstArticle(var4);
               var10._setLastArticle(var2);
            } catch (NumberFormatException var9) {
               var10001 = false;
               return null;
            }

            if (var4 == 0L && var2 == 0L) {
               try {
                  var10._setArticleCount(0L);
                  break label84;
               } catch (NumberFormatException var7) {
                  var10001 = false;
               }
            } else {
               try {
                  var10._setArticleCount(var2 - var4 + 1L);
                  break label84;
               } catch (NumberFormatException var8) {
                  var10001 = false;
               }
            }

            return null;
         }

         label85: {
            char var6 = var1[3].charAt(0);
            if (var6 != 'M') {
               if (var6 == 'N') {
                  break label85;
               }

               if (var6 == 'Y' || var6 == 'y') {
                  var10._setPostingPermission(2);
                  return var10;
               }

               if (var6 != 'm') {
                  if (var6 != 'n') {
                     var10._setPostingPermission(0);
                     return var10;
                  }
                  break label85;
               }
            }

            var10._setPostingPermission(1);
            return var10;
         }

         var10._setPostingPermission(3);
         return var10;
      }
   }

   private NewsgroupInfo[] __readNewsgroupListing() throws IOException {
      DotTerminatedMessageReader var1 = new DotTerminatedMessageReader(this._reader_);
      Vector var2 = new Vector(2048);

      Throwable var10000;
      while(true) {
         String var3;
         boolean var10001;
         try {
            var3 = var1.readLine();
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            break;
         }

         if (var3 == null) {
            var1.close();
            int var5 = var2.size();
            if (var5 < 1) {
               return new NewsgroupInfo[0];
            }

            NewsgroupInfo[] var26 = new NewsgroupInfo[var5];
            var2.copyInto(var26);
            return var26;
         }

         NewsgroupInfo var4;
         try {
            var4 = __parseNewsgroupListEntry(var3);
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break;
         }

         if (var4 != null) {
            try {
               var2.addElement(var4);
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break;
            }
         } else {
            try {
               MalformedServerReplyException var28 = new MalformedServerReplyException(var3);
               throw var28;
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break;
            }
         }
      }

      Throwable var27 = var10000;
      var1.close();
      throw var27;
   }

   private BufferedReader __retrieve(int var1, long var2, ArticleInfo var4) throws IOException {
      if (!NNTPReply.isPositiveCompletion(this.sendCommand(var1, Long.toString(var2)))) {
         return null;
      } else {
         if (var4 != null) {
            this.__parseArticlePointer(this.getReplyString(), var4);
         }

         return new DotTerminatedMessageReader(this._reader_);
      }
   }

   private BufferedReader __retrieve(int var1, String var2, ArticleInfo var3) throws IOException {
      if (var2 != null) {
         if (!NNTPReply.isPositiveCompletion(this.sendCommand(var1, var2))) {
            return null;
         }
      } else if (!NNTPReply.isPositiveCompletion(this.sendCommand(var1))) {
         return null;
      }

      if (var3 != null) {
         this.__parseArticlePointer(this.getReplyString(), var3);
      }

      return new DotTerminatedMessageReader(this._reader_);
   }

   private BufferedReader __retrieveArticleInfo(String var1) throws IOException {
      return !NNTPReply.isPositiveCompletion(this.xover(var1)) ? null : new DotTerminatedMessageReader(this._reader_);
   }

   private BufferedReader __retrieveHeader(String var1, String var2) throws IOException {
      return !NNTPReply.isPositiveCompletion(this.xhdr(var1, var2)) ? null : new DotTerminatedMessageReader(this._reader_);
   }

   public boolean authenticate(String var1, String var2) throws IOException {
      if (this.authinfoUser(var1) == 381 && this.authinfoPass(var2) == 281) {
         this._isAllowedToPost = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean completePendingCommand() throws IOException {
      return NNTPReply.isPositiveCompletion(this.getReply());
   }

   public Writer forwardArticle(String var1) throws IOException {
      return !NNTPReply.isPositiveIntermediate(this.ihave(var1)) ? null : new DotTerminatedMessageWriter(this._writer_);
   }

   public Iterable<Article> iterateArticleInfo(long var1, long var3) throws IOException {
      BufferedReader var5 = this.retrieveArticleInfo(var1, var3);
      if (var5 != null) {
         return new ArticleIterator(new ReplyIterator(var5, false));
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("XOVER command failed: ");
         var6.append(this.getReplyString());
         throw new IOException(var6.toString());
      }
   }

   public Iterable<String> iterateNewNews(NewGroupsOrNewsQuery var1) throws IOException {
      if (NNTPReply.isPositiveCompletion(this.newnews(var1.getNewsgroups(), var1.getDate(), var1.getTime(), var1.isGMT(), var1.getDistributions()))) {
         return new ReplyIterator(this._reader_);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("NEWNEWS command failed: ");
         var2.append(this.getReplyString());
         throw new IOException(var2.toString());
      }
   }

   public Iterable<String> iterateNewNewsgroupListing(NewGroupsOrNewsQuery var1) throws IOException {
      if (NNTPReply.isPositiveCompletion(this.newgroups(var1.getDate(), var1.getTime(), var1.isGMT(), var1.getDistributions()))) {
         return new ReplyIterator(this._reader_);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("NEWGROUPS command failed: ");
         var2.append(this.getReplyString());
         throw new IOException(var2.toString());
      }
   }

   public Iterable<NewsgroupInfo> iterateNewNewsgroups(NewGroupsOrNewsQuery var1) throws IOException {
      return new NewsgroupIterator(this.iterateNewNewsgroupListing(var1));
   }

   public Iterable<String> iterateNewsgroupListing() throws IOException {
      if (NNTPReply.isPositiveCompletion(this.list())) {
         return new ReplyIterator(this._reader_);
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("LIST command failed: ");
         var1.append(this.getReplyString());
         throw new IOException(var1.toString());
      }
   }

   public Iterable<String> iterateNewsgroupListing(String var1) throws IOException {
      if (NNTPReply.isPositiveCompletion(this.listActive(var1))) {
         return new ReplyIterator(this._reader_);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("LIST ACTIVE ");
         var2.append(var1);
         var2.append(" command failed: ");
         var2.append(this.getReplyString());
         throw new IOException(var2.toString());
      }
   }

   public Iterable<NewsgroupInfo> iterateNewsgroups() throws IOException {
      return new NewsgroupIterator(this.iterateNewsgroupListing());
   }

   public Iterable<NewsgroupInfo> iterateNewsgroups(String var1) throws IOException {
      return new NewsgroupIterator(this.iterateNewsgroupListing(var1));
   }

   public String listHelp() throws IOException {
      if (!NNTPReply.isInformational(this.help())) {
         return null;
      } else {
         StringWriter var1 = new StringWriter();
         DotTerminatedMessageReader var2 = new DotTerminatedMessageReader(this._reader_);
         Util.copyReader(var2, var1);
         var2.close();
         var1.close();
         return var1.toString();
      }
   }

   public String[] listNewNews(NewGroupsOrNewsQuery var1) throws IOException {
      if (!NNTPReply.isPositiveCompletion(this.newnews(var1.getNewsgroups(), var1.getDate(), var1.getTime(), var1.isGMT(), var1.getDistributions()))) {
         return null;
      } else {
         Vector var11 = new Vector();
         DotTerminatedMessageReader var2 = new DotTerminatedMessageReader(this._reader_);

         while(true) {
            Throwable var10000;
            label111: {
               boolean var10001;
               String var3;
               try {
                  var3 = var2.readLine();
               } catch (Throwable var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label111;
               }

               if (var3 == null) {
                  var2.close();
                  int var4 = var11.size();
                  if (var4 < 1) {
                     return new String[0];
                  } else {
                     String[] var13 = new String[var4];
                     var11.copyInto(var13);
                     return var13;
                  }
               }

               label100:
               try {
                  var11.addElement(var3);
                  continue;
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label100;
               }
            }

            Throwable var12 = var10000;
            var2.close();
            throw var12;
         }
      }
   }

   public NewsgroupInfo[] listNewNewsgroups(NewGroupsOrNewsQuery var1) throws IOException {
      return !NNTPReply.isPositiveCompletion(this.newgroups(var1.getDate(), var1.getTime(), var1.isGMT(), var1.getDistributions())) ? null : this.__readNewsgroupListing();
   }

   public NewsgroupInfo[] listNewsgroups() throws IOException {
      return !NNTPReply.isPositiveCompletion(this.list()) ? null : this.__readNewsgroupListing();
   }

   public NewsgroupInfo[] listNewsgroups(String var1) throws IOException {
      return !NNTPReply.isPositiveCompletion(this.listActive(var1)) ? null : this.__readNewsgroupListing();
   }

   public String[] listOverviewFmt() throws IOException {
      if (!NNTPReply.isPositiveCompletion(this.sendCommand("LIST", "OVERVIEW.FMT"))) {
         return null;
      } else {
         DotTerminatedMessageReader var1 = new DotTerminatedMessageReader(this._reader_);
         ArrayList var2 = new ArrayList();

         while(true) {
            String var3 = var1.readLine();
            if (var3 == null) {
               var1.close();
               return (String[])var2.toArray(new String[var2.size()]);
            }

            var2.add(var3);
         }
      }
   }

   public boolean logout() throws IOException {
      return NNTPReply.isPositiveCompletion(this.quit());
   }

   public Writer postArticle() throws IOException {
      return !NNTPReply.isPositiveIntermediate(this.post()) ? null : new DotTerminatedMessageWriter(this._writer_);
   }

   public BufferedReader retrieveArticle(long var1) throws IOException {
      return this.retrieveArticle(var1, (ArticleInfo)null);
   }

   public BufferedReader retrieveArticle(long var1, ArticleInfo var3) throws IOException {
      return this.__retrieve(0, var1, var3);
   }

   public BufferedReader retrieveArticle(String var1, ArticleInfo var2) throws IOException {
      return this.__retrieve(0, var1, var2);
   }

   public Reader retrieveArticle() throws IOException {
      return this.retrieveArticle((String)null);
   }

   @Deprecated
   public Reader retrieveArticle(int var1) throws IOException {
      return this.retrieveArticle((long)var1);
   }

   @Deprecated
   public Reader retrieveArticle(int var1, ArticlePointer var2) throws IOException {
      ArticleInfo var3 = this.__ap2ai(var2);
      BufferedReader var4 = this.retrieveArticle((long)var1, var3);
      this.__ai2ap(var3, var2);
      return var4;
   }

   public Reader retrieveArticle(String var1) throws IOException {
      return this.retrieveArticle(var1, (ArticleInfo)null);
   }

   @Deprecated
   public Reader retrieveArticle(String var1, ArticlePointer var2) throws IOException {
      ArticleInfo var3 = this.__ap2ai(var2);
      BufferedReader var4 = this.retrieveArticle(var1, var3);
      this.__ai2ap(var3, var2);
      return var4;
   }

   public BufferedReader retrieveArticleBody(long var1) throws IOException {
      return this.retrieveArticleBody(var1, (ArticleInfo)null);
   }

   public BufferedReader retrieveArticleBody(long var1, ArticleInfo var3) throws IOException {
      return this.__retrieve(1, var1, var3);
   }

   public BufferedReader retrieveArticleBody(String var1, ArticleInfo var2) throws IOException {
      return this.__retrieve(1, var1, var2);
   }

   public Reader retrieveArticleBody() throws IOException {
      return this.retrieveArticleBody((String)null);
   }

   @Deprecated
   public Reader retrieveArticleBody(int var1) throws IOException {
      return this.retrieveArticleBody((long)var1);
   }

   @Deprecated
   public Reader retrieveArticleBody(int var1, ArticlePointer var2) throws IOException {
      ArticleInfo var3 = this.__ap2ai(var2);
      BufferedReader var4 = this.retrieveArticleBody((long)var1, var3);
      this.__ai2ap(var3, var2);
      return var4;
   }

   public Reader retrieveArticleBody(String var1) throws IOException {
      return this.retrieveArticleBody(var1, (ArticleInfo)null);
   }

   @Deprecated
   public Reader retrieveArticleBody(String var1, ArticlePointer var2) throws IOException {
      ArticleInfo var3 = this.__ap2ai(var2);
      BufferedReader var4 = this.retrieveArticleBody(var1, var3);
      this.__ai2ap(var3, var2);
      return var4;
   }

   public BufferedReader retrieveArticleHeader(long var1) throws IOException {
      return this.retrieveArticleHeader(var1, (ArticleInfo)null);
   }

   public BufferedReader retrieveArticleHeader(long var1, ArticleInfo var3) throws IOException {
      return this.__retrieve(3, var1, var3);
   }

   public BufferedReader retrieveArticleHeader(String var1, ArticleInfo var2) throws IOException {
      return this.__retrieve(3, var1, var2);
   }

   public Reader retrieveArticleHeader() throws IOException {
      return this.retrieveArticleHeader((String)null);
   }

   @Deprecated
   public Reader retrieveArticleHeader(int var1) throws IOException {
      return this.retrieveArticleHeader((long)var1);
   }

   @Deprecated
   public Reader retrieveArticleHeader(int var1, ArticlePointer var2) throws IOException {
      ArticleInfo var3 = this.__ap2ai(var2);
      BufferedReader var4 = this.retrieveArticleHeader((long)var1, var3);
      this.__ai2ap(var3, var2);
      return var4;
   }

   public Reader retrieveArticleHeader(String var1) throws IOException {
      return this.retrieveArticleHeader(var1, (ArticleInfo)null);
   }

   @Deprecated
   public Reader retrieveArticleHeader(String var1, ArticlePointer var2) throws IOException {
      ArticleInfo var3 = this.__ap2ai(var2);
      BufferedReader var4 = this.retrieveArticleHeader(var1, var3);
      this.__ai2ap(var3, var2);
      return var4;
   }

   public BufferedReader retrieveArticleInfo(long var1) throws IOException {
      return this.__retrieveArticleInfo(Long.toString(var1));
   }

   public BufferedReader retrieveArticleInfo(long var1, long var3) throws IOException {
      StringBuilder var5 = new StringBuilder();
      var5.append(var1);
      var5.append("-");
      var5.append(var3);
      return this.__retrieveArticleInfo(var5.toString());
   }

   @Deprecated
   public Reader retrieveArticleInfo(int var1) throws IOException {
      return this.retrieveArticleInfo((long)var1);
   }

   @Deprecated
   public Reader retrieveArticleInfo(int var1, int var2) throws IOException {
      return this.retrieveArticleInfo((long)var1, (long)var2);
   }

   public BufferedReader retrieveHeader(String var1, long var2) throws IOException {
      return this.__retrieveHeader(var1, Long.toString(var2));
   }

   public BufferedReader retrieveHeader(String var1, long var2, long var4) throws IOException {
      StringBuilder var6 = new StringBuilder();
      var6.append(var2);
      var6.append("-");
      var6.append(var4);
      return this.__retrieveHeader(var1, var6.toString());
   }

   @Deprecated
   public Reader retrieveHeader(String var1, int var2) throws IOException {
      return this.retrieveHeader(var1, (long)var2);
   }

   @Deprecated
   public Reader retrieveHeader(String var1, int var2, int var3) throws IOException {
      return this.retrieveHeader(var1, (long)var2, (long)var3);
   }

   @Deprecated
   public boolean selectArticle(int var1) throws IOException {
      return this.selectArticle((long)var1);
   }

   @Deprecated
   public boolean selectArticle(int var1, ArticlePointer var2) throws IOException {
      ArticleInfo var3 = this.__ap2ai(var2);
      boolean var4 = this.selectArticle((long)var1, var3);
      this.__ai2ap(var3, var2);
      return var4;
   }

   public boolean selectArticle(long var1) throws IOException {
      return this.selectArticle(var1, (ArticleInfo)null);
   }

   public boolean selectArticle(long var1, ArticleInfo var3) throws IOException {
      if (!NNTPReply.isPositiveCompletion(this.stat(var1))) {
         return false;
      } else {
         if (var3 != null) {
            this.__parseArticlePointer(this.getReplyString(), var3);
         }

         return true;
      }
   }

   public boolean selectArticle(String var1) throws IOException {
      return this.selectArticle(var1, (ArticleInfo)null);
   }

   public boolean selectArticle(String var1, ArticleInfo var2) throws IOException {
      if (var1 != null) {
         if (!NNTPReply.isPositiveCompletion(this.stat(var1))) {
            return false;
         }
      } else if (!NNTPReply.isPositiveCompletion(this.stat())) {
         return false;
      }

      if (var2 != null) {
         this.__parseArticlePointer(this.getReplyString(), var2);
      }

      return true;
   }

   @Deprecated
   public boolean selectArticle(String var1, ArticlePointer var2) throws IOException {
      ArticleInfo var3 = this.__ap2ai(var2);
      boolean var4 = this.selectArticle(var1, var3);
      this.__ai2ap(var3, var2);
      return var4;
   }

   public boolean selectArticle(ArticleInfo var1) throws IOException {
      return this.selectArticle((String)null, (ArticleInfo)var1);
   }

   @Deprecated
   public boolean selectArticle(ArticlePointer var1) throws IOException {
      ArticleInfo var2 = this.__ap2ai(var1);
      boolean var3 = this.selectArticle(var2);
      this.__ai2ap(var2, var1);
      return var3;
   }

   public boolean selectNewsgroup(String var1) throws IOException {
      return this.selectNewsgroup(var1, (NewsgroupInfo)null);
   }

   public boolean selectNewsgroup(String var1, NewsgroupInfo var2) throws IOException {
      if (!NNTPReply.isPositiveCompletion(this.group(var1))) {
         return false;
      } else {
         if (var2 != null) {
            __parseGroupReply(this.getReplyString(), var2);
         }

         return true;
      }
   }

   public boolean selectNextArticle() throws IOException {
      return this.selectNextArticle((ArticleInfo)null);
   }

   public boolean selectNextArticle(ArticleInfo var1) throws IOException {
      if (!NNTPReply.isPositiveCompletion(this.next())) {
         return false;
      } else {
         if (var1 != null) {
            this.__parseArticlePointer(this.getReplyString(), var1);
         }

         return true;
      }
   }

   @Deprecated
   public boolean selectNextArticle(ArticlePointer var1) throws IOException {
      ArticleInfo var2 = this.__ap2ai(var1);
      boolean var3 = this.selectNextArticle(var2);
      this.__ai2ap(var2, var1);
      return var3;
   }

   public boolean selectPreviousArticle() throws IOException {
      return this.selectPreviousArticle((ArticleInfo)null);
   }

   public boolean selectPreviousArticle(ArticleInfo var1) throws IOException {
      if (!NNTPReply.isPositiveCompletion(this.last())) {
         return false;
      } else {
         if (var1 != null) {
            this.__parseArticlePointer(this.getReplyString(), var1);
         }

         return true;
      }
   }

   @Deprecated
   public boolean selectPreviousArticle(ArticlePointer var1) throws IOException {
      ArticleInfo var2 = this.__ap2ai(var1);
      boolean var3 = this.selectPreviousArticle(var2);
      this.__ai2ap(var2, var1);
      return var3;
   }
}
