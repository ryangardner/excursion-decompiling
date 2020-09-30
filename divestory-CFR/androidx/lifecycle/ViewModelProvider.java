/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package androidx.lifecycle;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewModelProvider {
    private static final String DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey";
    private final Factory mFactory;
    private final ViewModelStore mViewModelStore;

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory2) {
        this.mFactory = factory2;
        this.mViewModelStore = viewModelStore;
    }

    public ViewModelProvider(ViewModelStoreOwner object) {
        ViewModelStore viewModelStore = object.getViewModelStore();
        object = object instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory)object).getDefaultViewModelProviderFactory() : NewInstanceFactory.getInstance();
        this(viewModelStore, (Factory)object);
    }

    public ViewModelProvider(ViewModelStoreOwner viewModelStoreOwner, Factory factory2) {
        this(viewModelStoreOwner.getViewModelStore(), factory2);
    }

    public <T extends ViewModel> T get(Class<T> class_) {
        String string2 = class_.getCanonicalName();
        if (string2 == null) throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("androidx.lifecycle.ViewModelProvider.DefaultKey:");
        stringBuilder.append(string2);
        return this.get(stringBuilder.toString(), class_);
    }

    public <T extends ViewModel> T get(String object, Class<T> class_) {
        Object object2 = this.mViewModelStore.get((String)object);
        if (class_.isInstance(object2)) {
            object = this.mFactory;
            if (!(object instanceof OnRequeryFactory)) return (T)object2;
            ((OnRequeryFactory)object).onRequery((ViewModel)object2);
            return (T)object2;
        }
        object2 = this.mFactory;
        class_ = object2 instanceof KeyedFactory ? ((KeyedFactory)object2).create((String)object, class_) : object2.create(class_);
        this.mViewModelStore.put((String)object, (ViewModel)((Object)class_));
        return (T)class_;
    }

    public static class AndroidViewModelFactory
    extends NewInstanceFactory {
        private static AndroidViewModelFactory sInstance;
        private Application mApplication;

        public AndroidViewModelFactory(Application application) {
            this.mApplication = application;
        }

        public static AndroidViewModelFactory getInstance(Application application) {
            if (sInstance != null) return sInstance;
            sInstance = new AndroidViewModelFactory(application);
            return sInstance;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> class_) {
            ViewModel viewModel;
            if (!AndroidViewModel.class.isAssignableFrom(class_)) return super.create(class_);
            try {
                viewModel = (ViewModel)class_.getConstructor(Application.class).newInstance(new Object[]{this.mApplication});
            }
            catch (InvocationTargetException invocationTargetException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), invocationTargetException);
            }
            catch (InstantiationException instantiationException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), instantiationException);
            }
            catch (IllegalAccessException illegalAccessException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), illegalAccessException);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), noSuchMethodException);
            }
            return (T)viewModel;
        }
    }

    public static interface Factory {
        public <T extends ViewModel> T create(Class<T> var1);
    }

    static abstract class KeyedFactory
    extends OnRequeryFactory
    implements Factory {
        KeyedFactory() {
        }

        @Override
        public <T extends ViewModel> T create(Class<T> class_) {
            throw new UnsupportedOperationException("create(String, Class<?>) must be called on implementaions of KeyedFactory");
        }

        public abstract <T extends ViewModel> T create(String var1, Class<T> var2);
    }

    public static class NewInstanceFactory
    implements Factory {
        private static NewInstanceFactory sInstance;

        static NewInstanceFactory getInstance() {
            if (sInstance != null) return sInstance;
            sInstance = new NewInstanceFactory();
            return sInstance;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> class_) {
            ViewModel viewModel;
            try {
                viewModel = (ViewModel)class_.newInstance();
            }
            catch (IllegalAccessException illegalAccessException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), illegalAccessException);
            }
            catch (InstantiationException instantiationException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), instantiationException);
            }
            return (T)viewModel;
        }
    }

    static class OnRequeryFactory {
        OnRequeryFactory() {
        }

        void onRequery(ViewModel viewModel) {
        }
    }

}

