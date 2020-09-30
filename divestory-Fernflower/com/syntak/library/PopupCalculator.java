package com.syntak.library;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;
import java.text.DecimalFormat;

public class PopupCalculator {
   TextView b0;
   TextView b1;
   TextView b2;
   TextView b3;
   TextView b4;
   TextView b5;
   TextView b6;
   TextView b7;
   TextView b8;
   TextView b9;
   ImageView bBack;
   TextView bCancel;
   TextView bClear;
   TextView bDivide;
   TextView bDot;
   TextView bEqual;
   TextView bMR;
   TextView bMin;
   TextView bMinus;
   TextView bMultiply;
   TextView bOK;
   TextView bPlus;
   Context context;
   DecimalFormat df = new DecimalFormat("0.######");
   EditText editText;
   EditText editTextM;
   boolean flagClearResultForNextDigit = false;
   boolean flagFirstNumber = true;
   boolean flagFloat;
   boolean flagFloatM;
   boolean flagOpPressed;
   float input;
   int location = 80;
   float memory;
   int op;
   PopupWindow popup = null;
   float result;
   float scaleDigit = 1.5F;
   float scaleText = 1.2F;
   String strValue = "0";

   public PopupCalculator(Context var1, View var2, int var3) {
      this.context = var1;
      if (var3 >= 0) {
         this.location = var3;
      }

      this.init(var2, ((LayoutInflater)var1.getSystemService("layout_inflater")).inflate(R.layout.popup_calculator, (ViewGroup)null));
   }

   public PopupCalculator(Context var1, View var2, View var3, int var4) {
      this.context = var1;
      if (var4 >= 0) {
         this.location = var4;
      }

      this.init(var2, var3);
   }

   void BackSpace() {
      Editable var1 = this.editText.getText();
      String var2;
      if (var1 != null) {
         var2 = var1.toString();
      } else {
         var2 = null;
      }

      if (var2 != null && var2.length() > 0) {
         if (var2.length() == 1) {
            this.editText.setText("0");
         } else {
            var2 = var2.substring(0, var2.length() - 1);
            this.editText.setText(var2);
         }

         this.toResult();
         this.OnChanged(this.result);
      }

   }

   public void OnChanged(float var1) {
   }

   public void OnEqualPressed() {
   }

   void calculation() {
      String var1 = this.editText.getText().toString();
      String var2 = var1;
      if (var1.equals("-")) {
         var2 = "0";
      }

      float var3 = Float.parseFloat(var2);
      int var4 = this.op;
      if (var4 != 1) {
         if (var4 != 2) {
            if (var4 != 3) {
               if (var4 == 4) {
                  this.result /= var3;
               }
            } else {
               this.result *= var3;
            }
         } else {
            this.result -= var3;
         }
      } else {
         this.result += var3;
      }

      this.editText.setText(this.df.format((double)this.result));
   }

   void clearOperatorSelected() {
      this.bPlus.setSelected(false);
      this.bMinus.setSelected(false);
      this.bMultiply.setSelected(false);
      this.bDivide.setSelected(false);
   }

   public void clearResult() {
      boolean var1;
      if (this.result != 0.0F) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.flagFloat = false;
      this.flagOpPressed = false;
      this.op = 0;
      this.clearOperatorSelected();
      this.editText.setText("0");
      this.result = 0.0F;
      if (var1) {
         this.OnChanged(0.0F);
      }

   }

   public float getResult() {
      return this.result;
   }

   public void hideOkCancel() {
      this.bOK.setVisibility(8);
      this.bCancel.setVisibility(8);
   }

   public void init(View var1, View var2) {
      this.editText = (EditText)var2.findViewById(R.id.editText);
      this.editTextM = (EditText)var2.findViewById(R.id.editTextM);
      this.b1 = (TextView)var2.findViewById(R.id.button1);
      this.b2 = (TextView)var2.findViewById(R.id.button2);
      this.b3 = (TextView)var2.findViewById(R.id.button3);
      this.b4 = (TextView)var2.findViewById(R.id.button4);
      this.b5 = (TextView)var2.findViewById(R.id.button5);
      this.b6 = (TextView)var2.findViewById(R.id.button6);
      this.b7 = (TextView)var2.findViewById(R.id.button7);
      this.b8 = (TextView)var2.findViewById(R.id.button8);
      this.b9 = (TextView)var2.findViewById(R.id.button9);
      this.b0 = (TextView)var2.findViewById(R.id.button0);
      this.bDot = (TextView)var2.findViewById(R.id.buttonDot);
      this.bPlus = (TextView)var2.findViewById(R.id.buttonPlus);
      this.bMinus = (TextView)var2.findViewById(R.id.buttonMinus);
      this.bMultiply = (TextView)var2.findViewById(R.id.buttonMultiply);
      this.bDivide = (TextView)var2.findViewById(R.id.buttonDivide);
      this.bEqual = (TextView)var2.findViewById(R.id.buttonEqual);
      this.bClear = (TextView)var2.findViewById(R.id.buttonClear);
      this.bMR = (TextView)var2.findViewById(R.id.buttonMR);
      this.bMin = (TextView)var2.findViewById(R.id.buttonMin);
      this.bOK = (TextView)var2.findViewById(R.id.buttonOK);
      this.bCancel = (TextView)var2.findViewById(R.id.button_cancel);
      this.bBack = (ImageView)var2.findViewById(R.id.buttonBack);
      this.setClickListeners();
      PopupWindow var3 = new PopupWindow(var2, -1, -2);
      this.popup = var3;
      if (var1 != null) {
         var3.showAtLocation(var1, this.location, 0, 0);
      }

      this.popup.setOutsideTouchable(true);
      this.popup.setOnDismissListener(new OnDismissListener() {
         public void onDismiss() {
         }
      });
   }

   void keepinMemory() {
      this.editTextM.setText(this.editText.getText().toString());
      this.flagFloatM = this.flagFloat;
   }

   void keyin(String var1) {
      if (this.flagClearResultForNextDigit) {
         this.editText.setText("0");
      }

      this.flagClearResultForNextDigit = false;
      if (this.flagOpPressed) {
         this.editText.setText(var1);
         this.flagOpPressed = false;
      } else {
         String var2 = this.editText.getText().toString();
         String var3 = var2;
         StringBuilder var5;
         if (".".equals(var2.substring(0, 1))) {
            var5 = new StringBuilder();
            var5.append("0");
            var5.append(var2);
            var3 = var5.toString();
         }

         if ("0".equals(var3)) {
            this.editText.setText(var1);
         } else {
            EditText var4 = this.editText;
            var5 = new StringBuilder();
            var5.append(this.editText.getText());
            var5.append(var1);
            var4.setText(var5.toString());
         }

         if (!".".equals(var1) && this.flagFirstNumber) {
            this.toResult();
            this.OnChanged(this.result);
         }
      }

   }

   void opPressed(int var1) {
      this.flagClearResultForNextDigit = false;
      if (this.op > 0) {
         this.calculation();
         this.OnChanged(this.result);
      } else {
         this.toResult();
      }

      if (var1 < 5) {
         this.op = var1;
         this.flagOpPressed = true;
         this.flagFirstNumber = false;
      } else {
         this.op = 0;
         this.flagOpPressed = false;
         this.flagFirstNumber = true;
         this.flagClearResultForNextDigit = true;
         this.OnEqualPressed();
      }

      this.flagFloat = false;
   }

   void restorefromMemory() {
      this.editText.setText(this.editTextM.getText().toString());
      this.flagFloat = this.flagFloatM;
      if (!this.flagOpPressed) {
         this.toResult();
         this.OnChanged(this.result);
      }

   }

   void setClickListeners() {
      this.b1.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b2.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b5.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b6.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b7.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b8.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b9.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keyin(((TextView)var1).getText().toString());
         }
      });
      this.b0.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (!PopupCalculator.this.editText.getText().toString().equals("0")) {
               PopupCalculator.this.keyin("0");
            }

         }
      });
      this.bDot.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (!PopupCalculator.this.flagFloat) {
               PopupCalculator.this.keyin(".");
            }

            PopupCalculator.this.flagFloat = true;
         }
      });
      this.bPlus.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.opPressed(Integer.parseInt(var1.getTag().toString()));
            PopupCalculator.this.clearOperatorSelected();
            var1.setSelected(true);
         }
      });
      this.bMinus.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if ("0".equals(PopupCalculator.this.editText.getText().toString())) {
               PopupCalculator.this.keyin("-");
            } else {
               PopupCalculator.this.opPressed(Integer.parseInt(var1.getTag().toString()));
               PopupCalculator.this.clearOperatorSelected();
               var1.setSelected(true);
            }

         }
      });
      this.bMultiply.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.opPressed(Integer.parseInt(var1.getTag().toString()));
            PopupCalculator.this.clearOperatorSelected();
            var1.setSelected(true);
         }
      });
      this.bDivide.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.opPressed(Integer.parseInt(var1.getTag().toString()));
            PopupCalculator.this.clearOperatorSelected();
            var1.setSelected(true);
         }
      });
      this.bEqual.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.opPressed(Integer.parseInt(var1.getTag().toString()));
            PopupCalculator.this.clearOperatorSelected();
         }
      });
      this.bClear.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.clearResult();
         }
      });
      this.bBack.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.BackSpace();
         }
      });
      this.bMin.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.keepinMemory();
         }
      });
      this.bMR.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            PopupCalculator.this.restorefromMemory();
         }
      });
      this.bOK.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
         }
      });
      this.bCancel.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
         }
      });
   }

   public void setInitialValue(float var1) {
      this.editText.setText(String.valueOf(var1));
   }

   public void showOkCancel() {
      this.bOK.setVisibility(0);
      this.bCancel.setVisibility(0);
   }

   public void stop() {
      this.popup.dismiss();
      this.popup = null;
   }

   void toResult() {
      String var1 = this.editText.getText().toString();
      if (var1.length() == 0) {
         this.result = 0.0F;
      } else {
         String var2 = var1;
         if (".".equals(var1.substring(0, 1))) {
            StringBuilder var3 = new StringBuilder();
            var3.append("0");
            var3.append(var1);
            var2 = var3.toString();
         }

         if ("-".equals(var2)) {
            this.result = 0.0F;
         } else {
            this.result = Float.parseFloat(var2);
         }
      }

   }
}
