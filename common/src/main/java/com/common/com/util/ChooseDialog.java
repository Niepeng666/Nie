package com.common.com.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.com.R;


/**
 */
public class ChooseDialog extends Dialog {

    private OnChooseDialogListener listener;
    private TextView textTip00;
    private TextView textTip01;
    private TextView textCancle;

    public ChooseDialog(Context context,String name1,String name2) {
        this(context,0,name1,name2);
    }

    public ChooseDialog(Context context, int themeResId,String name1,String name2) {
        super(context, R.style.payDialogstyle);
        setContentView(R.layout.dialog_choose);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textTip00 = (TextView) findViewById(R.id.text_tip00);
        textTip00.setText(name1);
        textTip01 = (TextView) findViewById(R.id.text_tip01);
        textTip01.setText(name2);
        textCancle = (TextView) findViewById(R.id.text_cancle);
    }

//    public ChooseDialog setListText(String... text) {
//        textTip00.setText(text[0]);
//        textTip01.setText(text[1]);
//        textCancle.setText(text[2]);
//        return this;
//    }

    public ChooseDialog setOnChooseDialogListener(final OnChooseDialogListener listener) {

        this.listener = listener;

        textCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onCancle();
                }
            }
        });

        textTip00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onSelect0();
                }
            }
        });

        textTip01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onSelect1();
                }
            }
        });
        return this;
    }

    public interface OnChooseDialogListener{

        void onCancle();

        void onSelect0();

        void onSelect1();
    }

}