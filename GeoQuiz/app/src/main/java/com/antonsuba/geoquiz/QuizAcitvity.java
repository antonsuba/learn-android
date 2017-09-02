package com.antonsuba.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizAcitvity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";
    private static final String KEY_ANSWERED_QUESTIONS = "answeredQuestions";
    private static final int REQUEST_CODE_CHEAT = 0;

    private TextView mQuestionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private Button mCheatButton;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mQuestionCounter = 0;
    private int mCorrectAnswerCount = 0;

    private int mAnsweredCounter = 0;
    private boolean[] mAnsweredQuestions;

    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz_acitvity);

        if (savedInstanceState != null) {
            mQuestionCounter = savedInstanceState.getInt(KEY_INDEX);
            mAnsweredQuestions = savedInstanceState.getBooleanArray(KEY_ANSWERED_QUESTIONS);
        } else {
            mAnsweredQuestions = initAnsweredQuestions(mQuestionBank.length);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionCounter = (mQuestionCounter + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnswerButtons(false);
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnswerButtons(false);
                checkAnswer(false);
            }
        });

        mPrevButton =  (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = mQuestionBank.length;
                mQuestionCounter = ((((mQuestionCounter - 1) % length) + length) % length);
                mIsCheater = false;
                updateQuestion();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionCounter = (mQuestionCounter + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mQuestionCounter].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizAcitvity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mQuestionCounter);
        outState.putInt(KEY_SCORE, mCorrectAnswerCount);
        outState.putBooleanArray(KEY_ANSWERED_QUESTIONS, mAnsweredQuestions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    private void updateQuestion() {
        Question question = mQuestionBank[mQuestionCounter];
        int questionTextId = question.getTextResId();
        mQuestionTextView.setText(questionTextId);

        if (mAnsweredQuestions[mQuestionCounter]) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }

    private void checkAnswer(boolean userAnswer) {
        Question question = mQuestionBank[mQuestionCounter];
        boolean isAnswerTrue = question.isAnswerTrue();

        int messageResId;

        if (mIsCheater) {
            messageResId = R.string.judgement_toast;
        }
        else if (userAnswer == isAnswerTrue) {
            messageResId = R.string.correct_toast;
            mCorrectAnswerCount++;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        mAnsweredCounter++;
        mAnsweredQuestions[mQuestionCounter] = true;

        if (mAnsweredCounter >= mQuestionBank.length) {
            String resultMessage = getResources().getString(messageResId);
            String scoreMessage = getResources().getString(R.string.score_toast);
            int percentage = (mCorrectAnswerCount * 100) / mQuestionBank.length;

            String finalMessage = resultMessage + ". " + scoreMessage + " " + percentage + "%";

            Toast.makeText(this, finalMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleAnswerButtons(boolean setting) {
        mTrueButton.setEnabled(setting);
        mFalseButton.setEnabled(setting);
    }

    private boolean[] initAnsweredQuestions(int length) {
        boolean[] answeredQuestions = new boolean[length];
        for (int i = 0; i < length; i++) {
            answeredQuestions[i] = false;
        }
        return answeredQuestions;
    }
}
