/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import org.apache.commons.net.ftp.FTPFile;

public interface FTPFileEntryParser {
    public FTPFile parseFTPEntry(String var1);

    public List<String> preParse(List<String> var1);

    public String readNextEntry(BufferedReader var1) throws IOException;
}

