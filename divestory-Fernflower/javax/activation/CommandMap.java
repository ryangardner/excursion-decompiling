package javax.activation;

public abstract class CommandMap {
   private static CommandMap defaultCommandMap;

   public static CommandMap getDefaultCommandMap() {
      if (defaultCommandMap == null) {
         defaultCommandMap = new MailcapCommandMap();
      }

      return defaultCommandMap;
   }

   public static void setDefaultCommandMap(CommandMap var0) {
      SecurityManager var1 = System.getSecurityManager();
      if (var1 != null) {
         try {
            var1.checkSetFactory();
         } catch (SecurityException var2) {
            if (CommandMap.class.getClassLoader() != var0.getClass().getClassLoader()) {
               throw var2;
            }
         }
      }

      defaultCommandMap = var0;
   }

   public abstract DataContentHandler createDataContentHandler(String var1);

   public DataContentHandler createDataContentHandler(String var1, DataSource var2) {
      return this.createDataContentHandler(var1);
   }

   public abstract CommandInfo[] getAllCommands(String var1);

   public CommandInfo[] getAllCommands(String var1, DataSource var2) {
      return this.getAllCommands(var1);
   }

   public abstract CommandInfo getCommand(String var1, String var2);

   public CommandInfo getCommand(String var1, String var2, DataSource var3) {
      return this.getCommand(var1, var2);
   }

   public String[] getMimeTypes() {
      return null;
   }

   public abstract CommandInfo[] getPreferredCommands(String var1);

   public CommandInfo[] getPreferredCommands(String var1, DataSource var2) {
      return this.getPreferredCommands(var1);
   }
}
