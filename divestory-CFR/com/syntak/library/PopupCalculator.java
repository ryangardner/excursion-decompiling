/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.TextView
 */
package com.syntak.library;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.syntak.library.R;
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
    float scaleDigit = 1.5f;
    float scaleText = 1.2f;
    String strValue = "0";

    public PopupCalculator(Context context, View view, int n) {
        this.context = context;
        if (n >= 0) {
            this.location = n;
        }
        this.init(view, ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(R.layout.popup_calculator, null));
    }

    public PopupCalculator(Context context, View view, View view2, int n) {
        this.context = context;
        if (n >= 0) {
            this.location = n;
        }
        this.init(view, view2);
    }

    void BackSpace() {
        Object object = this.editText.getText();
        object = object != null ? object.toString() : null;
        if (object == null) return;
        if (((String)object).length() <= 0) return;
        if (((String)object).length() == 1) {
            this.editText.setText((CharSequence)"0");
        } else {
            object = ((String)object).substring(0, ((String)object).length() - 1);
            this.editText.setText((CharSequence)object);
        }
        this.toResult();
        this.OnChanged(this.result);
    }

    public void OnChanged(float f) {
    }

    public void OnEqualPressed() {
    }

    void calculation() {
        String string2;
        String string3 = string2 = this.editText.getText().toString();
        if (string2.equals("-")) {
            string3 = "0";
        }
        float f = Float.parseFloat(string3);
        int n = this.op;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        this.result /= f;
                    }
                } else {
                    this.result *= f;
                }
            } else {
                this.result -= f;
            }
        } else {
            this.result += f;
        }
        this.editText.setText((CharSequence)this.df.format(this.result));
    }

    void clearOperatorSelected() {
        this.bPlus.setSelected(false);
        this.bMinus.setSelected(false);
        this.bMultiply.setSelected(false);
        this.bDivide.setSelected(false);
    }

    public void clearResult() {
        boolean bl = this.result != 0.0f;
        this.flagFloat = false;
        this.flagOpPressed = false;
        this.op = 0;
        this.clearOperatorSelected();
        this.editText.setText((CharSequence)"0");
        this.result = 0.0f;
        if (!bl) return;
        this.OnChanged(0.0f);
    }

    public float getResult() {
        return this.result;
    }

    public void hideOkCancel() {
        this.bOK.setVisibility(8);
        this.bCancel.setVisibility(8);
    }

    public void init(View view, View view2) {
        this.editText = (EditText)view2.findViewById(R.id.editText);
        this.editTextM = (EditText)view2.findViewById(R.id.editTextM);
        this.b1 = (TextView)view2.findViewById(R.id.button1);
        this.b2 = (TextView)view2.findViewById(R.id.button2);
        this.b3 = (TextView)view2.findViewById(R.id.button3);
        this.b4 = (TextView)view2.findViewById(R.id.button4);
        this.b5 = (TextView)view2.findViewById(R.id.button5);
        this.b6 = (TextView)view2.findViewById(R.id.button6);
        this.b7 = (TextView)view2.findViewById(R.id.button7);
        this.b8 = (TextView)view2.findViewById(R.id.button8);
        this.b9 = (TextView)view2.findViewById(R.id.button9);
        this.b0 = (TextView)view2.findViewById(R.id.button0);
        this.bDot = (TextView)view2.findViewById(R.id.buttonDot);
        this.bPlus = (TextView)view2.findViewById(R.id.buttonPlus);
        this.bMinus = (TextView)view2.findViewById(R.id.buttonMinus);
        this.bMultiply = (TextView)view2.findViewById(R.id.buttonMultiply);
        this.bDivide = (TextView)view2.findViewById(R.id.buttonDivide);
        this.bEqual = (TextView)view2.findViewById(R.id.buttonEqual);
        this.bClear = (TextView)view2.findViewById(R.id.buttonClear);
        this.bMR = (TextView)view2.findViewById(R.id.buttonMR);
        this.bMin = (TextView)view2.findViewById(R.id.buttonMin);
        this.bOK = (TextView)view2.findViewById(R.id.buttonOK);
        this.bCancel = (TextView)view2.findViewById(R.id.button_cancel);
        this.bBack = (ImageView)view2.findViewById(R.id.buttonBack);
        this.setClickListeners();
        view2 = new PopupWindow(view2, -1, -2);
        this.popup = view2;
        if (view != null) {
            view2.showAtLocation(view, this.location, 0, 0);
        }
        this.popup.setOutsideTouchable(true);
        this.popup.setOnDismissListener(new PopupWindow.OnDismissListener(){

            public void onDismiss() {
            }
        });
    }

    void keepinMemory() {
        this.editTextM.setText((CharSequence)this.editText.getText().toString());
        this.flagFloatM = this.flagFloat;
    }

    void keyin(String string2) {
        if (this.flagClearResultForNextDigit) {
            this.editText.setText((CharSequence)"0");
        }
        this.flagClearResultForNextDigit = false;
        if (this.flagOpPressed) {
            this.editText.setText((CharSequence)string2);
            this.flagOpPressed = false;
            return;
        }
        String string3 = this.editText.getText().toString();
        CharSequence charSequence = string3;
        if (".".equals(string3.substring(0, 1))) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("0");
            ((StringBuilder)charSequence).append(string3);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        if ("0".equals(charSequence)) {
            this.editText.setText((CharSequence)string2);
        } else {
            string3 = this.editText;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((Object)this.editText.getText());
            ((StringBuilder)charSequence).append(string2);
            string3.setText((CharSequence)((StringBuilder)charSequence).toString());
        }
        if (".".equals(string2)) return;
        if (!this.flagFirstNumber) return;
        this.toResult();
        this.OnChanged(this.result);
    }

    void opPressed(int n) {
        this.flagClearResultForNextDigit = false;
        if (this.op > 0) {
            this.calculation();
            this.OnChanged(this.result);
        } else {
            this.toResult();
        }
        if (n < 5) {
            this.op = n;
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
        this.editText.setText((CharSequence)this.editTextM.getText().toString());
        this.flagFloat = this.flagFloatM;
        if (this.flagOpPressed) return;
        this.toResult();
        this.OnChanged(this.result);
    }

    void setClickListeners() {
        this.b1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b4.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b5.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b6.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b7.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b8.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b9.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keyin(((TextView)view).getText().toString());
            }
        });
        this.b0.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (PopupCalculator.this.editText.getText().toString().equals("0")) {
                    return;
                }
                PopupCalculator.this.keyin("0");
            }
        });
        this.bDot.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (!PopupCalculator.this.flagFloat) {
                    PopupCalculator.this.keyin(".");
                }
                PopupCalculator.this.flagFloat = true;
            }
        });
        this.bPlus.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.opPressed(Integer.parseInt(view.getTag().toString()));
                PopupCalculator.this.clearOperatorSelected();
                view.setSelected(true);
            }
        });
        this.bMinus.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if ("0".equals(PopupCalculator.this.editText.getText().toString())) {
                    PopupCalculator.this.keyin("-");
                    return;
                }
                PopupCalculator.this.opPressed(Integer.parseInt(view.getTag().toString()));
                PopupCalculator.this.clearOperatorSelected();
                view.setSelected(true);
            }
        });
        this.bMultiply.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.opPressed(Integer.parseInt(view.getTag().toString()));
                PopupCalculator.this.clearOperatorSelected();
                view.setSelected(true);
            }
        });
        this.bDivide.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.opPressed(Integer.parseInt(view.getTag().toString()));
                PopupCalculator.this.clearOperatorSelected();
                view.setSelected(true);
            }
        });
        this.bEqual.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.opPressed(Integer.parseInt(view.getTag().toString()));
                PopupCalculator.this.clearOperatorSelected();
            }
        });
        this.bClear.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.clearResult();
            }
        });
        this.bBack.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.BackSpace();
            }
        });
        this.bMin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.keepinMemory();
            }
        });
        this.bMR.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PopupCalculator.this.restorefromMemory();
            }
        });
        this.bOK.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
            }
        });
        this.bCancel.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
            }
        });
    }

    public void setInitialValue(float f) {
        this.editText.setText((CharSequence)String.valueOf(f));
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
        String string2 = this.editText.getText().toString();
        if (string2.length() == 0) {
            this.result = 0.0f;
            return;
        }
        CharSequence charSequence = string2;
        if (".".equals(string2.substring(0, 1))) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("0");
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        if ("-".equals(charSequence)) {
            this.result = 0.0f;
            return;
        }
        this.result = Float.parseFloat((String)charSequence);
    }

}

