/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;
import org.apache.commons.net.ftp.parser.FTPTimestampParser;
import org.apache.commons.net.ftp.parser.FTPTimestampParserImpl;

public class NTFTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    private static final String DEFAULT_DATE_FORMAT = "MM-dd-yy hh:mma";
    private static final String DEFAULT_DATE_FORMAT2 = "MM-dd-yy kk:mm";
    private static final String REGEX = "(\\S+)\\s+(\\S+)\\s+(?:(<DIR>)|([0-9]+))\\s+(\\S.*)";
    private final FTPTimestampParser timestampParser;

    public NTFTPEntryParser() {
        this(null);
    }

    public NTFTPEntryParser(FTPClientConfig object) {
        super(REGEX, 32);
        this.configure((FTPClientConfig)object);
        FTPClientConfig fTPClientConfig = new FTPClientConfig("WINDOWS", DEFAULT_DATE_FORMAT2, null, null, null, null);
        fTPClientConfig.setDefaultDateFormatStr(DEFAULT_DATE_FORMAT2);
        this.timestampParser = object = new FTPTimestampParserImpl();
        ((Configurable)object).configure(fTPClientConfig);
    }

    @Override
    public FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig("WINDOWS", DEFAULT_DATE_FORMAT, null, null, null, null);
    }

    @Override
    public FTPFile parseFTPEntry(String charSequence) {
        FTPFile fTPFile = new FTPFile();
        fTPFile.setRawListing((String)charSequence);
        if (!this.matches((String)charSequence)) return null;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.group(1));
        ((StringBuilder)charSequence).append(" ");
        ((StringBuilder)charSequence).append(this.group(2));
        String string2 = ((StringBuilder)charSequence).toString();
        String string3 = this.group(3);
        String string4 = this.group(4);
        charSequence = this.group(5);
        try {
            fTPFile.setTimestamp(super.parseTimestamp(string2));
        }
        catch (ParseException parseException) {
            try {
                fTPFile.setTimestamp(this.timestampParser.parseTimestamp(string2));
            }
            catch (ParseException parseException2) {
                // empty catch block
            }
        }
        if (charSequence == null) return null;
        if (((String)charSequence).equals(".")) return null;
        if (((String)charSequence).equals("..")) {
            return null;
        }
        fTPFile.setName((String)charSequence);
        if ("<DIR>".equals(string3)) {
            fTPFile.setType(1);
            fTPFile.setSize(0L);
            return fTPFile;
        }
        fTPFile.setType(0);
        if (string4 == null) return fTPFile;
        fTPFile.setSize(Long.parseLong(string4));
        return fTPFile;
    }
}

