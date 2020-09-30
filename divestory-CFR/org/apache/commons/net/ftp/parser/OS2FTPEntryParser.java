/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;

public class OS2FTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    private static final String DEFAULT_DATE_FORMAT = "MM-dd-yy HH:mm";
    private static final String REGEX = "\\s*([0-9]+)\\s*(\\s+|[A-Z]+)\\s*(DIR|\\s+)\\s*(\\S+)\\s+(\\S+)\\s+(\\S.*)";

    public OS2FTPEntryParser() {
        this(null);
    }

    public OS2FTPEntryParser(FTPClientConfig fTPClientConfig) {
        super(REGEX);
        this.configure(fTPClientConfig);
    }

    @Override
    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig("OS/2", DEFAULT_DATE_FORMAT, null, null, null, null);
    }

    @Override
    public FTPFile parseFTPEntry(String string2) {
        FTPFile fTPFile = new FTPFile();
        if (!this.matches(string2)) return null;
        String string3 = this.group(1);
        String string4 = this.group(2);
        string2 = this.group(3);
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.group(4));
        ((StringBuilder)charSequence).append(" ");
        ((StringBuilder)charSequence).append(this.group(5));
        String string5 = ((StringBuilder)charSequence).toString();
        charSequence = this.group(6);
        try {
            fTPFile.setTimestamp(super.parseTimestamp(string5));
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        if (!string2.trim().equals("DIR") && !string4.trim().equals("DIR")) {
            fTPFile.setType(0);
        } else {
            fTPFile.setType(1);
        }
        fTPFile.setName(((String)charSequence).trim());
        fTPFile.setSize(Long.parseLong(string3.trim()));
        return fTPFile;
    }
}

