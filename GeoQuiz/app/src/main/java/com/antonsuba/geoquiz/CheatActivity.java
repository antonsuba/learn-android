package com.antonsuba.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.antonsuba.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.antonsuba.geoquiz.answer_shown";
    private static final String KEY_QUIZ_ANSWER = "quizAnswer";
    private static final String KEY_IS_CHEATER = "isCheater";

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    private boolean mAnswerIsTrue;
    private boolean mIsCheater;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);

        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_QUIZ_ANSWER);
            setAnswerTextView();

            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER);
            setAnswerShown(mIsCheater);
        } else {
            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        }

        mShowAnswerButton = (Button)findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerTextView();
                setAnswerShown(true);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_QUIZ_ANSWER, mAnswerIsTrue);
        outState.putBoolean(KEY_IS_CHEATER, mIsCheater);
    }

    private void setAnswerShown(boolean isAnswerShown) {
        mIsCheater = isAnswerShown;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intent);
    }

    private void setAnswerTextView() {
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
    }
}
