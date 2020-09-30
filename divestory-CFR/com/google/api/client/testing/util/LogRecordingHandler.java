/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.util;

import com.google.api.client.util.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogRecordingHandler
extends Handler {
    private final List<LogRecord> records = Lists.newArrayList();

    @Override
    public void close() throws SecurityException {
    }

    @Override
    public void flush() {
    }

    public List<String> messages() {
        ArrayList<String> arrayList = Lists.newArrayList();
        Iterator<LogRecord> iterator2 = this.records.iterator();
        while (iterator2.hasNext()) {
            arrayList.add(iterator2.next().getMessage());
        }
        return arrayList;
    }

    @Override
    public void publish(LogRecord logRecord) {
        this.records.add(logRecord);
    }
}

