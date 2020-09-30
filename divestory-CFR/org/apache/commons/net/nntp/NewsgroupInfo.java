/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

public final class NewsgroupInfo {
    public static final int MODERATED_POSTING_PERMISSION = 1;
    public static final int PERMITTED_POSTING_PERMISSION = 2;
    public static final int PROHIBITED_POSTING_PERMISSION = 3;
    public static final int UNKNOWN_POSTING_PERMISSION = 0;
    private long __estimatedArticleCount;
    private long __firstArticle;
    private long __lastArticle;
    private String __newsgroup;
    private int __postingPermission;

    void _setArticleCount(long l) {
        this.__estimatedArticleCount = l;
    }

    void _setFirstArticle(long l) {
        this.__firstArticle = l;
    }

    void _setLastArticle(long l) {
        this.__lastArticle = l;
    }

    void _setNewsgroup(String string2) {
        this.__newsgroup = string2;
    }

    void _setPostingPermission(int n) {
        this.__postingPermission = n;
    }

    @Deprecated
    public int getArticleCount() {
        return (int)this.__estimatedArticleCount;
    }

    public long getArticleCountLong() {
        return this.__estimatedArticleCount;
    }

    @Deprecated
    public int getFirstArticle() {
        return (int)this.__firstArticle;
    }

    public long getFirstArticleLong() {
        return this.__firstArticle;
    }

    @Deprecated
    public int getLastArticle() {
        return (int)this.__lastArticle;
    }

    public long getLastArticleLong() {
        return this.__lastArticle;
    }

    public String getNewsgroup() {
        return this.__newsgroup;
    }

    public int getPostingPermission() {
        return this.__postingPermission;
    }
}

