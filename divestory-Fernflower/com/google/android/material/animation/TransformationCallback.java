package com.google.android.material.animation;

import android.view.View;

public interface TransformationCallback<T extends View> {
   void onScaleChanged(T var1);

   void onTranslationChanged(T var1);
}
