/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.net.ftp.FTPFileEntryParserImpl;

public abstract class RegexFTPFileEntryParserImpl
extends FTPFileEntryParserImpl {
    protected Matcher _matcher_ = null;
    private Pattern pattern = null;
    private MatchResult result = null;

    public RegexFTPFileEntryParserImpl(String string2) {
        this.compileRegex(string2, 0);
    }

    public RegexFTPFileEntryParserImpl(String string2, int n) {
        this.compileRegex(string2, n);
    }

    private void compileRegex(String string2, int n) {
        try {
            this.pattern = Pattern.compile(string2, n);
            return;
        }
        catch (PatternSyntaxException patternSyntaxException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unparseable regex supplied: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public int getGroupCnt() {
        MatchResult matchResult = this.result;
        if (matchResult != null) return matchResult.groupCount();
        return 0;
    }

    public String getGroupsAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 1;
        while (n <= this.result.groupCount()) {
            stringBuilder.append(n);
            stringBuilder.append(") ");
            stringBuilder.append(this.result.group(n));
            stringBuilder.append(System.getProperty("line.separator"));
            ++n;
        }
        return stringBuilder.toString();
    }

    public String group(int n) {
        MatchResult matchResult = this.result;
        if (matchResult != null) return matchResult.group(n);
        return null;
    }

    public boolean matches(String object) {
        this.result = null;
        this._matcher_ = object = this.pattern.matcher((CharSequence)object);
        if (((Matcher)object).matches()) {
            this.result = this._matcher_.toMatchResult();
        }
        if (this.result == null) return false;
        return true;
    }

    public boolean setRegex(String string2) {
        this.compileRegex(string2, 0);
        return true;
    }

    public boolean setRegex(String string2, int n) {
        this.compileRegex(string2, n);
        return true;
    }
}

