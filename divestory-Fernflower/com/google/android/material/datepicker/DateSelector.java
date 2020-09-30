package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.util.Pair;
import java.util.Collection;

public interface DateSelector<S> extends Parcelable {
   int getDefaultThemeResId(Context var1);

   int getDefaultTitleResId();

   Collection<Long> getSelectedDays();

   Collection<Pair<Long, Long>> getSelectedRanges();

   S getSelection();

   String getSelectionDisplayString(Context var1);

   boolean isSelectionComplete();

   View onCreateTextInputView(LayoutInflater var1, ViewGroup var2, Bundle var3, CalendarConstraints var4, OnSelectionChangedListener<S> var5);

   void select(long var1);

   void setSelection(S var1);
}
