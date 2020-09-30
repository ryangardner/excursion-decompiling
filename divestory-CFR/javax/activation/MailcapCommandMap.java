/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MailcapFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.activation.CommandInfo;
import javax.activation.CommandMap;
import javax.activation.DataContentHandler;
import javax.activation.SecuritySupport;

public class MailcapCommandMap
extends CommandMap {
    private static final int PROG = 0;
    private static MailcapFile defDB;
    private MailcapFile[] DB;

    /*
     * Enabled unnecessary exception pruning
     */
    public MailcapCommandMap() {
        Object object;
        ArrayList<MailcapFile[]> arrayList = new ArrayList<MailcapFile[]>(5);
        arrayList.add(null);
        LogSupport.log("MailcapCommandMap: load HOME");
        try {
            object = System.getProperty("user.home");
            if (object != null) {
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(object));
                stringBuilder.append(File.separator);
                stringBuilder.append(".mailcap");
                object = this.loadFile(stringBuilder.toString());
                if (object != null) {
                    arrayList.add((MailcapFile[])object);
                }
            }
        }
        catch (SecurityException securityException) {}
        LogSupport.log("MailcapCommandMap: load SYS");
        try {
            super(String.valueOf(System.getProperty("java.home")));
            object.append(File.separator);
            object.append("lib");
            object.append(File.separator);
            object.append("mailcap");
            object = this.loadFile(object.toString());
            if (object != null) {
                arrayList.add((MailcapFile[])object);
            }
        }
        catch (SecurityException securityException) {}
        LogSupport.log("MailcapCommandMap: load JAR");
        this.loadAllResources(arrayList, "mailcap");
        LogSupport.log("MailcapCommandMap: load DEF");
        synchronized (MailcapCommandMap.class) {
            if (defDB == null) {
                defDB = this.loadResource("mailcap.default");
            }
        }
        object = defDB;
        if (object != null) {
            arrayList.add((MailcapFile[])object);
        }
        object = new MailcapFile[arrayList.size()];
        this.DB = object;
        this.DB = arrayList.toArray((T[])object);
    }

    public MailcapCommandMap(InputStream inputStream2) {
        this();
        LogSupport.log("MailcapCommandMap: load PROG");
        MailcapFile[] arrmailcapFile = this.DB;
        if (arrmailcapFile[0] != null) return;
        try {
            MailcapFile mailcapFile;
            arrmailcapFile[0] = mailcapFile = new MailcapFile(inputStream2);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public MailcapCommandMap(String string2) throws IOException {
        this();
        Object object;
        if (LogSupport.isLoggable()) {
            object = new StringBuilder("MailcapCommandMap: load PROG from ");
            ((StringBuilder)object).append(string2);
            LogSupport.log(((StringBuilder)object).toString());
        }
        if ((object = this.DB)[0] != null) return;
        object[0] = new MailcapFile(string2);
    }

    private void appendCmdsToList(Map map, List list) {
        Iterator iterator2 = map.keySet().iterator();
        block0 : while (iterator2.hasNext()) {
            String string2 = (String)iterator2.next();
            Iterator iterator3 = ((List)map.get(string2)).iterator();
            do {
                if (!iterator3.hasNext()) continue block0;
                list.add(new CommandInfo(string2, (String)iterator3.next()));
            } while (true);
            break;
        }
        return;
    }

    private void appendPrefCmdsToList(Map map, List list) {
        Iterator iterator2 = map.keySet().iterator();
        while (iterator2.hasNext()) {
            String string2 = (String)iterator2.next();
            if (this.checkForVerb(list, string2)) continue;
            list.add(new CommandInfo(string2, (String)((List)map.get(string2)).get(0)));
        }
        return;
    }

    private boolean checkForVerb(List object, String string2) {
        object = object.iterator();
        do {
            if (object.hasNext()) continue;
            return false;
        } while (!((CommandInfo)object.next()).getCommandName().equals(string2));
        return true;
    }

    private DataContentHandler getDataContentHandler(String string2) {
        Object object;
        if (LogSupport.isLoggable()) {
            LogSupport.log("    got content-handler");
        }
        if (LogSupport.isLoggable()) {
            object = new StringBuilder("      class ");
            ((StringBuilder)object).append(string2);
            LogSupport.log(((StringBuilder)object).toString());
        }
        try {
            ClassLoader classLoader = SecuritySupport.getContextClassLoader();
            object = classLoader;
            if (classLoader == null) {
                object = this.getClass().getClassLoader();
            }
            try {
                object = ((ClassLoader)object).loadClass(string2);
            }
            catch (Exception exception) {
                object = Class.forName(string2);
            }
            if (object == null) return null;
            return (DataContentHandler)((Class)object).newInstance();
        }
        catch (InstantiationException instantiationException) {
            if (!LogSupport.isLoggable()) return null;
            object = new StringBuilder("Can't load DCH ");
            ((StringBuilder)object).append(string2);
            LogSupport.log(((StringBuilder)object).toString(), instantiationException);
            return null;
        }
        catch (ClassNotFoundException classNotFoundException) {
            if (!LogSupport.isLoggable()) return null;
            StringBuilder stringBuilder = new StringBuilder("Can't load DCH ");
            stringBuilder.append(string2);
            LogSupport.log(stringBuilder.toString(), classNotFoundException);
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            if (!LogSupport.isLoggable()) return null;
            StringBuilder stringBuilder = new StringBuilder("Can't load DCH ");
            stringBuilder.append(string2);
            LogSupport.log(stringBuilder.toString(), illegalAccessException);
        }
        return null;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void loadAllResources(List var1_1, String var2_2) {
        block33 : {
            var3_3 = false;
            var4_4 = false;
            var5_5 = 0;
            try {
                var7_8 = var6_6 = SecuritySupport.getContextClassLoader();
                if (var6_6 == null) {
                    var7_8 = this.getClass().getClassLoader();
                }
                if ((var8_15 = var7_8 != null ? SecuritySupport.getResources((ClassLoader)var7_8, (String)var2_2) : SecuritySupport.getSystemResources((String)var2_2)) == null) break block33;
                if (LogSupport.isLoggable()) {
                    LogSupport.log("MailcapCommandMap: getResources");
                }
                var3_3 = false;
            }
            catch (Exception var7_11) {
                // empty catch block
                ** GOTO lbl178
            }
            do {
                block41 : {
                    block39 : {
                        block36 : {
                            block38 : {
                                block37 : {
                                    block35 : {
                                        block34 : {
                                            var9_16 = var3_3;
                                            if (var5_5 >= var8_15.length) {
                                                var4_4 = var3_3;
                                                break;
                                            }
                                            var10_17 = var8_15[var5_5];
                                            var11_18 = null;
                                            var12_26 = null;
                                            var6_6 = null;
                                            var9_16 = var3_3;
                                            if (LogSupport.isLoggable()) {
                                                var9_16 = var3_3;
                                                var9_16 = var3_3;
                                                var7_8 = new StringBuilder("MailcapCommandMap: URL ");
                                                var9_16 = var3_3;
                                                var7_8.append(var10_17);
                                                var9_16 = var3_3;
                                                LogSupport.log(var7_8.toString());
                                            }
                                            var9_16 = true;
                                            var4_4 = true;
                                            var7_8 = SecuritySupport.openStream(var10_17);
                                            if (var7_8 == null) break block34;
                                            var6_6 = var7_8;
                                            var11_18 = var7_8;
                                            var12_26 = var7_8;
                                            var6_6 = var7_8;
                                            var11_18 = var7_8;
                                            var12_26 = var7_8;
                                            var13_27 = new MailcapFile((InputStream)var7_8);
                                            var6_6 = var7_8;
                                            var11_18 = var7_8;
                                            var12_26 = var7_8;
                                            var1_1.add(var13_27);
                                            try {
                                                if (LogSupport.isLoggable()) {
                                                    var6_6 = new StringBuilder("MailcapCommandMap: successfully loaded mailcap file from URL: ");
                                                    var6_6.append(var10_17);
                                                    LogSupport.log(var6_6.toString());
                                                }
                                                var4_4 = true;
                                                break block35;
                                            }
                                            catch (Throwable var11_19) {
                                                var3_3 = true;
                                                break block36;
                                            }
                                            catch (SecurityException var11_20) {
                                                var3_3 = var4_4;
                                                break block37;
                                            }
                                            catch (IOException var11_21) {
                                                var3_3 = var9_16;
                                                ** GOTO lbl131
                                            }
                                        }
                                        var4_4 = var3_3;
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        if (!LogSupport.isLoggable()) break block35;
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        var13_27 = new StringBuilder("MailcapCommandMap: not loading mailcap file from URL: ");
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        var13_27.append(var10_17);
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        LogSupport.log(var13_27.toString());
                                        var4_4 = var3_3;
                                    }
                                    var3_3 = var4_4;
                                    if (var7_8 == null) break block41;
                                    var9_16 = var4_4;
                                    var7_8.close();
                                    var3_3 = var4_4;
                                    catch (Throwable var11_22) {
                                        var7_8 = var6_6;
                                        break block36;
                                    }
                                    catch (SecurityException var6_7) {
                                        var7_8 = var11_18;
                                        var11_18 = var6_7;
                                    }
                                }
                                var6_6 = var7_8;
                                var4_4 = var3_3;
                                if (LogSupport.isLoggable()) {
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var12_26 = new StringBuilder("MailcapCommandMap: can't load ");
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var12_26.append(var10_17);
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    LogSupport.log(var12_26.toString(), (Throwable)var11_18);
                                }
                                var4_4 = var3_3;
                                if (var7_8 != null) {
                                    break block38;
                                }
                                ** GOTO lbl173
                                catch (IOException var11_23) {
                                    var7_8 = var12_26;
                                }
lbl131: // 2 sources:
                                var6_6 = var7_8;
                                var4_4 = var3_3;
                                if (LogSupport.isLoggable()) {
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var12_26 = new StringBuilder("MailcapCommandMap: can't load ");
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var12_26.append(var10_17);
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    LogSupport.log(var12_26.toString(), (Throwable)var11_18);
                                }
                                var4_4 = var3_3;
                                if (var7_8 == null) ** GOTO lbl173
                            }
                            try {
                                var7_8.close();
                                var4_4 = var3_3;
                                ** GOTO lbl173
                            }
                            catch (Exception var7_9) {
                                break block39;
                            }
                            {
                            }
                            catch (Throwable var11_24) {
                                var3_3 = var4_4;
                                var7_8 = var6_6;
                            }
                        }
                        if (var7_8 == null) ** GOTO lbl170
                        var9_16 = var3_3;
                        try {
                            block42 : {
                                block40 : {
                                    try {
                                        var7_8.close();
                                        break block40;
                                    }
                                    catch (IOException var7_14) {}
                                    catch (IOException var7_13) {
                                        var4_4 = var3_3;
                                    }
                                    break block42;
                                }
                                var9_16 = var3_3;
                                throw var11_25;
                            }
                            var3_3 = var4_4;
                            break block41;
                        }
                        catch (Exception var7_10) {
                            var3_3 = var9_16;
                        }
                    }
                    var4_4 = var3_3;
                    if (!LogSupport.isLoggable()) break;
                    var6_6 = new StringBuilder("MailcapCommandMap: can't load ");
                    var6_6.append((String)var2_2);
                    LogSupport.log(var6_6.toString(), (Throwable)var7_8);
                    var4_4 = var3_3;
                    break;
                    catch (IOException var7_12) {
                        var3_3 = var4_4;
                    }
                }
                ++var5_5;
            } while (true);
        }
        if (var4_4 != false) return;
        if (LogSupport.isLoggable()) {
            LogSupport.log("MailcapCommandMap: !anyLoaded");
        }
        var7_8 = new StringBuilder("/");
        var7_8.append((String)var2_2);
        var2_2 = this.loadResource(var7_8.toString());
        if (var2_2 == null) return;
        var1_1.add(var2_2);
    }

    private MailcapFile loadFile(String object) {
        try {
            MailcapFile mailcapFile = new MailcapFile((String)object);
            return mailcapFile;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private MailcapFile loadResource(String var1_1) {
        block23 : {
            block21 : {
                block24 : {
                    block22 : {
                        block20 : {
                            var2_7 = null;
                            var3_9 = SecuritySupport.getResourceAsStream(this.getClass(), var1_1);
                            if (var3_9 == null) break block20;
                            var2_7 = var3_9;
                            var2_7 = var3_9;
                            var4_10 = new MailcapFile(var3_9);
                            var2_7 = var3_9;
                            if (LogSupport.isLoggable()) {
                                var2_7 = var3_9;
                                var2_7 = var3_9;
                                var5_18 = new StringBuilder("MailcapCommandMap: successfully loaded mailcap file: ");
                                var2_7 = var3_9;
                                var5_18.append(var1_1);
                                var2_7 = var3_9;
                                LogSupport.log(var5_18.toString());
                            }
                            if (var3_9 == null) return var4_10;
                            try {
                                var3_9.close();
                                return var4_10;
                            }
                            catch (IOException var1_5) {
                                return var4_10;
                            }
                        }
                        var2_7 = var3_9;
                        try {
                            if (LogSupport.isLoggable()) {
                                var2_7 = var3_9;
                                var2_7 = var3_9;
                                var4_11 = new StringBuilder("MailcapCommandMap: not loading mailcap file: ");
                                var2_7 = var3_9;
                                var4_11.append(var1_1);
                                var2_7 = var3_9;
                                LogSupport.log(var4_11.toString());
                            }
                            if (var3_9 == null) return null;
                            break block21;
                        }
                        catch (SecurityException var4_12) {
                            break block22;
                        }
                        catch (IOException var4_13) {
                            ** GOTO lbl62
                        }
                        catch (Throwable var1_2) {
                            break block23;
                        }
                        catch (SecurityException var4_14) {
                            var3_9 = null;
                        }
                    }
                    var2_7 = var3_9;
                    if (LogSupport.isLoggable()) {
                        var2_7 = var3_9;
                        var2_7 = var3_9;
                        var5_19 = new StringBuilder("MailcapCommandMap: can't load ");
                        var2_7 = var3_9;
                        var5_19.append(var1_1);
                        var2_7 = var3_9;
                        LogSupport.log(var5_19.toString(), (Throwable)var4_15);
                    }
                    break block24;
                    catch (IOException var4_16) {
                        var3_9 = null;
                    }
lbl62: // 2 sources:
                    var2_7 = var3_9;
                    if (LogSupport.isLoggable()) {
                        var2_7 = var3_9;
                        var2_7 = var3_9;
                        var5_20 = new StringBuilder("MailcapCommandMap: can't load ");
                        var2_7 = var3_9;
                        var5_20.append(var1_1);
                        var2_7 = var3_9;
                        LogSupport.log(var5_20.toString(), (Throwable)var4_17);
                    }
                    if (var3_9 == null) return null;
                }
                if (var3_9 == null) return null;
            }
            try {
                var3_9.close();
                return null;
            }
            catch (IOException var1_6) {
                return null;
            }
            catch (Throwable var1_3) {
                // empty catch block
            }
        }
        if (var2_7 == null) throw var1_4;
        try {
            var2_7.close();
        }
        catch (IOException var2_8) {
            throw var1_4;
        }
        throw var1_4;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    public void addMailcap(String string2) {
        synchronized (this) {
            LogSupport.log("MailcapCommandMap: add to PROG");
            if (this.DB[0] == null) {
                MailcapFile mailcapFile;
                MailcapFile[] arrmailcapFile = this.DB;
                arrmailcapFile[0] = mailcapFile = new MailcapFile();
            }
            this.DB[0].appendToMailcap(string2);
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public DataContentHandler createDataContentHandler(String object) {
        synchronized (this) {
            Object object2;
            if (LogSupport.isLoggable()) {
                object2 = new StringBuilder("MailcapCommandMap: createDataContentHandler for ");
                ((StringBuilder)object2).append((String)object);
                LogSupport.log(((StringBuilder)object2).toString());
            }
            object2 = object;
            if (object != null) {
                object2 = ((String)object).toLowerCase(Locale.ENGLISH);
            }
            int n = 0;
            do {
                if (n >= this.DB.length) break;
                if (this.DB[n] != null) {
                    if (LogSupport.isLoggable()) {
                        object = new StringBuilder("  search DB #");
                        ((StringBuilder)object).append(n);
                        LogSupport.log(((StringBuilder)object).toString());
                    }
                    if ((object = this.DB[n].getMailcapList((String)object2)) != null && (object = (List)object.get("content-handler")) != null && (object = this.getDataContentHandler((String)object.get(0))) != null) {
                        return object;
                    }
                }
                ++n;
            } while (true);
            n = 0;
            int n2;
            while (n < (n2 = this.DB.length)) {
                if (this.DB[n] != null) {
                    if (LogSupport.isLoggable()) {
                        object = new StringBuilder("  search fallback DB #");
                        ((StringBuilder)object).append(n);
                        LogSupport.log(((StringBuilder)object).toString());
                    }
                    if ((object = this.DB[n].getMailcapFallbackList((String)object2)) != null && (object = (List)object.get("content-handler")) != null && (object = this.getDataContentHandler((String)object.get(0))) != null) {
                        return object;
                    }
                }
                ++n;
            }
            return null;
        }
    }

    @Override
    public CommandInfo[] getAllCommands(String object) {
        synchronized (this) {
            ArrayList arrayList = new ArrayList();
            String string2 = object;
            if (object != null) {
                string2 = object.toLowerCase(Locale.ENGLISH);
            }
            int n = 0;
            int n2 = 0;
            do {
                if (n2 >= this.DB.length) break;
                if (this.DB[n2] != null && (object = this.DB[n2].getMailcapList(string2)) != null) {
                    this.appendCmdsToList((Map)object, arrayList);
                }
                ++n2;
            } while (true);
            n2 = n;
            do {
                if (n2 >= this.DB.length) {
                    return arrayList.toArray(new CommandInfo[arrayList.size()]);
                }
                if (this.DB[n2] != null && (object = this.DB[n2].getMailcapFallbackList(string2)) != null) {
                    this.appendCmdsToList((Map)object, arrayList);
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public CommandInfo getCommand(String var1_1, String var2_3) {
        // MONITORENTER : this
        var3_4 = var1_1;
        if (var1_1 == null) ** GOTO lbl6
        var3_4 = var1_1.toLowerCase(Locale.ENGLISH);
lbl6: // 2 sources:
        var4_5 = 0;
        do {
            if (var4_5 >= this.DB.length) {
                break;
            }
            if (this.DB[var4_5] != null && (var1_1 = this.DB[var4_5].getMailcapList((String)var3_4)) != null && (var1_1 = (List)var1_1.get(var2_3)) != null && (var1_1 = (String)var1_1.get(0)) != null) {
                var1_1 = new CommandInfo(var2_3, (String)var1_1);
                // MONITOREXIT : this
                return var1_1;
            }
            ++var4_5;
        } while (true);
        for (var4_5 = 0; var4_5 < (var5_6 = this.DB.length); ++var4_5) {
            if (this.DB[var4_5] == null || (var1_1 = this.DB[var4_5].getMailcapFallbackList((String)var3_4)) == null || (var1_1 = (List)var1_1.get(var2_3)) == null || (var1_1 = (String)var1_1.get(0)) == null) continue;
            var1_1 = new CommandInfo(var2_3, (String)var1_1);
            // MONITOREXIT : this
            return var1_1;
        }
        // MONITOREXIT : this
        return null;
    }

    @Override
    public String[] getMimeTypes() {
        synchronized (this) {
            String[] arrstring = new ArrayList();
            int n = 0;
            do {
                String[] arrstring2;
                if (n >= this.DB.length) {
                    return arrstring.toArray(new String[arrstring.size()]);
                }
                if (this.DB[n] != null && (arrstring2 = this.DB[n].getMimeTypes()) != null) {
                    for (int i = 0; i < arrstring2.length; ++i) {
                        if (arrstring.contains(arrstring2[i])) continue;
                        arrstring.add(arrstring2[i]);
                    }
                }
                ++n;
            } while (true);
        }
    }

    public String[] getNativeCommands(String arrstring) {
        synchronized (this) {
            ArrayList<String> arrayList = new ArrayList<String>();
            String[] arrstring2 = arrstring;
            if (arrstring != null) {
                arrstring2 = arrstring.toLowerCase(Locale.ENGLISH);
            }
            int n = 0;
            do {
                if (n >= this.DB.length) {
                    return arrayList.toArray(new String[arrayList.size()]);
                }
                if (this.DB[n] != null && (arrstring = this.DB[n].getNativeCommands((String)arrstring2)) != null) {
                    for (int i = 0; i < arrstring.length; ++i) {
                        if (arrayList.contains(arrstring[i])) continue;
                        arrayList.add(arrstring[i]);
                    }
                }
                ++n;
            } while (true);
        }
    }

    @Override
    public CommandInfo[] getPreferredCommands(String object) {
        synchronized (this) {
            ArrayList arrayList = new ArrayList();
            String string2 = object;
            if (object != null) {
                string2 = object.toLowerCase(Locale.ENGLISH);
            }
            int n = 0;
            int n2 = 0;
            do {
                if (n2 >= this.DB.length) break;
                if (this.DB[n2] != null && (object = this.DB[n2].getMailcapList(string2)) != null) {
                    this.appendPrefCmdsToList((Map)object, arrayList);
                }
                ++n2;
            } while (true);
            n2 = n;
            do {
                if (n2 >= this.DB.length) {
                    return arrayList.toArray(new CommandInfo[arrayList.size()]);
                }
                if (this.DB[n2] != null && (object = this.DB[n2].getMailcapFallbackList(string2)) != null) {
                    this.appendPrefCmdsToList((Map)object, arrayList);
                }
                ++n2;
            } while (true);
        }
    }
}

