package androidx.lifecycle;

public class MutableLiveData<T> extends LiveData<T> {
   public MutableLiveData() {
   }

   public MutableLiveData(T var1) {
      super(var1);
   }

   public void postValue(T var1) {
      super.postValue(var1);
   }

   public void setValue(T var1) {
      super.setValue(var1);
   }
}
