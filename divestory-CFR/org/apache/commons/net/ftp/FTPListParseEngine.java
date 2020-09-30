/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPFileFilters;
import org.apache.commons.net.util.Charsets;

public class FTPListParseEngine {
    private ListIterator<String> _internalIterator;
    private List<String> entries;
    private final FTPFileEntryParser parser;
    private final boolean saveUnparseableEntries;

    public FTPListParseEngine(FTPFileEntryParser fTPFileEntryParser) {
        this(fTPFileEntryParser, null);
    }

    FTPListParseEngine(FTPFileEntryParser fTPFileEntryParser, FTPClientConfig fTPClientConfig) {
        LinkedList<String> linkedList = new LinkedList<String>();
        this.entries = linkedList;
        this._internalIterator = linkedList.listIterator();
        this.parser = fTPFileEntryParser;
        if (fTPClientConfig != null) {
            this.saveUnparseableEntries = fTPClientConfig.getUnparseableEntries();
            return;
        }
        this.saveUnparseableEntries = false;
    }

    private void readStream(InputStream object, String object2) throws IOException {
        object2 = new BufferedReader(new InputStreamReader((InputStream)object, Charsets.toCharset((String)object2)));
        object = this.parser.readNextEntry((BufferedReader)object2);
        do {
            if (object == null) {
                ((BufferedReader)object2).close();
                return;
            }
            this.entries.add((String)object);
            object = this.parser.readNextEntry((BufferedReader)object2);
        } while (true);
    }

    public FTPFile[] getFiles() throws IOException {
        return this.getFiles(FTPFileFilters.NON_NULL);
    }

    public FTPFile[] getFiles(FTPFileFilter fTPFileFilter) throws IOException {
        ArrayList<FTPFile> arrayList = new ArrayList<FTPFile>();
        Iterator<String> iterator2 = this.entries.iterator();
        while (iterator2.hasNext()) {
            FTPFile fTPFile;
            String string2 = iterator2.next();
            FTPFile fTPFile2 = fTPFile = this.parser.parseFTPEntry(string2);
            if (fTPFile == null) {
                fTPFile2 = fTPFile;
                if (this.saveUnparseableEntries) {
                    fTPFile2 = new FTPFile(string2);
                }
            }
            if (!fTPFileFilter.accept(fTPFile2)) continue;
            arrayList.add(fTPFile2);
        }
        return arrayList.toArray(new FTPFile[arrayList.size()]);
    }

    public FTPFile[] getNext(int n) {
        LinkedList<FTPFile> linkedList = new LinkedList<FTPFile>();
        while (n > 0) {
            FTPFile fTPFile;
            if (!this._internalIterator.hasNext()) return linkedList.toArray(new FTPFile[linkedList.size()]);
            String string2 = this._internalIterator.next();
            FTPFile fTPFile2 = fTPFile = this.parser.parseFTPEntry(string2);
            if (fTPFile == null) {
                fTPFile2 = fTPFile;
                if (this.saveUnparseableEntries) {
                    fTPFile2 = new FTPFile(string2);
                }
            }
            linkedList.add(fTPFile2);
            --n;
        }
        return linkedList.toArray(new FTPFile[linkedList.size()]);
    }

    public FTPFile[] getPrevious(int n) {
        LinkedList<FTPFile> linkedList = new LinkedList<FTPFile>();
        while (n > 0) {
            FTPFile fTPFile;
            if (!this._internalIterator.hasPrevious()) return linkedList.toArray(new FTPFile[linkedList.size()]);
            String string2 = this._internalIterator.previous();
            FTPFile fTPFile2 = fTPFile = this.parser.parseFTPEntry(string2);
            if (fTPFile == null) {
                fTPFile2 = fTPFile;
                if (this.saveUnparseableEntries) {
                    fTPFile2 = new FTPFile(string2);
                }
            }
            linkedList.add(0, fTPFile2);
            --n;
        }
        return linkedList.toArray(new FTPFile[linkedList.size()]);
    }

    public boolean hasNext() {
        return this._internalIterator.hasNext();
    }

    public boolean hasPrevious() {
        return this._internalIterator.hasPrevious();
    }

    @Deprecated
    public void readServerList(InputStream inputStream2) throws IOException {
        this.readServerList(inputStream2, null);
    }

    public void readServerList(InputStream inputStream2, String string2) throws IOException {
        this.entries = new LinkedList<String>();
        this.readStream(inputStream2, string2);
        this.parser.preParse(this.entries);
        this.resetIterator();
    }

    public void resetIterator() {
        this._internalIterator = this.entries.listIterator();
    }
}

