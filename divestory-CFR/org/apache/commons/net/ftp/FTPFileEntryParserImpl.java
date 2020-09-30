/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import org.apache.commons.net.ftp.FTPFileEntryParser;

public abstract class FTPFileEntryParserImpl
implements FTPFileEntryParser {
    @Override
    public List<String> preParse(List<String> list) {
        return list;
    }

    @Override
    public String readNextEntry(BufferedReader bufferedReader) throws IOException {
        return bufferedReader.readLine();
    }
}

