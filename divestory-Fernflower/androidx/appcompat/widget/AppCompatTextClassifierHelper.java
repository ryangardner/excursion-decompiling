package androidx.appcompat.widget;

import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.core.util.Preconditions;

final class AppCompatTextClassifierHelper {
   private TextClassifier mTextClassifier;
   private TextView mTextView;

   AppCompatTextClassifierHelper(TextView var1) {
      this.mTextView = (TextView)Preconditions.checkNotNull(var1);
   }

   public TextClassifier getTextClassifier() {
      TextClassifier var1 = this.mTextClassifier;
      TextClassifier var2 = var1;
      if (var1 == null) {
         TextClassificationManager var3 = (TextClassificationManager)this.mTextView.getContext().getSystemService(TextClassificationManager.class);
         if (var3 != null) {
            return var3.getTextClassifier();
         }

         var2 = TextClassifier.NO_OP;
      }

      return var2;
   }

   public void setTextClassifier(TextClassifier var1) {
      this.mTextClassifier = var1;
   }
}
