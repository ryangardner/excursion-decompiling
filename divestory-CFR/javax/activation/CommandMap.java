/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import javax.activation.CommandInfo;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.activation.MailcapCommandMap;

public abstract class CommandMap {
    private static CommandMap defaultCommandMap;

    public static CommandMap getDefaultCommandMap() {
        if (defaultCommandMap != null) return defaultCommandMap;
        defaultCommandMap = new MailcapCommandMap();
        return defaultCommandMap;
    }

    public static void setDefaultCommandMap(CommandMap commandMap) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkSetFactory();
            }
            catch (SecurityException securityException) {
                if (CommandMap.class.getClassLoader() != commandMap.getClass().getClassLoader()) throw securityException;
            }
        }
        defaultCommandMap = commandMap;
    }

    public abstract DataContentHandler createDataContentHandler(String var1);

    public DataContentHandler createDataContentHandler(String string2, DataSource dataSource) {
        return this.createDataContentHandler(string2);
    }

    public abstract CommandInfo[] getAllCommands(String var1);

    public CommandInfo[] getAllCommands(String string2, DataSource dataSource) {
        return this.getAllCommands(string2);
    }

    public abstract CommandInfo getCommand(String var1, String var2);

    public CommandInfo getCommand(String string2, String string3, DataSource dataSource) {
        return this.getCommand(string2, string3);
    }

    public String[] getMimeTypes() {
        return null;
    }

    public abstract CommandInfo[] getPreferredCommands(String var1);

    public CommandInfo[] getPreferredCommands(String string2, DataSource dataSource) {
        return this.getPreferredCommands(string2);
    }
}

