package inventionstudio.inventionstudioandroid.Model;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import inventionstudio.inventionstudioandroid.R;

/**
 * Created by Maxwell on 4/13/2018.
 */

public class CustomSeekBar {
    int maxCount, textColor;
    Context mContext;
    LinearLayout mSeekLin;
    SeekBar mSeekBar;

    public CustomSeekBar(Context context, int maxCount, int textColor) {
        this.mContext = context;
        this.maxCount = maxCount;
        this.textColor = textColor;
    }

    public void addSeekBar(LinearLayout parent) {

        if (parent instanceof LinearLayout) {
            // Create seekbar and set layout orientation to vertical
            parent.setOrientation(LinearLayout.VERTICAL);
            mSeekBar = new SeekBar(mContext);
            mSeekBar.setMax(maxCount - 1);

            // Add LinearLayout for labels below SeekBar
            mSeekLin = new LinearLayout(mContext);
            mSeekLin.setOrientation(LinearLayout.HORIZONTAL);
            mSeekLin.setPadding(10, 0, 10, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(35, 10, 35, 15);
            mSeekLin.setLayoutParams(params);
            params.setMargins(0, 10, 10, 30);
            addLabelsAboveSeekBar();
            parent.addView(mSeekLin);
            parent.addView(mSeekBar);
        } else {
            Log.e("CustomSeekBar", " Parent is not a LinearLayout");
        }
    }

    /**
     * method to add labels above the seekbar
     */
    private void addLabelsAboveSeekBar() {
        // Loop through all values and create textview for each at an interval
        for (int count = 0; count < maxCount; count++) {
            TextView textView = new TextView(mContext);

            // Make first selection N/A if not needed.
            if (count == 0) {
                textView.setText("N/A");
            } else {
                textView.setText(String.valueOf(count));
            }
            textView.setTextColor(mContext.getResources().getColor(R.color.IS_Text_Light));
            textView.setGravity(Gravity.LEFT);
            mSeekLin.addView(textView);
            textView.setLayoutParams((count == maxCount - 1) ? getLayoutParams(0.0f) : getLayoutParams(1.0f));
        }
    }

    // use methods from the parent seekbar for progress
    public int getProgress() {
        return mSeekBar.getProgress();
    }

    public void setProgress(int value) {
        mSeekBar.setProgress(value);
    }

    LinearLayout.LayoutParams getLayoutParams(float weight) {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, weight);
    }
}
