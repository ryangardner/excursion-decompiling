package com.syntak.library;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ClearableEditText extends RelativeLayout {
   Button btn_clear;
   EditText edit_text;
   LayoutInflater inflater = null;

   public ClearableEditText(Context var1) {
      super(var1);
      this.initViews();
   }

   public ClearableEditText(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.initViews();
   }

   public ClearableEditText(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.initViews();
   }

   void clearText() {
      this.btn_clear.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            ClearableEditText.this.edit_text.setText("");
         }
      });
   }

   public Editable getText() {
      return this.edit_text.getText();
   }

   public float getTextSize() {
      return this.edit_text.getTextSize();
   }

   void initViews() {
      LayoutInflater var1 = (LayoutInflater)this.getContext().getSystemService("layout_inflater");
      this.inflater = var1;
      var1.inflate(R.layout.clearable_edittext, this, true);
      this.edit_text = (EditText)this.findViewById(R.id.clearable_edit);
      Button var2 = (Button)this.findViewById(R.id.clearable_button_clear);
      this.btn_clear = var2;
      var2.setVisibility(4);
      this.clearText();
      this.showHideClearButton();
   }

   public void setNumericOnly() {
      this.edit_text.setInputType(2);
   }

   public void setSelectAllOnFocus(boolean var1) {
      this.edit_text.setSelectAllOnFocus(true);
   }

   public void setText(String var1) {
      this.edit_text.setText(var1);
   }

   void showHideClearButton() {
      this.edit_text.addTextChangedListener(new TextWatcher() {
         public void afterTextChanged(Editable var1) {
         }

         public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }

         public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
            if (var1.length() > 0) {
               ClearableEditText.this.btn_clear.setVisibility(0);
            } else {
               ClearableEditText.this.btn_clear.setVisibility(4);
            }

         }
      });
   }
}
