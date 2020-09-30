/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.commons.net.nntp.Threadable;

public class Article
implements Threadable {
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

    public static void printThread(Article article) {
        Article.printThread(article, 0, System.out);
    }

    public static void printThread(Article article, int n) {
        Article.printThread(article, n, System.out);
    }

    public static void printThread(Article article, int n, PrintStream object) {
        for (int i = 0; i < n; ++i) {
            ((PrintStream)object).print("==>");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(article.getSubject());
        stringBuilder.append("\t");
        stringBuilder.append(article.getFrom());
        stringBuilder.append("\t");
        stringBuilder.append(article.getArticleId());
        ((PrintStream)object).println(stringBuilder.toString());
        object = article.kid;
        if (object != null) {
            Article.printThread((Article)object, n + 1);
        }
        if ((article = article.next) == null) return;
        Article.printThread(article, n);
    }

    public static void printThread(Article article, PrintStream printStream) {
        Article.printThread(article, 0, printStream);
    }

    /*
     * Unable to fully structure code
     */
    private void simplifySubject() {
        var1_1 = this.getSubject();
        var2_2 = var1_1.length();
        var3_3 = 0;
        var4_4 = 0;
        while (var3_3 == 0) {
            block8 : {
                while (var4_4 < var2_2 && var1_1.charAt(var4_4) == ' ') {
                    ++var4_4;
                }
                var3_3 = var2_2 - 2;
                if (var4_4 >= var3_3 || var1_1.charAt(var4_4) != 'r' && var1_1.charAt(var4_4) != 'R' || var1_1.charAt(var5_5 = var4_4 + 1) != 'e' && var1_1.charAt(var5_5) != 'E') ** GOTO lbl-1000
                var5_5 = var4_4 + 2;
                if (var1_1.charAt(var5_5) != ':') break block8;
                var4_4 += 3;
                ** GOTO lbl21
            }
            if (var4_4 >= var3_3 || var1_1.charAt(var5_5) != '[' && var1_1.charAt(var5_5) != '(') ** GOTO lbl-1000
            for (var3_3 = var4_4 + 3; var3_3 < var2_2 && var1_1.charAt(var3_3) >= '0' && var1_1.charAt(var3_3) <= '9'; ++var3_3) {
            }
            if (var3_3 < var2_2 - 1 && (var1_1.charAt(var3_3) == ']' || var1_1.charAt(var3_3) == ')') && var1_1.charAt(var3_3 + 1) == ':') {
                var4_4 = var3_3 + 2;
lbl21: // 2 sources:
                var3_3 = 0;
            } else lbl-1000: // 3 sources:
            {
                var3_3 = 1;
            }
            if ("(no subject)".equals(this.simplifiedSubject)) {
                this.simplifiedSubject = "";
            }
            for (var5_5 = var2_2; var5_5 > var4_4 && var1_1.charAt(var5_5 - 1) < ' '; --var5_5) {
            }
            if (var4_4 == 0 && var5_5 == var2_2) {
                this.simplifiedSubject = var1_1;
                continue;
            }
            this.simplifiedSubject = var1_1.substring(var4_4, var5_5);
        }
    }

    @Deprecated
    public void addHeaderField(String string2, String string3) {
    }

    public void addReference(String string2) {
        if (string2 == null) return;
        if (string2.length() == 0) {
            return;
        }
        if (this.references == null) {
            this.references = new ArrayList();
        }
        this.isReply = true;
        String[] arrstring = string2.split(" ");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            string2 = arrstring[n2];
            this.references.add(string2);
            ++n2;
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
        ArrayList<String> arrayList = this.references;
        if (arrayList != null) return arrayList.toArray(new String[arrayList.size()]);
        return new String[0];
    }

    public String getSubject() {
        return this.subject;
    }

    @Override
    public boolean isDummy() {
        if (this.articleNumber != -1L) return false;
        return true;
    }

    @Override
    public Threadable makeDummy() {
        return new Article();
    }

    @Override
    public String messageThreadId() {
        return this.articleId;
    }

    @Override
    public String[] messageThreadReferences() {
        return this.getReferences();
    }

    public void setArticleId(String string2) {
        this.articleId = string2;
    }

    @Deprecated
    public void setArticleNumber(int n) {
        this.articleNumber = n;
    }

    public void setArticleNumber(long l) {
        this.articleNumber = l;
    }

    @Override
    public void setChild(Threadable threadable) {
        this.kid = (Article)threadable;
        this.flushSubjectCache();
    }

    public void setDate(String string2) {
        this.date = string2;
    }

    public void setFrom(String string2) {
        this.from = string2;
    }

    @Override
    public void setNext(Threadable threadable) {
        this.next = (Article)threadable;
        this.flushSubjectCache();
    }

    public void setSubject(String string2) {
        this.subject = string2;
    }

    @Override
    public String simplifiedSubject() {
        if (this.simplifiedSubject != null) return this.simplifiedSubject;
        this.simplifySubject();
        return this.simplifiedSubject;
    }

    @Override
    public boolean subjectIsReply() {
        return this.isReply;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.articleNumber);
        stringBuilder.append(" ");
        stringBuilder.append(this.articleId);
        stringBuilder.append(" ");
        stringBuilder.append(this.subject);
        return stringBuilder.toString();
    }
}

