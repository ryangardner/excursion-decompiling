/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UriPatternMatcher {
    private final Map map = new HashMap();

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public Object lookup(String object) {
        // MONITORENTER : this
        if (object != null) {
            Object object2;
            block7 : {
                int n = ((String)object).indexOf("?");
                Object object3 = object;
                if (n != -1) {
                    object3 = ((String)object).substring(0, n);
                }
                object2 = object = this.map.get(object3);
                if (object != null) break block7;
                Object object4 = null;
                Iterator iterator2 = this.map.keySet().iterator();
                do {
                    object2 = object;
                    if (!iterator2.hasNext()) break;
                    object2 = (String)iterator2.next();
                    if (!this.matchUriRequestPattern((String)object2, (String)object3) || object4 != null && ((String)object4).length() >= ((String)object2).length() && (((String)object4).length() != ((String)object2).length() || !((String)object2).endsWith("*"))) continue;
                    object = this.map.get(object2);
                    object4 = object2;
                } while (true);
            }
            // MONITOREXIT : this
            return object2;
        }
        object = new IllegalArgumentException("Request URI may not be null");
        throw object;
    }

    protected boolean matchUriRequestPattern(String string2, String string3) {
        boolean bl = string2.equals("*");
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (string2.endsWith("*")) {
            bl = bl2;
            if (string3.startsWith(string2.substring(0, string2.length() - 1))) return bl;
        }
        if (!string2.startsWith("*")) return false;
        if (!string3.endsWith(string2.substring(1, string2.length()))) return false;
        return bl2;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void register(String var1_1, Object var2_3) {
        // MONITORENTER : this
        if (var1_1 == null) ** GOTO lbl8
        this.map.put(var1_1, var2_3);
        // MONITOREXIT : this
        return;
lbl8: // 1 sources:
        var1_1 = new IllegalArgumentException("URI request pattern may not be null");
        throw var1_1;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void setHandlers(Map var1_1) {
        // MONITORENTER : this
        if (var1_1 == null) ** GOTO lbl8
        this.map.clear();
        this.map.putAll(var1_1);
        // MONITOREXIT : this
        return;
lbl8: // 1 sources:
        var1_1 = new IllegalArgumentException("Map of handlers may not be null");
        throw var1_1;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void setObjects(Map var1_1) {
        // MONITORENTER : this
        if (var1_1 == null) ** GOTO lbl8
        this.map.clear();
        this.map.putAll(var1_1);
        // MONITOREXIT : this
        return;
lbl8: // 1 sources:
        var1_1 = new IllegalArgumentException("Map of handlers may not be null");
        throw var1_1;
    }

    public void unregister(String string2) {
        synchronized (this) {
            if (string2 == null) {
                return;
            }
            this.map.remove(string2);
            return;
        }
    }
}

