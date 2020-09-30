/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.imap;

public final class IMAPCommand
extends Enum<IMAPCommand> {
    private static final /* synthetic */ IMAPCommand[] $VALUES;
    public static final /* enum */ IMAPCommand APPEND;
    public static final /* enum */ IMAPCommand AUTHENTICATE;
    public static final /* enum */ IMAPCommand CAPABILITY;
    public static final /* enum */ IMAPCommand CHECK;
    public static final /* enum */ IMAPCommand CLOSE;
    public static final /* enum */ IMAPCommand COPY;
    public static final /* enum */ IMAPCommand CREATE;
    public static final /* enum */ IMAPCommand DELETE;
    public static final /* enum */ IMAPCommand EXAMINE;
    public static final /* enum */ IMAPCommand EXPUNGE;
    public static final /* enum */ IMAPCommand FETCH;
    public static final /* enum */ IMAPCommand LIST;
    public static final /* enum */ IMAPCommand LOGIN;
    public static final /* enum */ IMAPCommand LOGOUT;
    public static final /* enum */ IMAPCommand LSUB;
    public static final /* enum */ IMAPCommand NOOP;
    public static final /* enum */ IMAPCommand RENAME;
    public static final /* enum */ IMAPCommand SEARCH;
    public static final /* enum */ IMAPCommand SELECT;
    public static final /* enum */ IMAPCommand STARTTLS;
    public static final /* enum */ IMAPCommand STATUS;
    public static final /* enum */ IMAPCommand STORE;
    public static final /* enum */ IMAPCommand SUBSCRIBE;
    public static final /* enum */ IMAPCommand UID;
    public static final /* enum */ IMAPCommand UNSUBSCRIBE;
    public static final /* enum */ IMAPCommand XOAUTH;
    private final String imapCommand;
    private final int maxParamCount;
    private final int minParamCount;

    static {
        IMAPCommand iMAPCommand;
        CAPABILITY = new IMAPCommand(0);
        NOOP = new IMAPCommand(0);
        LOGOUT = new IMAPCommand(0);
        STARTTLS = new IMAPCommand(0);
        AUTHENTICATE = new IMAPCommand(1);
        LOGIN = new IMAPCommand(2);
        XOAUTH = new IMAPCommand(1);
        SELECT = new IMAPCommand(1);
        EXAMINE = new IMAPCommand(1);
        CREATE = new IMAPCommand(1);
        DELETE = new IMAPCommand(1);
        RENAME = new IMAPCommand(2);
        SUBSCRIBE = new IMAPCommand(1);
        UNSUBSCRIBE = new IMAPCommand(1);
        LIST = new IMAPCommand(2);
        LSUB = new IMAPCommand(2);
        STATUS = new IMAPCommand(2);
        APPEND = new IMAPCommand(2, 4);
        CHECK = new IMAPCommand(0);
        CLOSE = new IMAPCommand(0);
        EXPUNGE = new IMAPCommand(0);
        SEARCH = new IMAPCommand(1, Integer.MAX_VALUE);
        FETCH = new IMAPCommand(2);
        STORE = new IMAPCommand(3);
        COPY = new IMAPCommand(2);
        UID = iMAPCommand = new IMAPCommand(2, Integer.MAX_VALUE);
        $VALUES = new IMAPCommand[]{CAPABILITY, NOOP, LOGOUT, STARTTLS, AUTHENTICATE, LOGIN, XOAUTH, SELECT, EXAMINE, CREATE, DELETE, RENAME, SUBSCRIBE, UNSUBSCRIBE, LIST, LSUB, STATUS, APPEND, CHECK, CLOSE, EXPUNGE, SEARCH, FETCH, STORE, COPY, iMAPCommand};
    }

    private IMAPCommand() {
        this(null);
    }

    private IMAPCommand(int n2) {
        this(null, n2, n2);
    }

    private IMAPCommand(int n2, int n3) {
        this(null, n2, n3);
    }

    private IMAPCommand(String string3) {
        this(string3, 0);
    }

    private IMAPCommand(String string3, int n2) {
        this(string3, n2, n2);
    }

    private IMAPCommand(String string3, int n2, int n3) {
        this.imapCommand = string3;
        this.minParamCount = n2;
        this.maxParamCount = n3;
    }

    public static final String getCommand(IMAPCommand iMAPCommand) {
        return iMAPCommand.getIMAPCommand();
    }

    public static IMAPCommand valueOf(String string2) {
        return Enum.valueOf(IMAPCommand.class, string2);
    }

    public static IMAPCommand[] values() {
        return (IMAPCommand[])$VALUES.clone();
    }

    public String getIMAPCommand() {
        String string2 = this.imapCommand;
        if (string2 == null) return this.name();
        return string2;
    }
}

