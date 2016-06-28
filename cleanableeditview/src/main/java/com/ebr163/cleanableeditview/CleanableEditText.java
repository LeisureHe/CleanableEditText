package com.ebr163.cleanableeditview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Bakht on 27.06.2016.
 */
public class CleanableEditText extends RelativeLayout {

    private TextInputLayout textView;
    private ImageView cleanButton;
    private RelativeLayout transitionsContainer;
    private TextView error;

    public CleanableEditText(Context context) {
        super(context);
        init(context);
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cleanable_edit_view, this, false);
        transitionsContainer = (RelativeLayout) view.findViewById(R.id.container);
        cleanButton = (ImageView) view.findViewById(R.id.clean_btn);
        error = (TextView) view.findViewById(R.id.error);
        initText(view);
        addView(view);
    }

    private void initText(View view){
        textView = (TextInputLayout) view.findViewById(R.id.text);
        textView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TransitionManager.beginDelayedTransition(transitionsContainer);
                if (s.toString().equals("")){
                    cleanButton.setVisibility(GONE);
                } else {
                    cleanButton.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void setError(String text){
        error.setText(text);
    }

    public void setErrorEnabled(boolean flag){
        error.setVisibility(flag ? VISIBLE : GONE);
    }
}
