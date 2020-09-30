package androidx.recyclerview.widget;

import java.util.List;

public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
   final AsyncListDiffer<T> mDiffer;
   private final AsyncListDiffer.ListListener<T> mListener = new AsyncListDiffer.ListListener<T>() {
      public void onCurrentListChanged(List<T> var1, List<T> var2) {
         ListAdapter.this.onCurrentListChanged(var1, var2);
      }
   };

   protected ListAdapter(AsyncDifferConfig<T> var1) {
      AsyncListDiffer var2 = new AsyncListDiffer(new AdapterListUpdateCallback(this), var1);
      this.mDiffer = var2;
      var2.addListListener(this.mListener);
   }

   protected ListAdapter(DiffUtil.ItemCallback<T> var1) {
      AsyncListDiffer var2 = new AsyncListDiffer(new AdapterListUpdateCallback(this), (new AsyncDifferConfig.Builder(var1)).build());
      this.mDiffer = var2;
      var2.addListListener(this.mListener);
   }

   public List<T> getCurrentList() {
      return this.mDiffer.getCurrentList();
   }

   protected T getItem(int var1) {
      return this.mDiffer.getCurrentList().get(var1);
   }

   public int getItemCount() {
      return this.mDiffer.getCurrentList().size();
   }

   public void onCurrentListChanged(List<T> var1, List<T> var2) {
   }

   public void submitList(List<T> var1) {
      this.mDiffer.submitList(var1);
   }

   public void submitList(List<T> var1, Runnable var2) {
      this.mDiffer.submitList(var1, var2);
   }
}
