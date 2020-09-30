/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.FolderEvent;

public interface FolderListener
extends EventListener {
    public void folderCreated(FolderEvent var1);

    public void folderDeleted(FolderEvent var1);

    public void folderRenamed(FolderEvent var1);
}

