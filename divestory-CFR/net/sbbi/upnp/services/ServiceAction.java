/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sbbi.upnp.services.ServiceActionArgument;
import net.sbbi.upnp.services.UPNPService;

public class ServiceAction {
    protected String name;
    private List orderedActionArguments;
    private List orderedInputActionArguments;
    private List orderedInputActionArgumentsNames;
    private List orderedOutputActionArguments;
    private List orderedOutputActionArgumentsNames;
    protected UPNPService parent;

    protected ServiceAction() {
    }

    private List getListForActionArgument(List arrayList, String string2) {
        Object var3_3 = null;
        if (arrayList == null) {
            return null;
        }
        ArrayList<ServiceActionArgument> arrayList2 = new ArrayList<ServiceActionArgument>();
        arrayList = arrayList.iterator();
        do {
            if (!arrayList.hasNext()) {
                if (arrayList2.size() != 0) return arrayList2;
                return var3_3;
            }
            ServiceActionArgument serviceActionArgument = (ServiceActionArgument)arrayList.next();
            if (serviceActionArgument.getDirection() != string2) continue;
            arrayList2.add(serviceActionArgument);
        } while (true);
    }

    private List getListForActionArgumentNames(List object, String string2) {
        Object var3_3 = null;
        if (object == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator iterator2 = object.iterator();
        do {
            if (!iterator2.hasNext()) {
                if (arrayList.size() != 0) return arrayList;
                return var3_3;
            }
            object = (ServiceActionArgument)iterator2.next();
            if (((ServiceActionArgument)object).getDirection() != string2) continue;
            arrayList.add(((ServiceActionArgument)object).getName());
        } while (true);
    }

    public ServiceActionArgument getActionArgument(String string2) {
        ServiceActionArgument serviceActionArgument;
        Object object = this.orderedActionArguments;
        if (object == null) {
            return null;
        }
        object = object.iterator();
        do {
            if (object.hasNext()) continue;
            return null;
        } while (!(serviceActionArgument = (ServiceActionArgument)object.next()).getName().equals(string2));
        return serviceActionArgument;
    }

    public List getActionArguments() {
        return this.orderedActionArguments;
    }

    public ServiceActionArgument getInputActionArgument(String string2) {
        ServiceActionArgument serviceActionArgument;
        Object object = this.orderedInputActionArguments;
        if (object == null) {
            return null;
        }
        object = object.iterator();
        do {
            if (object.hasNext()) continue;
            return null;
        } while (!(serviceActionArgument = (ServiceActionArgument)object.next()).getName().equals(string2));
        return serviceActionArgument;
    }

    public List getInputActionArguments() {
        return this.orderedInputActionArguments;
    }

    public List getInputActionArgumentsNames() {
        return this.orderedInputActionArgumentsNames;
    }

    public String getName() {
        return this.name;
    }

    public ServiceActionArgument getOutputActionArgument(String string2) {
        ServiceActionArgument serviceActionArgument;
        Object object = this.orderedOutputActionArguments;
        if (object == null) {
            return null;
        }
        object = object.iterator();
        do {
            if (object.hasNext()) continue;
            return null;
        } while (!(serviceActionArgument = (ServiceActionArgument)object.next()).getName().equals(string2));
        return serviceActionArgument;
    }

    public List getOutputActionArguments() {
        return this.orderedOutputActionArguments;
    }

    public List getOutputActionArgumentsNames() {
        return this.orderedOutputActionArgumentsNames;
    }

    public UPNPService getParent() {
        return this.parent;
    }

    protected void setActionArguments(List list) {
        this.orderedActionArguments = list;
        this.orderedInputActionArguments = this.getListForActionArgument(list, "in");
        this.orderedOutputActionArguments = this.getListForActionArgument(list, "out");
        this.orderedInputActionArgumentsNames = this.getListForActionArgumentNames(list, "in");
        this.orderedOutputActionArgumentsNames = this.getListForActionArgumentNames(list, "out");
    }
}

