package com.google.android.material.datepicker;

import androidx.fragment.app.Fragment;
import java.util.LinkedHashSet;

abstract class PickerFragment<S> extends Fragment {
   protected final LinkedHashSet<OnSelectionChangedListener<S>> onSelectionChangedListeners = new LinkedHashSet();

   boolean addOnSelectionChangedListener(OnSelectionChangedListener<S> var1) {
      return this.onSelectionChangedListeners.add(var1);
   }

   void clearOnSelectionChangedListeners() {
      this.onSelectionChangedListeners.clear();
   }

   abstract DateSelector<S> getDateSelector();

   boolean removeOnSelectionChangedListener(OnSelectionChangedListener<S> var1) {
      return this.onSelectionChangedListeners.remove(var1);
   }
}
