package com.syntak.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class CheckBoxForDB extends CheckBox {
   public CheckBoxForDB(Context var1) {
      super(var1);
   }

   public CheckBoxForDB(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public CheckBoxForDB(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
      if (var1.toString().compareTo("") != 0) {
         boolean var5;
         if (var1.toString().compareTo("1") == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.setChecked(var5);
         this.setText("");
      }

   }
}
