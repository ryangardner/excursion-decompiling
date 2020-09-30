/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

public class Quota {
    public String quotaRoot;
    public Resource[] resources;

    public Quota(String string2) {
        this.quotaRoot = string2;
    }

    public void setResourceLimit(String string2, long l) {
        if (this.resources == null) {
            Resource[] arrresource = new Resource[1];
            this.resources = arrresource;
            arrresource[0] = new Resource(string2, 0L, l);
            return;
        }
        int n = 0;
        do {
            Resource[] arrresource;
            if (n >= (arrresource = this.resources).length) {
                n = arrresource.length + 1;
                Resource[] arrresource2 = new Resource[n];
                System.arraycopy(arrresource, 0, arrresource2, 0, arrresource.length);
                arrresource2[n - 1] = new Resource(string2, 0L, l);
                this.resources = arrresource2;
                return;
            }
            if (arrresource[n].name.equalsIgnoreCase(string2)) {
                this.resources[n].limit = l;
                return;
            }
            ++n;
        } while (true);
    }

    public static class Resource {
        public long limit;
        public String name;
        public long usage;

        public Resource(String string2, long l, long l2) {
            this.name = string2;
            this.usage = l;
            this.limit = l2;
        }
    }

}

