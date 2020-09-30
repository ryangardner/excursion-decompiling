/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

public abstract class RemovalCause
extends Enum<RemovalCause> {
    private static final /* synthetic */ RemovalCause[] $VALUES;
    public static final /* enum */ RemovalCause COLLECTED;
    public static final /* enum */ RemovalCause EXPIRED;
    public static final /* enum */ RemovalCause EXPLICIT;
    public static final /* enum */ RemovalCause REPLACED;
    public static final /* enum */ RemovalCause SIZE;

    static {
        RemovalCause removalCause;
        EXPLICIT = new RemovalCause(){

            @Override
            boolean wasEvicted() {
                return false;
            }
        };
        REPLACED = new RemovalCause(){

            @Override
            boolean wasEvicted() {
                return false;
            }
        };
        COLLECTED = new RemovalCause(){

            @Override
            boolean wasEvicted() {
                return true;
            }
        };
        EXPIRED = new RemovalCause(){

            @Override
            boolean wasEvicted() {
                return true;
            }
        };
        SIZE = removalCause = new RemovalCause(){

            @Override
            boolean wasEvicted() {
                return true;
            }
        };
        $VALUES = new RemovalCause[]{EXPLICIT, REPLACED, COLLECTED, EXPIRED, removalCause};
    }

    public static RemovalCause valueOf(String string2) {
        return Enum.valueOf(RemovalCause.class, string2);
    }

    public static RemovalCause[] values() {
        return (RemovalCause[])$VALUES.clone();
    }

    abstract boolean wasEvicted();

}

