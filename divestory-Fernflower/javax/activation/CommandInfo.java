package javax.activation;

import java.beans.Beans;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class CommandInfo {
   private String className;
   private String verb;

   public CommandInfo(String var1, String var2) {
      this.verb = var1;
      this.className = var2;
   }

   public String getCommandClass() {
      return this.className;
   }

   public String getCommandName() {
      return this.verb;
   }

   public Object getCommandObject(DataHandler var1, ClassLoader var2) throws IOException, ClassNotFoundException {
      Object var4 = Beans.instantiate(var2, this.className);
      if (var4 != null) {
         if (var4 instanceof CommandObject) {
            ((CommandObject)var4).setCommandContext(this.verb, var1);
         } else if (var4 instanceof Externalizable && var1 != null) {
            InputStream var3 = var1.getInputStream();
            if (var3 != null) {
               ((Externalizable)var4).readExternal(new ObjectInputStream(var3));
            }
         }
      }

      return var4;
   }
}
