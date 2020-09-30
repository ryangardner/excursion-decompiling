/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;

public class NetwareFTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    private static final String DEFAULT_DATE_FORMAT = "MMM dd yyyy";
    private static final String DEFAULT_RECENT_DATE_FORMAT = "MMM dd HH:mm";
    private static final String REGEX = "(d|-){1}\\s+\\[([-A-Z]+)\\]\\s+(\\S+)\\s+(\\d+)\\s+(\\S+\\s+\\S+\\s+((\\d+:\\d+)|(\\d{4})))\\s+(.*)";

    public NetwareFTPEntryParser() {
        this(null);
    }

    public NetwareFTPEntryParser(FTPClientConfig fTPClientConfig) {
        super(REGEX);
        this.configure(fTPClientConfig);
    }

    @Override
    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig("NETWARE", DEFAULT_DATE_FORMAT, DEFAULT_RECENT_DATE_FORMAT, null, null, null);
    }

    @Override
    public FTPFile parseFTPEntry(String string2) {
        FTPFile fTPFile = new FTPFile();
        if (!this.matches(string2)) return null;
        String string3 = this.group(1);
        String string4 = this.group(2);
        String string5 = this.group(3);
        String string6 = this.group(4);
        String string7 = this.group(5);
        string2 = this.group(9);
        try {
            fTPFile.setTimestamp(super.parseTimestamp(string7));
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        if (string3.trim().equals("d")) {
            fTPFile.setType(1);
        } else {
            fTPFile.setType(0);
        }
        fTPFile.setUser(string5);
        fTPFile.setName(string2.trim());
        fTPFile.setSize(Long.parseLong(string6.trim()));
        if (string4.indexOf("R") != -1) {
            fTPFile.setPermission(0, 0, true);
        }
        if (string4.indexOf("W") == -1) return fTPFile;
        fTPFile.setPermission(0, 1, true);
        return fTPFile;
    }
}

