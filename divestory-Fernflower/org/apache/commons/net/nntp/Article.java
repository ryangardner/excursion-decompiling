package org.apache.commons.net.nntp;

import java.io.PrintStream;
import java.util.ArrayList;

public class Article implements Threadable {
   private String articleId;
   private long articleNumber = -1L;
   private String date;
   private String from;
   private boolean isReply = false;
   public Article kid;
   public Article next;
   private ArrayList<String> references;
   private String simplifiedSubject;
   private String subject;

   private void flushSubjectCache() {
      this.simplifiedSubject = null;
   }

   public static void printThread(Article var0) {
      printThread(var0, 0, System.out);
   }

   public static void printThread(Article var0, int var1) {
      printThread(var0, var1, System.out);
   }

   public static void printThread(Article var0, int var1, PrintStream var2) {
      for(int var3 = 0; var3 < var1; ++var3) {
         var2.print("==>");
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var0.getSubject());
      var4.append("\t");
      var4.append(var0.getFrom());
      var4.append("\t");
      var4.append(var0.getArticleId());
      var2.println(var4.toString());
      Article var5 = var0.kid;
      if (var5 != null) {
         printThread(var5, var1 + 1);
      }

      var0 = var0.next;
      if (var0 != null) {
         printThread(var0, var1);
      }

   }

   public static void printThread(Article var0, PrintStream var1) {
      printThread(var0, 0, var1);
   }

   private void simplifySubject() {
      String var1 = this.getSubject();
      int var2 = var1.length();
      boolean var3 = false;
      int var4 = 0;

      while(true) {
         while(!var3) {
            while(var4 < var2 && var1.charAt(var4) == ' ') {
               ++var4;
            }

            int var5;
            label114: {
               int var6 = var2 - 2;
               if (var4 < var6 && (var1.charAt(var4) == 'r' || var1.charAt(var4) == 'R')) {
                  var5 = var4 + 1;
                  if (var1.charAt(var5) == 'e' || var1.charAt(var5) == 'E') {
                     label116: {
                        var5 = var4 + 2;
                        if (var1.charAt(var5) == ':') {
                           var4 += 3;
                        } else {
                           if (var4 >= var6 || var1.charAt(var5) != '[' && var1.charAt(var5) != '(') {
                              break label116;
                           }

                           for(var6 = var4 + 3; var6 < var2 && var1.charAt(var6) >= '0' && var1.charAt(var6) <= '9'; ++var6) {
                           }

                           if (var6 >= var2 - 1 || var1.charAt(var6) != ']' && var1.charAt(var6) != ')' || var1.charAt(var6 + 1) != ':') {
                              break label116;
                           }

                           var4 = var6 + 2;
                        }

                        var3 = false;
                        break label114;
                     }
                  }
               }

               var3 = true;
            }

            if ("(no subject)".equals(this.simplifiedSubject)) {
               this.simplifiedSubject = "";
            }

            for(var5 = var2; var5 > var4 && var1.charAt(var5 - 1) < ' '; --var5) {
            }

            if (var4 == 0 && var5 == var2) {
               this.simplifiedSubject = var1;
            } else {
               this.simplifiedSubject = var1.substring(var4, var5);
            }
         }

         return;
      }
   }

   @Deprecated
   public void addHeaderField(String var1, String var2) {
   }

   public void addReference(String var1) {
      if (var1 != null && var1.length() != 0) {
         if (this.references == null) {
            this.references = new ArrayList();
         }

         this.isReply = true;
         String[] var2 = var1.split(" ");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var1 = var2[var4];
            this.references.add(var1);
         }
      }

   }

   public String getArticleId() {
      return this.articleId;
   }

   @Deprecated
   public int getArticleNumber() {
      return (int)this.articleNumber;
   }

   public long getArticleNumberLong() {
      return this.articleNumber;
   }

   public String getDate() {
      return this.date;
   }

   public String getFrom() {
      return this.from;
   }

   public String[] getReferences() {
      ArrayList var1 = this.references;
      return var1 == null ? new String[0] : (String[])var1.toArray(new String[var1.size()]);
   }

   public String getSubject() {
      return this.subject;
   }

   public boolean isDummy() {
      boolean var1;
      if (this.articleNumber == -1L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Threadable makeDummy() {
      return new Article();
   }

   public String messageThreadId() {
      return this.articleId;
   }

   public String[] messageThreadReferences() {
      return this.getReferences();
   }

   public void setArticleId(String var1) {
      this.articleId = var1;
   }

   @Deprecated
   public void setArticleNumber(int var1) {
      this.articleNumber = (long)var1;
   }

   public void setArticleNumber(long var1) {
      this.articleNumber = var1;
   }

   public void setChild(Threadable var1) {
      this.kid = (Article)var1;
      this.flushSubjectCache();
   }

   public void setDate(String var1) {
      this.date = var1;
   }

   public void setFrom(String var1) {
      this.from = var1;
   }

   public void setNext(Threadable var1) {
      this.next = (Article)var1;
      this.flushSubjectCache();
   }

   public void setSubject(String var1) {
      this.subject = var1;
   }

   public String simplifiedSubject() {
      if (this.simplifiedSubject == null) {
         this.simplifySubject();
      }

      return this.simplifiedSubject;
   }

   public boolean subjectIsReply() {
      return this.isReply;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.articleNumber);
      var1.append(" ");
      var1.append(this.articleId);
      var1.append(" ");
      var1.append(this.subject);
      return var1.toString();
   }
}
