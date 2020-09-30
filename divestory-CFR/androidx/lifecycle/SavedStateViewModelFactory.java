/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.os.Bundle
 */
package androidx.lifecycle;

import android.app.Application;
import android.os.Bundle;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateHandleController;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public final class SavedStateViewModelFactory
extends ViewModelProvider.KeyedFactory {
    private static final Class<?>[] ANDROID_VIEWMODEL_SIGNATURE = new Class[]{Application.class, SavedStateHandle.class};
    private static final Class<?>[] VIEWMODEL_SIGNATURE = new Class[]{SavedStateHandle.class};
    private final Application mApplication;
    private final Bundle mDefaultArgs;
    private final ViewModelProvider.AndroidViewModelFactory mFactory;
    private final Lifecycle mLifecycle;
    private final SavedStateRegistry mSavedStateRegistry;

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner savedStateRegistryOwner) {
        this(application, savedStateRegistryOwner, null);
    }

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner savedStateRegistryOwner, Bundle bundle) {
        this.mSavedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
        this.mLifecycle = savedStateRegistryOwner.getLifecycle();
        this.mDefaultArgs = bundle;
        this.mApplication = application;
        this.mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
    }

    private static <T> Constructor<T> findMatchingConstructor(Class<T> arrconstructor, Class<?>[] arrclass) {
        arrconstructor = arrconstructor.getConstructors();
        int n = arrconstructor.length;
        int n2 = 0;
        while (n2 < n) {
            Constructor<?> constructor = arrconstructor[n2];
            if (Arrays.equals(arrclass, constructor.getParameterTypes())) {
                return constructor;
            }
            ++n2;
        }
        return null;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> class_) {
        String string2 = class_.getCanonicalName();
        if (string2 == null) throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        return this.create(string2, class_);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public <T extends ViewModel> T create(String var1_1, Class<T> var2_4) {
        var3_5 = AndroidViewModel.class.isAssignableFrom(var2_4);
        var4_6 = var3_5 != false ? SavedStateViewModelFactory.findMatchingConstructor(var2_4, SavedStateViewModelFactory.ANDROID_VIEWMODEL_SIGNATURE) : SavedStateViewModelFactory.findMatchingConstructor(var2_4, SavedStateViewModelFactory.VIEWMODEL_SIGNATURE);
        if (var4_6 == null) {
            return this.mFactory.create(var2_4);
        }
        var5_8 = SavedStateHandleController.create(this.mSavedStateRegistry, this.mLifecycle, (String)var1_1, this.mDefaultArgs);
        if (!var3_5) ** GOTO lbl10
        try {
            block5 : {
                var1_1 = (ViewModel)var4_6.newInstance(new Object[]{this.mApplication, var5_8.getHandle()});
                break block5;
lbl10: // 1 sources:
                var1_1 = (ViewModel)var4_6.newInstance(new Object[]{var5_8.getHandle()});
            }
            var1_1.setTagIfAbsent("androidx.lifecycle.savedstate.vm.tag", var5_8);
        }
        catch (InvocationTargetException var1_2) {
            var4_6 = new StringBuilder();
            var4_6.append("An exception happened in constructor of ");
            var4_6.append(var2_4);
            throw new RuntimeException(var4_6.toString(), var1_2.getCause());
        }
        catch (InstantiationException var1_3) {
            var4_6 = new StringBuilder();
            var4_6.append("A ");
            var4_6.append(var2_4);
            var4_6.append(" cannot be instantiated.");
            throw new RuntimeException(var4_6.toString(), var1_3);
        }
        catch (IllegalAccessException var4_7) {
            var1_1 = new StringBuilder();
            var1_1.append("Failed to access ");
            var1_1.append(var2_4);
            throw new RuntimeException(var1_1.toString(), var4_7);
        }
        return (T)var1_1;
    }

    @Override
    void onRequery(ViewModel viewModel) {
        SavedStateHandleController.attachHandleIfNeeded(viewModel, this.mSavedStateRegistry, this.mLifecycle);
    }
}

