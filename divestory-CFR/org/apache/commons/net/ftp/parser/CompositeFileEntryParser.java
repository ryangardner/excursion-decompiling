/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.FTPFileEntryParserImpl;

public class CompositeFileEntryParser
extends FTPFileEntryParserImpl {
    private FTPFileEntryParser cachedFtpFileEntryParser = null;
    private final FTPFileEntryParser[] ftpFileEntryParsers;

    public CompositeFileEntryParser(FTPFileEntryParser[] arrfTPFileEntryParser) {
        this.ftpFileEntryParsers = arrfTPFileEntryParser;
    }

    @Override
    public FTPFile parseFTPEntry(String object) {
        FTPFileEntryParser[] arrfTPFileEntryParser = this.cachedFtpFileEntryParser;
        if (arrfTPFileEntryParser != null) {
            if ((object = arrfTPFileEntryParser.parseFTPEntry((String)object)) == null) return null;
            return object;
        }
        arrfTPFileEntryParser = this.ftpFileEntryParsers;
        int n = arrfTPFileEntryParser.length;
        int n2 = 0;
        while (n2 < n) {
            FTPFileEntryParser fTPFileEntryParser = arrfTPFileEntryParser[n2];
            FTPFile fTPFile = fTPFileEntryParser.parseFTPEntry((String)object);
            if (fTPFile != null) {
                this.cachedFtpFileEntryParser = fTPFileEntryParser;
                return fTPFile;
            }
            ++n2;
        }
        return null;
    }
}

