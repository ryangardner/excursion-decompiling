/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.imap;

import java.io.IOException;
import org.apache.commons.net.imap.IMAP;
import org.apache.commons.net.imap.IMAPCommand;
import org.apache.commons.net.imap.IMAPReply;

public class IMAPClient
extends IMAP {
    private static final char DQUOTE = '\"';
    private static final String DQUOTE_S = "\"";

    @Deprecated
    public boolean append(String string2) throws IOException {
        return this.append(string2, null, null);
    }

    @Deprecated
    public boolean append(String charSequence, String string2, String string3) throws IOException {
        CharSequence charSequence2 = charSequence;
        if (string2 != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(" ");
            ((StringBuilder)charSequence2).append(string2);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (string3 == null) return this.doCommand(IMAPCommand.APPEND, (String)charSequence);
        if (string3.charAt(0) == '{') {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(string3);
            charSequence = ((StringBuilder)charSequence).toString();
            return this.doCommand(IMAPCommand.APPEND, (String)charSequence);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(" {");
        ((StringBuilder)charSequence).append(string3);
        ((StringBuilder)charSequence).append("}");
        charSequence = ((StringBuilder)charSequence).toString();
        return this.doCommand(IMAPCommand.APPEND, (String)charSequence);
    }

    public boolean append(String charSequence, String string2, String string3, String string4) throws IOException {
        charSequence = new StringBuilder((String)charSequence);
        if (string2 != null) {
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(string2);
        }
        boolean bl = false;
        if (string3 != null) {
            ((StringBuilder)charSequence).append(" ");
            if (string3.charAt(0) == '\"') {
                ((StringBuilder)charSequence).append(string3);
            } else {
                ((StringBuilder)charSequence).append('\"');
                ((StringBuilder)charSequence).append(string3);
                ((StringBuilder)charSequence).append('\"');
            }
        }
        ((StringBuilder)charSequence).append(" ");
        if (string4.startsWith(DQUOTE_S) && string4.endsWith(DQUOTE_S)) {
            ((StringBuilder)charSequence).append(string4);
            return this.doCommand(IMAPCommand.APPEND, ((StringBuilder)charSequence).toString());
        }
        ((StringBuilder)charSequence).append('{');
        ((StringBuilder)charSequence).append(string4.length());
        ((StringBuilder)charSequence).append('}');
        boolean bl2 = bl;
        if (!IMAPReply.isContinuation(this.sendCommand(IMAPCommand.APPEND, ((StringBuilder)charSequence).toString()))) return bl2;
        bl2 = bl;
        if (!IMAPReply.isSuccess(this.sendData(string4))) return bl2;
        return true;
    }

    public boolean capability() throws IOException {
        return this.doCommand(IMAPCommand.CAPABILITY);
    }

    public boolean check() throws IOException {
        return this.doCommand(IMAPCommand.CHECK);
    }

    public boolean close() throws IOException {
        return this.doCommand(IMAPCommand.CLOSE);
    }

    public boolean copy(String string2, String string3) throws IOException {
        IMAPCommand iMAPCommand = IMAPCommand.COPY;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        return this.doCommand(iMAPCommand, stringBuilder.toString());
    }

    public boolean create(String string2) throws IOException {
        return this.doCommand(IMAPCommand.CREATE, string2);
    }

    public boolean delete(String string2) throws IOException {
        return this.doCommand(IMAPCommand.DELETE, string2);
    }

    public boolean examine(String string2) throws IOException {
        return this.doCommand(IMAPCommand.EXAMINE, string2);
    }

    public boolean expunge() throws IOException {
        return this.doCommand(IMAPCommand.EXPUNGE);
    }

    public boolean fetch(String string2, String string3) throws IOException {
        IMAPCommand iMAPCommand = IMAPCommand.FETCH;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        return this.doCommand(iMAPCommand, stringBuilder.toString());
    }

    public boolean list(String string2, String string3) throws IOException {
        IMAPCommand iMAPCommand = IMAPCommand.LIST;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        return this.doCommand(iMAPCommand, stringBuilder.toString());
    }

    public boolean login(String string2, String string3) throws IOException {
        if (this.getState() != IMAP.IMAPState.NOT_AUTH_STATE) {
            return false;
        }
        IMAPCommand iMAPCommand = IMAPCommand.LOGIN;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        if (!this.doCommand(iMAPCommand, stringBuilder.toString())) {
            return false;
        }
        this.setState(IMAP.IMAPState.AUTH_STATE);
        return true;
    }

    public boolean logout() throws IOException {
        return this.doCommand(IMAPCommand.LOGOUT);
    }

    public boolean lsub(String string2, String string3) throws IOException {
        IMAPCommand iMAPCommand = IMAPCommand.LSUB;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        return this.doCommand(iMAPCommand, stringBuilder.toString());
    }

    public boolean noop() throws IOException {
        return this.doCommand(IMAPCommand.NOOP);
    }

    public boolean rename(String string2, String string3) throws IOException {
        IMAPCommand iMAPCommand = IMAPCommand.RENAME;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        return this.doCommand(iMAPCommand, stringBuilder.toString());
    }

    public boolean search(String string2) throws IOException {
        return this.search(null, string2);
    }

    public boolean search(String charSequence, String string2) throws IOException {
        CharSequence charSequence2 = "";
        if (charSequence != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("");
            ((StringBuilder)charSequence2).append("CHARSET ");
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(string2);
        charSequence = ((StringBuilder)charSequence).toString();
        return this.doCommand(IMAPCommand.SEARCH, (String)charSequence);
    }

    public boolean select(String string2) throws IOException {
        return this.doCommand(IMAPCommand.SELECT, string2);
    }

    public boolean status(String string2, String[] arrstring) throws IOException {
        if (arrstring == null) throw new IllegalArgumentException("STATUS command requires at least one data item name");
        if (arrstring.length < 1) throw new IllegalArgumentException("STATUS command requires at least one data item name");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        int n = 0;
        do {
            if (n >= arrstring.length) {
                stringBuilder.append(")");
                return this.doCommand(IMAPCommand.STATUS, stringBuilder.toString());
            }
            if (n > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(arrstring[n]);
            ++n;
        } while (true);
    }

    public boolean store(String string2, String string3, String string4) throws IOException {
        IMAPCommand iMAPCommand = IMAPCommand.STORE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        stringBuilder.append(" ");
        stringBuilder.append(string4);
        return this.doCommand(iMAPCommand, stringBuilder.toString());
    }

    public boolean subscribe(String string2) throws IOException {
        return this.doCommand(IMAPCommand.SUBSCRIBE, string2);
    }

    public boolean uid(String string2, String string3) throws IOException {
        IMAPCommand iMAPCommand = IMAPCommand.UID;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        return this.doCommand(iMAPCommand, stringBuilder.toString());
    }

    public boolean unsubscribe(String string2) throws IOException {
        return this.doCommand(IMAPCommand.UNSUBSCRIBE, string2);
    }

    public static final class FETCH_ITEM_NAMES
    extends Enum<FETCH_ITEM_NAMES> {
        private static final /* synthetic */ FETCH_ITEM_NAMES[] $VALUES;
        public static final /* enum */ FETCH_ITEM_NAMES ALL;
        public static final /* enum */ FETCH_ITEM_NAMES BODY;
        public static final /* enum */ FETCH_ITEM_NAMES BODYSTRUCTURE;
        public static final /* enum */ FETCH_ITEM_NAMES ENVELOPE;
        public static final /* enum */ FETCH_ITEM_NAMES FAST;
        public static final /* enum */ FETCH_ITEM_NAMES FLAGS;
        public static final /* enum */ FETCH_ITEM_NAMES FULL;
        public static final /* enum */ FETCH_ITEM_NAMES INTERNALDATE;
        public static final /* enum */ FETCH_ITEM_NAMES RFC822;
        public static final /* enum */ FETCH_ITEM_NAMES UID;

        static {
            FETCH_ITEM_NAMES fETCH_ITEM_NAMES;
            ALL = new FETCH_ITEM_NAMES();
            FAST = new FETCH_ITEM_NAMES();
            FULL = new FETCH_ITEM_NAMES();
            BODY = new FETCH_ITEM_NAMES();
            BODYSTRUCTURE = new FETCH_ITEM_NAMES();
            ENVELOPE = new FETCH_ITEM_NAMES();
            FLAGS = new FETCH_ITEM_NAMES();
            INTERNALDATE = new FETCH_ITEM_NAMES();
            RFC822 = new FETCH_ITEM_NAMES();
            UID = fETCH_ITEM_NAMES = new FETCH_ITEM_NAMES();
            $VALUES = new FETCH_ITEM_NAMES[]{ALL, FAST, FULL, BODY, BODYSTRUCTURE, ENVELOPE, FLAGS, INTERNALDATE, RFC822, fETCH_ITEM_NAMES};
        }

        public static FETCH_ITEM_NAMES valueOf(String string2) {
            return Enum.valueOf(FETCH_ITEM_NAMES.class, string2);
        }

        public static FETCH_ITEM_NAMES[] values() {
            return (FETCH_ITEM_NAMES[])$VALUES.clone();
        }
    }

    public static final class SEARCH_CRITERIA
    extends Enum<SEARCH_CRITERIA> {
        private static final /* synthetic */ SEARCH_CRITERIA[] $VALUES;
        public static final /* enum */ SEARCH_CRITERIA ALL;
        public static final /* enum */ SEARCH_CRITERIA ANSWERED;
        public static final /* enum */ SEARCH_CRITERIA BCC;
        public static final /* enum */ SEARCH_CRITERIA BEFORE;
        public static final /* enum */ SEARCH_CRITERIA BODY;
        public static final /* enum */ SEARCH_CRITERIA CC;
        public static final /* enum */ SEARCH_CRITERIA DELETED;
        public static final /* enum */ SEARCH_CRITERIA DRAFT;
        public static final /* enum */ SEARCH_CRITERIA FLAGGED;
        public static final /* enum */ SEARCH_CRITERIA FROM;
        public static final /* enum */ SEARCH_CRITERIA HEADER;
        public static final /* enum */ SEARCH_CRITERIA KEYWORD;
        public static final /* enum */ SEARCH_CRITERIA LARGER;
        public static final /* enum */ SEARCH_CRITERIA NEW;
        public static final /* enum */ SEARCH_CRITERIA NOT;
        public static final /* enum */ SEARCH_CRITERIA OLD;
        public static final /* enum */ SEARCH_CRITERIA ON;
        public static final /* enum */ SEARCH_CRITERIA OR;
        public static final /* enum */ SEARCH_CRITERIA RECENT;
        public static final /* enum */ SEARCH_CRITERIA SEEN;
        public static final /* enum */ SEARCH_CRITERIA SENTBEFORE;
        public static final /* enum */ SEARCH_CRITERIA SENTON;
        public static final /* enum */ SEARCH_CRITERIA SENTSINCE;
        public static final /* enum */ SEARCH_CRITERIA SINCE;
        public static final /* enum */ SEARCH_CRITERIA SMALLER;
        public static final /* enum */ SEARCH_CRITERIA SUBJECT;
        public static final /* enum */ SEARCH_CRITERIA TEXT;
        public static final /* enum */ SEARCH_CRITERIA TO;
        public static final /* enum */ SEARCH_CRITERIA UID;
        public static final /* enum */ SEARCH_CRITERIA UNANSWERED;
        public static final /* enum */ SEARCH_CRITERIA UNDELETED;
        public static final /* enum */ SEARCH_CRITERIA UNDRAFT;
        public static final /* enum */ SEARCH_CRITERIA UNFLAGGED;
        public static final /* enum */ SEARCH_CRITERIA UNKEYWORD;
        public static final /* enum */ SEARCH_CRITERIA UNSEEN;

        static {
            SEARCH_CRITERIA sEARCH_CRITERIA;
            ALL = new SEARCH_CRITERIA();
            ANSWERED = new SEARCH_CRITERIA();
            BCC = new SEARCH_CRITERIA();
            BEFORE = new SEARCH_CRITERIA();
            BODY = new SEARCH_CRITERIA();
            CC = new SEARCH_CRITERIA();
            DELETED = new SEARCH_CRITERIA();
            DRAFT = new SEARCH_CRITERIA();
            FLAGGED = new SEARCH_CRITERIA();
            FROM = new SEARCH_CRITERIA();
            HEADER = new SEARCH_CRITERIA();
            KEYWORD = new SEARCH_CRITERIA();
            LARGER = new SEARCH_CRITERIA();
            NEW = new SEARCH_CRITERIA();
            NOT = new SEARCH_CRITERIA();
            OLD = new SEARCH_CRITERIA();
            ON = new SEARCH_CRITERIA();
            OR = new SEARCH_CRITERIA();
            RECENT = new SEARCH_CRITERIA();
            SEEN = new SEARCH_CRITERIA();
            SENTBEFORE = new SEARCH_CRITERIA();
            SENTON = new SEARCH_CRITERIA();
            SENTSINCE = new SEARCH_CRITERIA();
            SINCE = new SEARCH_CRITERIA();
            SMALLER = new SEARCH_CRITERIA();
            SUBJECT = new SEARCH_CRITERIA();
            TEXT = new SEARCH_CRITERIA();
            TO = new SEARCH_CRITERIA();
            UID = new SEARCH_CRITERIA();
            UNANSWERED = new SEARCH_CRITERIA();
            UNDELETED = new SEARCH_CRITERIA();
            UNDRAFT = new SEARCH_CRITERIA();
            UNFLAGGED = new SEARCH_CRITERIA();
            UNKEYWORD = new SEARCH_CRITERIA();
            UNSEEN = sEARCH_CRITERIA = new SEARCH_CRITERIA();
            $VALUES = new SEARCH_CRITERIA[]{ALL, ANSWERED, BCC, BEFORE, BODY, CC, DELETED, DRAFT, FLAGGED, FROM, HEADER, KEYWORD, LARGER, NEW, NOT, OLD, ON, OR, RECENT, SEEN, SENTBEFORE, SENTON, SENTSINCE, SINCE, SMALLER, SUBJECT, TEXT, TO, UID, UNANSWERED, UNDELETED, UNDRAFT, UNFLAGGED, UNKEYWORD, sEARCH_CRITERIA};
        }

        public static SEARCH_CRITERIA valueOf(String string2) {
            return Enum.valueOf(SEARCH_CRITERIA.class, string2);
        }

        public static SEARCH_CRITERIA[] values() {
            return (SEARCH_CRITERIA[])$VALUES.clone();
        }
    }

    public static final class STATUS_DATA_ITEMS
    extends Enum<STATUS_DATA_ITEMS> {
        private static final /* synthetic */ STATUS_DATA_ITEMS[] $VALUES;
        public static final /* enum */ STATUS_DATA_ITEMS MESSAGES;
        public static final /* enum */ STATUS_DATA_ITEMS RECENT;
        public static final /* enum */ STATUS_DATA_ITEMS UIDNEXT;
        public static final /* enum */ STATUS_DATA_ITEMS UIDVALIDITY;
        public static final /* enum */ STATUS_DATA_ITEMS UNSEEN;

        static {
            STATUS_DATA_ITEMS sTATUS_DATA_ITEMS;
            MESSAGES = new STATUS_DATA_ITEMS();
            RECENT = new STATUS_DATA_ITEMS();
            UIDNEXT = new STATUS_DATA_ITEMS();
            UIDVALIDITY = new STATUS_DATA_ITEMS();
            UNSEEN = sTATUS_DATA_ITEMS = new STATUS_DATA_ITEMS();
            $VALUES = new STATUS_DATA_ITEMS[]{MESSAGES, RECENT, UIDNEXT, UIDVALIDITY, sTATUS_DATA_ITEMS};
        }

        public static STATUS_DATA_ITEMS valueOf(String string2) {
            return Enum.valueOf(STATUS_DATA_ITEMS.class, string2);
        }

        public static STATUS_DATA_ITEMS[] values() {
            return (STATUS_DATA_ITEMS[])$VALUES.clone();
        }
    }

}

