/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.recyclerview.widget;

public interface ListUpdateCallback {
    public void onChanged(int var1, int var2, Object var3);

    public void onInserted(int var1, int var2);

    public void onMoved(int var1, int var2);

    public void onRemoved(int var1, int var2);
}

