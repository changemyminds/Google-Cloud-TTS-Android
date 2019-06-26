package darren.gcptts.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import darren.gcptts.R;
import darren.gcptts.presenter.MainActivityPresenter;

/**
 * Author: Changemyminds.
 * Date: 2018/6/25.
 * Description:
 * Reference:
 */
public class MainActivity extends AppCompatActivity implements MainActivityView {
    private MainActivityPresenter mPresenter;

    private ArrayAdapter<String> mAdapterLanguage;
    private ArrayAdapter<String> mAdapterStyle;
    private Spinner mSpinnerLanguage;
    private Spinner mSpinnerStyle;

    private EditText mEditText;
    private TextView mTextViewGender;
    private TextView mTextViewSampleRate;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeakRate;
    private Button mButtonSpeak;
    private Button mButtonSpeakPause;
    private Button mButtonSpeakStop;
    private Button mButtonRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildViews();

        mSeekBarPitch.setProgress(2000);
        mSeekBarSpeakRate.setProgress(75);
        mPresenter = new MainActivityPresenter(this);
        mPresenter.initGCPTTSSettings();
        mPresenter.initAndroidTTSSetting();
    }

    @Override
    protected void onDestroy() {
        mPresenter.disposeSpeak();
        super.onDestroy();
    }

    private void buildViews() {
        mEditText = findViewById(R.id.ettIdText);
        mSpinnerLanguage = findViewById(R.id.snrIdLanguage);
        mSpinnerStyle = findViewById(R.id.snrIdStyle);
        mTextViewGender = findViewById(R.id.tvwIdGender);
        mTextViewSampleRate = findViewById(R.id.tvwIdSampleRate);
        mSeekBarPitch = findViewById(R.id.sbrIdPitch);
        mSeekBarSpeakRate = findViewById(R.id.sbrIdSpeakRate);
        mButtonSpeak = findViewById(R.id.btnIdSpeak);
        mButtonSpeakPause = findViewById(R.id.btnIdPauseAndResume);
        mButtonSpeakStop = findViewById(R.id.btnIdStop);
        mButtonRefresh = findViewById(R.id.btnIdRefresh);

        mSeekBarPitch.setMax(4000);
        mSeekBarPitch.setProgress(2000);

        mSeekBarSpeakRate.setMax(375);
        mSeekBarSpeakRate.setProgress(75);

        mButtonSpeak.setOnClickListener((view) -> mPresenter.startSpeak(mEditText.getText().toString()));
        mButtonSpeakPause.setOnClickListener((view) -> {
            if (mButtonSpeakPause.getText().toString().compareTo(getResources().getString(R.string.btnPtSpeechPause)) == 0) {
                mPresenter.pauseSpeak();
                mButtonSpeakPause.setText(getResources().getString(R.string.btnPtSpeechResume));
            } else {
                mPresenter.resumeSpeak();
                mButtonSpeakPause.setText(getResources().getString(R.string.btnPtSpeechPause));
            }
        });
        mButtonRefresh.setOnClickListener((view) ->
        {
            mSeekBarPitch.setProgress(2000);
            mSeekBarSpeakRate.setProgress(75);
            mPresenter.initGCPTTSSettings();
        });
        mButtonSpeakStop.setOnClickListener((view) -> mPresenter.stopSpeak());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onTextToSpeechResult(this, requestCode, resultCode);
    }

    public void onBackPressed() {
        mPresenter.exitApp();
    }

    @Override
    public void setAdapterLanguage(String[] languages) {
        if (languages == null) return;

        mAdapterLanguage = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item
                , languages);
        mAdapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void setSpinnerLanguage(final String[] languages) {
        if (languages == null) return;

        invoke(() -> {
            mSpinnerLanguage.setAdapter(mAdapterLanguage);
            mSpinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mPresenter.selectLanguage(languages[position]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        });
    }

    @Override
    public String getSelectedLanguageText() {
        return (mSpinnerLanguage.getSelectedItem() != null) ? mSpinnerLanguage.getSelectedItem().toString() : "";
    }

    @Override
    public void setAdapterStyle(String[] styles) {
        mAdapterStyle = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item
                , styles);
        mAdapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void setSpinnerStyle(final String[] styles) {
        mSpinnerStyle.setAdapter(mAdapterStyle);
        mSpinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.selectStyle(styles[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void clearUI() {
        invoke(() -> {
            mSpinnerLanguage.setAdapter(null);
            mSpinnerStyle.setAdapter(null);
            setTextViewGender("");
            setTextViewSampleRate("");
        });
    }

    @Override
    public String getSelectedStyleText() {
        return (mSpinnerStyle.getSelectedItem() != null) ? mSpinnerStyle.getSelectedItem().toString() : "";
    }

    @Override
    public void setTextViewGender(String gender) {
        mTextViewGender.setText(gender);
    }

    @Override
    public void setTextViewSampleRate(String sampleRate) {
        mTextViewSampleRate.setText(sampleRate);
    }

    @Override
    public int getProgressPitch() {
        return mSeekBarPitch.getProgress();
    }

    @Override
    public int getProgressSpeakRate() {
        return mSeekBarSpeakRate.getProgress();
    }

    @Override
    public void makeToast(String text, boolean longShow) {
        invoke(() -> Toast.makeText(this, text, (longShow) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show());
    }

    private void invoke(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
