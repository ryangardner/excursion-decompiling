/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpConnection;

@Deprecated
public class IdleConnectionHandler {
    private final Map<HttpConnection, TimeValues> connectionToTimes = new HashMap<HttpConnection, TimeValues>();
    private final Log log = LogFactory.getLog(this.getClass());

    public void add(HttpConnection httpConnection, long l, TimeUnit timeUnit) {
        long l2 = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            Log log = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Adding connection at: ");
            stringBuilder.append(l2);
            log.debug((Object)stringBuilder.toString());
        }
        this.connectionToTimes.put(httpConnection, new TimeValues(l2, l, timeUnit));
    }

    public void closeExpiredConnections() {
        Object object;
        Object object2;
        long l = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            object2 = this.log;
            object = new StringBuilder();
            ((StringBuilder)object).append("Checking for expired connections, now: ");
            ((StringBuilder)object).append(l);
            object2.debug((Object)((StringBuilder)object).toString());
        }
        object = this.connectionToTimes.entrySet().iterator();
        while (object.hasNext()) {
            Object object3 = (Map.Entry)object.next();
            object2 = (HttpConnection)object3.getKey();
            TimeValues timeValues = (TimeValues)object3.getValue();
            if (timeValues.timeExpires > l) continue;
            if (this.log.isDebugEnabled()) {
                Log log = this.log;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Closing connection, expired @: ");
                ((StringBuilder)object3).append(timeValues.timeExpires);
                log.debug((Object)((StringBuilder)object3).toString());
            }
            try {
                object2.close();
            }
            catch (IOException iOException) {
                this.log.debug((Object)"I/O error closing connection", (Throwable)iOException);
            }
        }
    }

    public void closeIdleConnections(long l) {
        Object object;
        Object object2;
        l = System.currentTimeMillis() - l;
        if (this.log.isDebugEnabled()) {
            object2 = this.log;
            object = new StringBuilder();
            ((StringBuilder)object).append("Checking for connections, idle timeout: ");
            ((StringBuilder)object).append(l);
            object2.debug((Object)((StringBuilder)object).toString());
        }
        object2 = this.connectionToTimes.entrySet().iterator();
        while (object2.hasNext()) {
            Map.Entry entry = (Map.Entry)object2.next();
            object = (HttpConnection)entry.getKey();
            long l2 = ((TimeValues)entry.getValue()).timeAdded;
            if (l2 > l) continue;
            if (this.log.isDebugEnabled()) {
                entry = this.log;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Closing idle connection, connection time: ");
                stringBuilder.append(l2);
                entry.debug((Object)stringBuilder.toString());
            }
            try {
                object.close();
            }
            catch (IOException iOException) {
                this.log.debug((Object)"I/O error closing connection", (Throwable)iOException);
            }
        }
    }

    public boolean remove(HttpConnection object) {
        object = this.connectionToTimes.remove(object);
        boolean bl = true;
        if (object == null) {
            this.log.warn((Object)"Removing a connection that never existed!");
            return true;
        }
        if (System.currentTimeMillis() > ((TimeValues)object).timeExpires) return false;
        return bl;
    }

    public void removeAll() {
        this.connectionToTimes.clear();
    }

    private static class TimeValues {
        private final long timeAdded;
        private final long timeExpires;

        TimeValues(long l, long l2, TimeUnit timeUnit) {
            this.timeAdded = l;
            if (l2 > 0L) {
                this.timeExpires = l + timeUnit.toMillis(l2);
                return;
            }
            this.timeExpires = Long.MAX_VALUE;
        }
    }

}

