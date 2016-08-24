package com.example.leealbert.myfirstapplication;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EditText.OnEditorActionListener {

    //static final String[] pokemonRadioNames = {"小火龍", "傑尼龜", "妙蛙種子"};
    TextView infoText;
    EditText nameEditText;
    RadioGroup optionsGroup;
    Button confirmBtn;
    String welcomeMessage;
    String selectedPokeName = "小火龍";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main_1);
        Log.d("testLog", "Hello");

        infoText = (TextView)findViewById(R.id.infoText);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        nameEditText.setOnEditorActionListener(this);
        nameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        optionsGroup = (RadioGroup)findViewById(R.id.optionsGroup);
        confirmBtn = (Button)findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(MainActivity.this);

        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Log.d("test", "checkedId:" + checkedId);

                switch(checkedId)
                {
                    case R.id.radioButton:
                        Log.d("test", "radioButton1");
                        selectedPokeName = "小火龍";
                        break;
                    case R.id.radioButton2:
                        Log.d("test", "radioButton2");
                        selectedPokeName = "傑尼龜";
                        break;
                    case R.id.radioButton3:
                        Log.d("test", "radioButton3");
                        selectedPokeName = "妙蛙種子";
                        break;
                    default:
                        Log.d("test", "radioButton1");
                        selectedPokeName = "小火龍";
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.confirmBtn)
        {
            //confirmBtn was clicked
            Log.d("buttonTest", "Confirm-button was clicked");
            String name = nameEditText.getText().toString();
            int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();
            View selectedRadioButtonView = optionsGroup.findViewById(selectedRadioButtonId);
            //int selectedIndex = optionsGroup.indexOfChild(selectedRadioButtonView);
            RadioButton selectedRadioButton = (RadioButton)selectedRadioButtonView;
            String radioBtnText = selectedRadioButton.getText().toString();

            CheckBox hideCheckBox = (CheckBox)findViewById(R.id.hideCheckBox);
            if(!hideCheckBox.isChecked())
            {
                welcomeMessage = String.format("你好,訓練家%s 歡迎來到神奇寶貝世界 你的第一個夥伴是%s",
                        name, selectedPokeName);
            }
            else
            {
                welcomeMessage = String.format("你好,歡迎來到神奇寶貝世界 你的第一個夥伴是%s",
                        selectedPokeName);
            }

            infoText.setText(welcomeMessage);


        }
        /*
        else if(viewId == R.id.optionsGroup)
        {
            Log.d("test", "HelloRadio");
        }
        */
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {

        if (actionId == EditorInfo.IME_ACTION_DONE)
        {
            //dismiss virtual keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);

            //simluate button clicked
            confirmBtn.performClick();
        }
        return false;
    }
}
