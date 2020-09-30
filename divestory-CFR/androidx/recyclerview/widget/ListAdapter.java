/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder>
extends RecyclerView.Adapter<VH> {
    final AsyncListDiffer<T> mDiffer;
    private final AsyncListDiffer.ListListener<T> mListener = new AsyncListDiffer.ListListener<T>(){

        @Override
        public void onCurrentListChanged(List<T> list, List<T> list2) {
            ListAdapter.this.onCurrentListChanged(list, list2);
        }
    };

    protected ListAdapter(AsyncDifferConfig<T> object) {
        this.mDiffer = object = new AsyncListDiffer<T>(new AdapterListUpdateCallback(this), (AsyncDifferConfig<T>)object);
        ((AsyncListDiffer)object).addListListener(this.mListener);
    }

    protected ListAdapter(DiffUtil.ItemCallback<T> object) {
        this.mDiffer = object = new AsyncListDiffer<T>(new AdapterListUpdateCallback(this), new AsyncDifferConfig.Builder<T>((DiffUtil.ItemCallback<T>)object).build());
        ((AsyncListDiffer)object).addListListener(this.mListener);
    }

    public List<T> getCurrentList() {
        return this.mDiffer.getCurrentList();
    }

    protected T getItem(int n) {
        return this.mDiffer.getCurrentList().get(n);
    }

    @Override
    public int getItemCount() {
        return this.mDiffer.getCurrentList().size();
    }

    public void onCurrentListChanged(List<T> list, List<T> list2) {
    }

    public void submitList(List<T> list) {
        this.mDiffer.submitList(list);
    }

    public void submitList(List<T> list, Runnable runnable2) {
        this.mDiffer.submitList(list, runnable2);
    }

}

