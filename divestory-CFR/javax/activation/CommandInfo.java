/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.beans.Beans;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import javax.activation.CommandObject;
import javax.activation.DataHandler;

public class CommandInfo {
    private String className;
    private String verb;

    public CommandInfo(String string2, String string3) {
        this.verb = string2;
        this.className = string3;
    }

    public String getCommandClass() {
        return this.className;
    }

    public String getCommandName() {
        return this.verb;
    }

    public Object getCommandObject(DataHandler object, ClassLoader object2) throws IOException, ClassNotFoundException {
        if ((object2 = Beans.instantiate((ClassLoader)object2, this.className)) == null) return object2;
        if (object2 instanceof CommandObject) {
            ((CommandObject)object2).setCommandContext(this.verb, (DataHandler)object);
            return object2;
        }
        if (!(object2 instanceof Externalizable)) return object2;
        if (object == null) return object2;
        if ((object = ((DataHandler)object).getInputStream()) == null) return object2;
        ((Externalizable)object2).readExternal(new ObjectInputStream((InputStream)object));
        return object2;
    }
}

