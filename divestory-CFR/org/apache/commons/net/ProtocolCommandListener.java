/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net;

import java.util.EventListener;
import org.apache.commons.net.ProtocolCommandEvent;

public interface ProtocolCommandListener
extends EventListener {
    public void protocolCommandSent(ProtocolCommandEvent var1);

    public void protocolReplyReceived(ProtocolCommandEvent var1);
}

