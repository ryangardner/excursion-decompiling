/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.parser.FTPTimestampParser;
import org.apache.commons.net.ftp.parser.FTPTimestampParserImpl;
import org.apache.commons.net.ftp.parser.RegexFTPFileEntryParserImpl;

public abstract class ConfigurableFTPFileEntryParserImpl
extends RegexFTPFileEntryParserImpl
implements Configurable {
    private final FTPTimestampParser timestampParser = new FTPTimestampParserImpl();

    public ConfigurableFTPFileEntryParserImpl(String string2) {
        super(string2);
    }

    public ConfigurableFTPFileEntryParserImpl(String string2, int n) {
        super(string2, n);
    }

    @Override
    public void configure(FTPClientConfig fTPClientConfig) {
        if (!(this.timestampParser instanceof Configurable)) return;
        FTPClientConfig fTPClientConfig2 = this.getDefaultConfiguration();
        if (fTPClientConfig == null) {
            ((Configurable)((Object)this.timestampParser)).configure(fTPClientConfig2);
            return;
        }
        if (fTPClientConfig.getDefaultDateFormatStr() == null) {
            fTPClientConfig.setDefaultDateFormatStr(fTPClientConfig2.getDefaultDateFormatStr());
        }
        if (fTPClientConfig.getRecentDateFormatStr() == null) {
            fTPClientConfig.setRecentDateFormatStr(fTPClientConfig2.getRecentDateFormatStr());
        }
        ((Configurable)((Object)this.timestampParser)).configure(fTPClientConfig);
    }

    protected abstract FTPClientConfig getDefaultConfiguration();

    public Calendar parseTimestamp(String string2) throws ParseException {
        return this.timestampParser.parseTimestamp(string2);
    }
}

