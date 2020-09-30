/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.imap;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.MalformedServerReplyException;

public final class IMAPReply {
    public static final int BAD = 2;
    public static final int CONT = 3;
    private static final String IMAP_BAD = "BAD";
    private static final String IMAP_CONTINUATION_PREFIX = "+";
    private static final String IMAP_NO = "NO";
    private static final String IMAP_OK = "OK";
    private static final String IMAP_UNTAGGED_PREFIX = "* ";
    private static final Pattern LITERAL_PATTERN;
    public static final int NO = 1;
    public static final int OK = 0;
    public static final int PARTIAL = 3;
    private static final Pattern TAGGED_PATTERN;
    private static final String TAGGED_RESPONSE = "^\\w+ (\\S+).*";
    private static final Pattern UNTAGGED_PATTERN;
    private static final String UNTAGGED_RESPONSE = "^\\* (\\S+).*";

    static {
        TAGGED_PATTERN = Pattern.compile(TAGGED_RESPONSE);
        UNTAGGED_PATTERN = Pattern.compile(UNTAGGED_RESPONSE);
        LITERAL_PATTERN = Pattern.compile("\\{(\\d+)\\}$");
    }

    private IMAPReply() {
    }

    public static int getReplyCode(String string2) throws IOException {
        return IMAPReply.getReplyCode(string2, TAGGED_PATTERN);
    }

    private static int getReplyCode(String string2, Pattern object) throws IOException {
        if (IMAPReply.isContinuation(string2)) {
            return 3;
        }
        if (((Matcher)(object = ((Pattern)object).matcher(string2))).matches()) {
            if (((String)(object = ((Matcher)object).group(1))).equals(IMAP_OK)) {
                return 0;
            }
            if (((String)object).equals(IMAP_BAD)) {
                return 2;
            }
            if (((String)object).equals(IMAP_NO)) {
                return 1;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Received unexpected IMAP protocol response from server: '");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("'.");
        throw new MalformedServerReplyException(((StringBuilder)object).toString());
    }

    public static int getUntaggedReplyCode(String string2) throws IOException {
        return IMAPReply.getReplyCode(string2, UNTAGGED_PATTERN);
    }

    public static boolean isContinuation(int n) {
        if (n != 3) return false;
        return true;
    }

    public static boolean isContinuation(String string2) {
        return string2.startsWith(IMAP_CONTINUATION_PREFIX);
    }

    public static boolean isSuccess(int n) {
        if (n != 0) return false;
        return true;
    }

    public static boolean isUntagged(String string2) {
        return string2.startsWith(IMAP_UNTAGGED_PREFIX);
    }

    public static int literalCount(String object) {
        if (!((Matcher)(object = LITERAL_PATTERN.matcher((CharSequence)object))).find()) return -1;
        return Integer.parseInt(((Matcher)object).group(1));
    }
}

