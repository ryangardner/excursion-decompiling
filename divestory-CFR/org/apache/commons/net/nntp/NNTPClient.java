/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.io.DotTerminatedMessageReader;
import org.apache.commons.net.io.DotTerminatedMessageWriter;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.nntp.Article;
import org.apache.commons.net.nntp.ArticleInfo;
import org.apache.commons.net.nntp.ArticleIterator;
import org.apache.commons.net.nntp.ArticlePointer;
import org.apache.commons.net.nntp.NNTP;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.apache.commons.net.nntp.NewsgroupInfo;
import org.apache.commons.net.nntp.NewsgroupIterator;
import org.apache.commons.net.nntp.ReplyIterator;

public class NNTPClient
extends NNTP {
    private void __ai2ap(ArticleInfo articleInfo, ArticlePointer articlePointer) {
        if (articlePointer == null) return;
        articlePointer.articleId = articleInfo.articleId;
        articlePointer.articleNumber = (int)articleInfo.articleNumber;
    }

    private ArticleInfo __ap2ai(ArticlePointer articlePointer) {
        if (articlePointer != null) return new ArticleInfo();
        return null;
    }

    static Article __parseArticleEntry(String arrstring) {
        Article article = new Article();
        article.setSubject((String)arrstring);
        arrstring = arrstring.split("\t");
        if (arrstring.length <= 6) return article;
        try {
            article.setArticleNumber(Long.parseLong(arrstring[0]));
            article.setSubject(arrstring[1]);
            article.setFrom(arrstring[2]);
            article.setDate(arrstring[3]);
            article.setArticleId(arrstring[4]);
            article.addReference(arrstring[5]);
            return article;
        }
        catch (NumberFormatException numberFormatException) {
            return article;
        }
    }

    private void __parseArticlePointer(String string2, ArticleInfo object) throws MalformedServerReplyException {
        String[] arrstring = string2.split(" ");
        if (arrstring.length >= 3) {
            try {
                ((ArticleInfo)object).articleNumber = Long.parseLong(arrstring[1]);
                ((ArticleInfo)object).articleId = arrstring[2];
                return;
            }
            catch (NumberFormatException numberFormatException) {}
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not parse article pointer.\nServer reply: ");
        ((StringBuilder)object).append(string2);
        throw new MalformedServerReplyException(((StringBuilder)object).toString());
    }

    private static void __parseGroupReply(String string2, NewsgroupInfo object) throws MalformedServerReplyException {
        String[] arrstring = string2.split(" ");
        if (arrstring.length >= 5) {
            try {
                ((NewsgroupInfo)object)._setArticleCount(Long.parseLong(arrstring[1]));
                ((NewsgroupInfo)object)._setFirstArticle(Long.parseLong(arrstring[2]));
                ((NewsgroupInfo)object)._setLastArticle(Long.parseLong(arrstring[3]));
                ((NewsgroupInfo)object)._setNewsgroup(arrstring[4]);
                ((NewsgroupInfo)object)._setPostingPermission(0);
                return;
            }
            catch (NumberFormatException numberFormatException) {}
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not parse newsgroup info.\nServer reply: ");
        ((StringBuilder)object).append(string2);
        throw new MalformedServerReplyException(((StringBuilder)object).toString());
    }

    static NewsgroupInfo __parseNewsgroupListEntry(String object) {
        block6 : {
            block7 : {
                block8 : {
                    String[] arrstring;
                    block5 : {
                        arrstring = ((String)object).split(" ");
                        if (arrstring.length < 4) {
                            return null;
                        }
                        object = new NewsgroupInfo();
                        ((NewsgroupInfo)object)._setNewsgroup(arrstring[0]);
                        try {
                            long l = Long.parseLong(arrstring[1]);
                            long l2 = Long.parseLong(arrstring[2]);
                            ((NewsgroupInfo)object)._setFirstArticle(l2);
                            ((NewsgroupInfo)object)._setLastArticle(l);
                            if (l2 == 0L && l == 0L) {
                                ((NewsgroupInfo)object)._setArticleCount(0L);
                                break block5;
                            }
                            ((NewsgroupInfo)object)._setArticleCount(l - l2 + 1L);
                        }
                        catch (NumberFormatException numberFormatException) {
                            return null;
                        }
                    }
                    char c = arrstring[3].charAt(0);
                    if (c == 'M') break block6;
                    if (c == 'N') break block7;
                    if (c == 'Y' || c == 'y') break block8;
                    if (c == 'm') break block6;
                    if (c != 'n') {
                        ((NewsgroupInfo)object)._setPostingPermission(0);
                        return object;
                    }
                    break block7;
                }
                ((NewsgroupInfo)object)._setPostingPermission(2);
                return object;
            }
            ((NewsgroupInfo)object)._setPostingPermission(3);
            return object;
        }
        ((NewsgroupInfo)object)._setPostingPermission(1);
        return object;
    }

    private NewsgroupInfo[] __readNewsgroupListing() throws IOException {
        Object[] arrobject;
        Serializable serializable;
        int n;
        block5 : {
            String string2;
            arrobject = new DotTerminatedMessageReader(this._reader_);
            serializable = new Vector<NewsgroupInfo>(2048);
            while ((string2 = arrobject.readLine()) != null) {
                NewsgroupInfo newsgroupInfo = NNTPClient.__parseNewsgroupListEntry(string2);
                if (newsgroupInfo == null) {
                    serializable = new MalformedServerReplyException(string2);
                    throw serializable;
                }
                ((Vector)serializable).addElement(newsgroupInfo);
            }
            n = ((Vector)serializable).size();
            if (n >= 1) break block5;
            return new NewsgroupInfo[0];
        }
        arrobject = new NewsgroupInfo[n];
        ((Vector)serializable).copyInto(arrobject);
        return arrobject;
        finally {
            arrobject.close();
        }
    }

    private BufferedReader __retrieve(int n, long l, ArticleInfo articleInfo) throws IOException {
        if (!NNTPReply.isPositiveCompletion(this.sendCommand(n, Long.toString(l)))) {
            return null;
        }
        if (articleInfo == null) return new DotTerminatedMessageReader(this._reader_);
        this.__parseArticlePointer(this.getReplyString(), articleInfo);
        return new DotTerminatedMessageReader(this._reader_);
    }

    private BufferedReader __retrieve(int n, String string2, ArticleInfo articleInfo) throws IOException {
        if (string2 != null ? !NNTPReply.isPositiveCompletion(this.sendCommand(n, string2)) : !NNTPReply.isPositiveCompletion(this.sendCommand(n))) {
            return null;
        }
        if (articleInfo == null) return new DotTerminatedMessageReader(this._reader_);
        this.__parseArticlePointer(this.getReplyString(), articleInfo);
        return new DotTerminatedMessageReader(this._reader_);
    }

    private BufferedReader __retrieveArticleInfo(String string2) throws IOException {
        if (NNTPReply.isPositiveCompletion(this.xover(string2))) return new DotTerminatedMessageReader(this._reader_);
        return null;
    }

    private BufferedReader __retrieveHeader(String string2, String string3) throws IOException {
        if (NNTPReply.isPositiveCompletion(this.xhdr(string2, string3))) return new DotTerminatedMessageReader(this._reader_);
        return null;
    }

    public boolean authenticate(String string2, String string3) throws IOException {
        if (this.authinfoUser(string2) != 381) return false;
        if (this.authinfoPass(string3) != 281) return false;
        this._isAllowedToPost = true;
        return true;
    }

    public boolean completePendingCommand() throws IOException {
        return NNTPReply.isPositiveCompletion(this.getReply());
    }

    public Writer forwardArticle(String string2) throws IOException {
        if (NNTPReply.isPositiveIntermediate(this.ihave(string2))) return new DotTerminatedMessageWriter(this._writer_);
        return null;
    }

    public Iterable<Article> iterateArticleInfo(long l, long l2) throws IOException {
        Object object = this.retrieveArticleInfo(l, l2);
        if (object != null) {
            return new ArticleIterator(new ReplyIterator((BufferedReader)object, false));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("XOVER command failed: ");
        ((StringBuilder)object).append(this.getReplyString());
        throw new IOException(((StringBuilder)object).toString());
    }

    public Iterable<String> iterateNewNews(NewGroupsOrNewsQuery object) throws IOException {
        if (NNTPReply.isPositiveCompletion(this.newnews(((NewGroupsOrNewsQuery)object).getNewsgroups(), ((NewGroupsOrNewsQuery)object).getDate(), ((NewGroupsOrNewsQuery)object).getTime(), ((NewGroupsOrNewsQuery)object).isGMT(), ((NewGroupsOrNewsQuery)object).getDistributions()))) {
            return new ReplyIterator(this._reader_);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("NEWNEWS command failed: ");
        ((StringBuilder)object).append(this.getReplyString());
        throw new IOException(((StringBuilder)object).toString());
    }

    public Iterable<String> iterateNewNewsgroupListing(NewGroupsOrNewsQuery object) throws IOException {
        if (NNTPReply.isPositiveCompletion(this.newgroups(((NewGroupsOrNewsQuery)object).getDate(), ((NewGroupsOrNewsQuery)object).getTime(), ((NewGroupsOrNewsQuery)object).isGMT(), ((NewGroupsOrNewsQuery)object).getDistributions()))) {
            return new ReplyIterator(this._reader_);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("NEWGROUPS command failed: ");
        ((StringBuilder)object).append(this.getReplyString());
        throw new IOException(((StringBuilder)object).toString());
    }

    public Iterable<NewsgroupInfo> iterateNewNewsgroups(NewGroupsOrNewsQuery newGroupsOrNewsQuery) throws IOException {
        return new NewsgroupIterator(this.iterateNewNewsgroupListing(newGroupsOrNewsQuery));
    }

    public Iterable<String> iterateNewsgroupListing() throws IOException {
        if (NNTPReply.isPositiveCompletion(this.list())) {
            return new ReplyIterator(this._reader_);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LIST command failed: ");
        stringBuilder.append(this.getReplyString());
        throw new IOException(stringBuilder.toString());
    }

    public Iterable<String> iterateNewsgroupListing(String string2) throws IOException {
        if (NNTPReply.isPositiveCompletion(this.listActive(string2))) {
            return new ReplyIterator(this._reader_);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LIST ACTIVE ");
        stringBuilder.append(string2);
        stringBuilder.append(" command failed: ");
        stringBuilder.append(this.getReplyString());
        throw new IOException(stringBuilder.toString());
    }

    public Iterable<NewsgroupInfo> iterateNewsgroups() throws IOException {
        return new NewsgroupIterator(this.iterateNewsgroupListing());
    }

    public Iterable<NewsgroupInfo> iterateNewsgroups(String string2) throws IOException {
        return new NewsgroupIterator(this.iterateNewsgroupListing(string2));
    }

    public String listHelp() throws IOException {
        if (!NNTPReply.isInformational(this.help())) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        DotTerminatedMessageReader dotTerminatedMessageReader = new DotTerminatedMessageReader(this._reader_);
        Util.copyReader(dotTerminatedMessageReader, stringWriter);
        ((BufferedReader)dotTerminatedMessageReader).close();
        stringWriter.close();
        return stringWriter.toString();
    }

    public String[] listNewNews(NewGroupsOrNewsQuery object) throws IOException {
        int n;
        Object[] arrobject;
        block5 : {
            String string2;
            if (!NNTPReply.isPositiveCompletion(this.newnews(((NewGroupsOrNewsQuery)object).getNewsgroups(), ((NewGroupsOrNewsQuery)object).getDate(), ((NewGroupsOrNewsQuery)object).getTime(), ((NewGroupsOrNewsQuery)object).isGMT(), ((NewGroupsOrNewsQuery)object).getDistributions()))) {
                return null;
            }
            object = new Vector();
            arrobject = new DotTerminatedMessageReader(this._reader_);
            while ((string2 = arrobject.readLine()) != null) {
                ((Vector)object).addElement(string2);
            }
            n = ((Vector)object).size();
            if (n >= 1) break block5;
            return new String[0];
        }
        arrobject = new String[n];
        ((Vector)object).copyInto(arrobject);
        return arrobject;
        finally {
            arrobject.close();
        }
    }

    public NewsgroupInfo[] listNewNewsgroups(NewGroupsOrNewsQuery newGroupsOrNewsQuery) throws IOException {
        if (NNTPReply.isPositiveCompletion(this.newgroups(newGroupsOrNewsQuery.getDate(), newGroupsOrNewsQuery.getTime(), newGroupsOrNewsQuery.isGMT(), newGroupsOrNewsQuery.getDistributions()))) return this.__readNewsgroupListing();
        return null;
    }

    public NewsgroupInfo[] listNewsgroups() throws IOException {
        if (NNTPReply.isPositiveCompletion(this.list())) return this.__readNewsgroupListing();
        return null;
    }

    public NewsgroupInfo[] listNewsgroups(String string2) throws IOException {
        if (NNTPReply.isPositiveCompletion(this.listActive(string2))) return this.__readNewsgroupListing();
        return null;
    }

    public String[] listOverviewFmt() throws IOException {
        if (!NNTPReply.isPositiveCompletion(this.sendCommand("LIST", "OVERVIEW.FMT"))) {
            return null;
        }
        DotTerminatedMessageReader dotTerminatedMessageReader = new DotTerminatedMessageReader(this._reader_);
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            String string2;
            if ((string2 = ((BufferedReader)dotTerminatedMessageReader).readLine()) == null) {
                ((BufferedReader)dotTerminatedMessageReader).close();
                return arrayList.toArray(new String[arrayList.size()]);
            }
            arrayList.add(string2);
        } while (true);
    }

    public boolean logout() throws IOException {
        return NNTPReply.isPositiveCompletion(this.quit());
    }

    public Writer postArticle() throws IOException {
        if (NNTPReply.isPositiveIntermediate(this.post())) return new DotTerminatedMessageWriter(this._writer_);
        return null;
    }

    public BufferedReader retrieveArticle(long l) throws IOException {
        return this.retrieveArticle(l, null);
    }

    public BufferedReader retrieveArticle(long l, ArticleInfo articleInfo) throws IOException {
        return this.__retrieve(0, l, articleInfo);
    }

    public BufferedReader retrieveArticle(String string2, ArticleInfo articleInfo) throws IOException {
        return this.__retrieve(0, string2, articleInfo);
    }

    public Reader retrieveArticle() throws IOException {
        return this.retrieveArticle(null);
    }

    @Deprecated
    public Reader retrieveArticle(int n) throws IOException {
        return this.retrieveArticle((long)n);
    }

    @Deprecated
    public Reader retrieveArticle(int n, ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        BufferedReader bufferedReader = this.retrieveArticle((long)n, articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return bufferedReader;
    }

    public Reader retrieveArticle(String string2) throws IOException {
        return this.retrieveArticle(string2, (ArticleInfo)null);
    }

    @Deprecated
    public Reader retrieveArticle(String object, ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        object = this.retrieveArticle((String)object, articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return object;
    }

    public BufferedReader retrieveArticleBody(long l) throws IOException {
        return this.retrieveArticleBody(l, null);
    }

    public BufferedReader retrieveArticleBody(long l, ArticleInfo articleInfo) throws IOException {
        return this.__retrieve(1, l, articleInfo);
    }

    public BufferedReader retrieveArticleBody(String string2, ArticleInfo articleInfo) throws IOException {
        return this.__retrieve(1, string2, articleInfo);
    }

    public Reader retrieveArticleBody() throws IOException {
        return this.retrieveArticleBody(null);
    }

    @Deprecated
    public Reader retrieveArticleBody(int n) throws IOException {
        return this.retrieveArticleBody((long)n);
    }

    @Deprecated
    public Reader retrieveArticleBody(int n, ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        BufferedReader bufferedReader = this.retrieveArticleBody((long)n, articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return bufferedReader;
    }

    public Reader retrieveArticleBody(String string2) throws IOException {
        return this.retrieveArticleBody(string2, (ArticleInfo)null);
    }

    @Deprecated
    public Reader retrieveArticleBody(String object, ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        object = this.retrieveArticleBody((String)object, articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return object;
    }

    public BufferedReader retrieveArticleHeader(long l) throws IOException {
        return this.retrieveArticleHeader(l, null);
    }

    public BufferedReader retrieveArticleHeader(long l, ArticleInfo articleInfo) throws IOException {
        return this.__retrieve(3, l, articleInfo);
    }

    public BufferedReader retrieveArticleHeader(String string2, ArticleInfo articleInfo) throws IOException {
        return this.__retrieve(3, string2, articleInfo);
    }

    public Reader retrieveArticleHeader() throws IOException {
        return this.retrieveArticleHeader(null);
    }

    @Deprecated
    public Reader retrieveArticleHeader(int n) throws IOException {
        return this.retrieveArticleHeader((long)n);
    }

    @Deprecated
    public Reader retrieveArticleHeader(int n, ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        BufferedReader bufferedReader = this.retrieveArticleHeader((long)n, articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return bufferedReader;
    }

    public Reader retrieveArticleHeader(String string2) throws IOException {
        return this.retrieveArticleHeader(string2, (ArticleInfo)null);
    }

    @Deprecated
    public Reader retrieveArticleHeader(String object, ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        object = this.retrieveArticleHeader((String)object, articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return object;
    }

    public BufferedReader retrieveArticleInfo(long l) throws IOException {
        return this.__retrieveArticleInfo(Long.toString(l));
    }

    public BufferedReader retrieveArticleInfo(long l, long l2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(l);
        stringBuilder.append("-");
        stringBuilder.append(l2);
        return this.__retrieveArticleInfo(stringBuilder.toString());
    }

    @Deprecated
    public Reader retrieveArticleInfo(int n) throws IOException {
        return this.retrieveArticleInfo((long)n);
    }

    @Deprecated
    public Reader retrieveArticleInfo(int n, int n2) throws IOException {
        return this.retrieveArticleInfo((long)n, (long)n2);
    }

    public BufferedReader retrieveHeader(String string2, long l) throws IOException {
        return this.__retrieveHeader(string2, Long.toString(l));
    }

    public BufferedReader retrieveHeader(String string2, long l, long l2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(l);
        stringBuilder.append("-");
        stringBuilder.append(l2);
        return this.__retrieveHeader(string2, stringBuilder.toString());
    }

    @Deprecated
    public Reader retrieveHeader(String string2, int n) throws IOException {
        return this.retrieveHeader(string2, (long)n);
    }

    @Deprecated
    public Reader retrieveHeader(String string2, int n, int n2) throws IOException {
        return this.retrieveHeader(string2, (long)n, (long)n2);
    }

    @Deprecated
    public boolean selectArticle(int n) throws IOException {
        return this.selectArticle((long)n);
    }

    @Deprecated
    public boolean selectArticle(int n, ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        boolean bl = this.selectArticle((long)n, articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return bl;
    }

    public boolean selectArticle(long l) throws IOException {
        return this.selectArticle(l, null);
    }

    public boolean selectArticle(long l, ArticleInfo articleInfo) throws IOException {
        if (!NNTPReply.isPositiveCompletion(this.stat(l))) {
            return false;
        }
        if (articleInfo == null) return true;
        this.__parseArticlePointer(this.getReplyString(), articleInfo);
        return true;
    }

    public boolean selectArticle(String string2) throws IOException {
        return this.selectArticle(string2, (ArticleInfo)null);
    }

    public boolean selectArticle(String string2, ArticleInfo articleInfo) throws IOException {
        if (string2 != null ? !NNTPReply.isPositiveCompletion(this.stat(string2)) : !NNTPReply.isPositiveCompletion(this.stat())) {
            return false;
        }
        if (articleInfo == null) return true;
        this.__parseArticlePointer(this.getReplyString(), articleInfo);
        return true;
    }

    @Deprecated
    public boolean selectArticle(String string2, ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        boolean bl = this.selectArticle(string2, articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return bl;
    }

    public boolean selectArticle(ArticleInfo articleInfo) throws IOException {
        return this.selectArticle(null, articleInfo);
    }

    @Deprecated
    public boolean selectArticle(ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        boolean bl = this.selectArticle(articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return bl;
    }

    public boolean selectNewsgroup(String string2) throws IOException {
        return this.selectNewsgroup(string2, null);
    }

    public boolean selectNewsgroup(String string2, NewsgroupInfo newsgroupInfo) throws IOException {
        if (!NNTPReply.isPositiveCompletion(this.group(string2))) {
            return false;
        }
        if (newsgroupInfo == null) return true;
        NNTPClient.__parseGroupReply(this.getReplyString(), newsgroupInfo);
        return true;
    }

    public boolean selectNextArticle() throws IOException {
        return this.selectNextArticle((ArticleInfo)null);
    }

    public boolean selectNextArticle(ArticleInfo articleInfo) throws IOException {
        if (!NNTPReply.isPositiveCompletion(this.next())) {
            return false;
        }
        if (articleInfo == null) return true;
        this.__parseArticlePointer(this.getReplyString(), articleInfo);
        return true;
    }

    @Deprecated
    public boolean selectNextArticle(ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        boolean bl = this.selectNextArticle(articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return bl;
    }

    public boolean selectPreviousArticle() throws IOException {
        return this.selectPreviousArticle((ArticleInfo)null);
    }

    public boolean selectPreviousArticle(ArticleInfo articleInfo) throws IOException {
        if (!NNTPReply.isPositiveCompletion(this.last())) {
            return false;
        }
        if (articleInfo == null) return true;
        this.__parseArticlePointer(this.getReplyString(), articleInfo);
        return true;
    }

    @Deprecated
    public boolean selectPreviousArticle(ArticlePointer articlePointer) throws IOException {
        ArticleInfo articleInfo = this.__ap2ai(articlePointer);
        boolean bl = this.selectPreviousArticle(articleInfo);
        this.__ai2ap(articleInfo, articlePointer);
        return bl;
    }
}

