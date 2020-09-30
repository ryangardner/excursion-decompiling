/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Size
 *  android.util.SizeF
 *  android.util.SparseArray
 */
package androidx.lifecycle;

import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.lifecycle.MutableLiveData;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class SavedStateHandle {
    private static final Class[] ACCEPTABLE_CLASSES;
    private static final String KEYS = "keys";
    private static final String VALUES = "values";
    private final Map<String, SavingStateLiveData<?>> mLiveDatas = new HashMap();
    final Map<String, Object> mRegular;
    private final SavedStateRegistry.SavedStateProvider mSavedStateProvider = new SavedStateRegistry.SavedStateProvider(){

        @Override
        public Bundle saveState() {
            Object object = SavedStateHandle.this.mRegular.keySet();
            ArrayList<String> arrayList = new ArrayList<String>(object.size());
            ArrayList<Object> arrayList2 = new ArrayList<Object>(arrayList.size());
            object = object.iterator();
            do {
                if (!object.hasNext()) {
                    object = new Bundle();
                    object.putParcelableArrayList(SavedStateHandle.KEYS, arrayList);
                    object.putParcelableArrayList(SavedStateHandle.VALUES, arrayList2);
                    return object;
                }
                String string2 = (String)object.next();
                arrayList.add(string2);
                arrayList2.add(SavedStateHandle.this.mRegular.get(string2));
            } while (true);
        }
    };

    static {
        Class<Boolean> class_ = Boolean.TYPE;
        Class<Double> class_2 = Double.TYPE;
        Class<Integer> class_3 = Integer.TYPE;
        Class<Long> class_4 = Long.TYPE;
        Class<Byte> class_5 = Byte.TYPE;
        Class<Character> class_6 = Character.TYPE;
        Class<Float> class_7 = Float.TYPE;
        Class<Short> class_8 = Short.TYPE;
        Class class_9 = Build.VERSION.SDK_INT >= 21 ? Size.class : Integer.TYPE;
        Class class_10 = Build.VERSION.SDK_INT >= 21 ? SizeF.class : Integer.TYPE;
        ACCEPTABLE_CLASSES = new Class[]{class_, boolean[].class, class_2, double[].class, class_3, int[].class, class_4, long[].class, String.class, String[].class, Binder.class, Bundle.class, class_5, byte[].class, class_6, char[].class, CharSequence.class, CharSequence[].class, ArrayList.class, class_7, float[].class, Parcelable.class, Parcelable[].class, Serializable.class, class_8, short[].class, SparseArray.class, class_9, class_10};
    }

    public SavedStateHandle() {
        this.mRegular = new HashMap<String, Object>();
    }

    public SavedStateHandle(Map<String, Object> map) {
        this.mRegular = new HashMap<String, Object>(map);
    }

    static SavedStateHandle createHandle(Bundle object, Bundle object2) {
        if (object == null && object2 == null) {
            return new SavedStateHandle();
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (object2 != null) {
            for (String string2 : object2.keySet()) {
                hashMap.put(string2, object2.get(string2));
            }
        }
        if (object == null) {
            return new SavedStateHandle(hashMap);
        }
        object2 = object.getParcelableArrayList(KEYS);
        object = object.getParcelableArrayList(VALUES);
        if (object2 == null) throw new IllegalStateException("Invalid bundle passed as restored state");
        if (object == null) throw new IllegalStateException("Invalid bundle passed as restored state");
        if (((ArrayList)object2).size() != ((ArrayList)object).size()) throw new IllegalStateException("Invalid bundle passed as restored state");
        int n = 0;
        while (n < ((ArrayList)object2).size()) {
            hashMap.put((String)((ArrayList)object2).get(n), ((ArrayList)object).get(n));
            ++n;
        }
        return new SavedStateHandle(hashMap);
    }

    private <T> MutableLiveData<T> getLiveDataInternal(String string2, boolean bl, T object) {
        MutableLiveData mutableLiveData = this.mLiveDatas.get(string2);
        if (mutableLiveData != null) {
            return mutableLiveData;
        }
        object = this.mRegular.containsKey(string2) ? new SavingStateLiveData<Object>(this, string2, this.mRegular.get(string2)) : (bl ? new SavingStateLiveData<T>(this, string2, object) : new SavingStateLiveData(this, string2));
        this.mLiveDatas.put(string2, (SavingStateLiveData<?>)object);
        return object;
    }

    private static void validateValue(Object object) {
        if (object == null) {
            return;
        }
        Object object2 = ACCEPTABLE_CLASSES;
        int n = ((Class[])object2).length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Can't put value with type ");
                ((StringBuilder)object2).append(object.getClass());
                ((StringBuilder)object2).append(" into saved state");
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }
            if (object2[n2].isInstance(object)) {
                return;
            }
            ++n2;
        } while (true);
    }

    public boolean contains(String string2) {
        return this.mRegular.containsKey(string2);
    }

    public <T> T get(String string2) {
        return (T)this.mRegular.get(string2);
    }

    public <T> MutableLiveData<T> getLiveData(String string2) {
        return this.getLiveDataInternal(string2, false, null);
    }

    public <T> MutableLiveData<T> getLiveData(String string2, T t) {
        return this.getLiveDataInternal(string2, true, t);
    }

    public Set<String> keys() {
        return Collections.unmodifiableSet(this.mRegular.keySet());
    }

    public <T> T remove(String object) {
        Object object2 = this.mRegular.remove(object);
        if ((object = this.mLiveDatas.remove(object)) == null) return (T)object2;
        ((SavingStateLiveData)object).detach();
        return (T)object2;
    }

    SavedStateRegistry.SavedStateProvider savedStateProvider() {
        return this.mSavedStateProvider;
    }

    public <T> void set(String string2, T t) {
        SavedStateHandle.validateValue(t);
        MutableLiveData mutableLiveData = this.mLiveDatas.get(string2);
        if (mutableLiveData != null) {
            mutableLiveData.setValue(t);
            return;
        }
        this.mRegular.put(string2, t);
    }

    static class SavingStateLiveData<T>
    extends MutableLiveData<T> {
        private SavedStateHandle mHandle;
        private String mKey;

        SavingStateLiveData(SavedStateHandle savedStateHandle, String string2) {
            this.mKey = string2;
            this.mHandle = savedStateHandle;
        }

        SavingStateLiveData(SavedStateHandle savedStateHandle, String string2, T t) {
            super(t);
            this.mKey = string2;
            this.mHandle = savedStateHandle;
        }

        void detach() {
            this.mHandle = null;
        }

        @Override
        public void setValue(T t) {
            SavedStateHandle savedStateHandle = this.mHandle;
            if (savedStateHandle != null) {
                savedStateHandle.mRegular.put(this.mKey, t);
            }
            super.setValue(t);
        }
    }

}

