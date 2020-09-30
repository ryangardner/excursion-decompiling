package com.google.android.material.theme;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.app.AppCompatViewInflater;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

public class MaterialComponentsViewInflater extends AppCompatViewInflater {
   protected AppCompatAutoCompleteTextView createAutoCompleteTextView(Context var1, AttributeSet var2) {
      return new MaterialAutoCompleteTextView(var1, var2);
   }

   protected AppCompatButton createButton(Context var1, AttributeSet var2) {
      return new MaterialButton(var1, var2);
   }

   protected AppCompatCheckBox createCheckBox(Context var1, AttributeSet var2) {
      return new MaterialCheckBox(var1, var2);
   }

   protected AppCompatRadioButton createRadioButton(Context var1, AttributeSet var2) {
      return new MaterialRadioButton(var1, var2);
   }

   protected AppCompatTextView createTextView(Context var1, AttributeSet var2) {
      return new MaterialTextView(var1, var2);
   }
}
