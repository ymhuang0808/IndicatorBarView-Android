package tw.fmbase.app.indicatorbarview_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import tw.fmbase.app.indicatorviewbar_android.IndicatorBar;

public class MainActivity extends AppCompatActivity {

    private IndicatorBar mIndicatorBar;
    private NumberPicker mNumberPicker;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIndicatorBar = (IndicatorBar) findViewById(R.id.indicator_bar);

        mNumberPicker = (NumberPicker) findViewById(R.id.value_picker);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(100);

        mBtn = (Button) findViewById(R.id.set_value_btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = mNumberPicker.getValue();
                mIndicatorBar.setValue(value);
            }
        });
    }
}
