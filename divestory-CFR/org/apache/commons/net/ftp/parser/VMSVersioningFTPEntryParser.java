/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.parser.VMSFTPEntryParser;

public class VMSVersioningFTPEntryParser
extends VMSFTPEntryParser {
    private static final String PRE_PARSE_REGEX = "(.*?);([0-9]+)\\s*.*";
    private final Pattern _preparse_pattern_;

    public VMSVersioningFTPEntryParser() {
        this(null);
    }

    public VMSVersioningFTPEntryParser(FTPClientConfig fTPClientConfig) {
        this.configure(fTPClientConfig);
        try {
            this._preparse_pattern_ = Pattern.compile(PRE_PARSE_REGEX);
            return;
        }
        catch (PatternSyntaxException patternSyntaxException) {
            throw new IllegalArgumentException("Unparseable regex supplied:  (.*?);([0-9]+)\\s*.*");
        }
    }

    @Override
    protected boolean isVersioning() {
        return true;
    }

    @Override
    public List<String> preParse(List<String> list) {
        Object object;
        Object object2;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            object = listIterator.next().trim();
            if (!((Matcher)(object = this._preparse_pattern_.matcher((CharSequence)object))).matches()) continue;
            object2 = ((Matcher)object).toMatchResult();
            object = object2.group(1);
            object2 = Integer.valueOf(object2.group(2));
            Integer n = (Integer)hashMap.get(object);
            if (n != null && (Integer)object2 < n) {
                listIterator.remove();
                continue;
            }
            hashMap.put(object, object2);
        }
        while (listIterator.hasPrevious()) {
            object = listIterator.previous().trim();
            if (!((Matcher)(object = this._preparse_pattern_.matcher((CharSequence)object))).matches()) continue;
            object2 = ((Matcher)object).toMatchResult();
            object = object2.group(1);
            object2 = Integer.valueOf(object2.group(2));
            if ((object = (Integer)hashMap.get(object)) == null || (Integer)object2 >= (Integer)object) continue;
            listIterator.remove();
        }
        return list;
    }
}

