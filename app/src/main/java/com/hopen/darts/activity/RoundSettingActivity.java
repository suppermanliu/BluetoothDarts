package com.hopen.darts.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.util.PrefUtils;

public class RoundSettingActivity extends Activity implements
        OnSeekBarChangeListener {

    SeekBar bar1, bar2, bar3;
    TextView bar1_number, bar2_number, bar3_number;
    public int highscore, game01, mickey, result_highscore, result_01,
            result_mickey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_setting);

        bar1 = (SeekBar) findViewById(R.id.seekBar1);
        bar2 = (SeekBar) findViewById(R.id.seekBar2);
        bar3 = (SeekBar) findViewById(R.id.seekBar3);

        bar1_number = (TextView) findViewById(R.id.bar1_number);
        bar2_number = (TextView) findViewById(R.id.bar2_number);
        bar3_number = (TextView) findViewById(R.id.bar3_number);

        //为三个seekbar设置监听事件
        bar1.setOnSeekBarChangeListener(this);
        bar2.setOnSeekBarChangeListener(this);
        bar3.setOnSeekBarChangeListener(this);


        //第一次进入设置页面设置默认值，下一次进来会读取本地SP文件获取数值
        result_highscore = PrefUtils.getInt(getApplicationContext(), "ronud_highscore", 8);
        result_01 = PrefUtils.getInt(getApplicationContext(), "ronud_01", 10);
        result_mickey = PrefUtils.getInt(getApplicationContext(), "ronud_mickey", 15);

        //SeekBar1设置进度条进度和对应数字
        bar1.setProgress(result_highscore);
        bar1_number.setText(result_highscore + 2 + "/20");

        //SeekBar2设置进度条进度和对应数字
        bar2.setProgress(result_01);
        bar2_number.setText(result_01 + "/20");

        //SeekBar3设置进度条进度和对应数字
        bar3.setProgress(result_mickey);
        bar3_number.setText(result_mickey + "/20");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {

        //获取第一个SeekBar的进度值
        highscore = bar1.getProgress();
        bar1_number.setText(highscore + 2 + "/20");

        //获取第二个SeekBar的进度值
        game01 = bar2.getProgress();
        bar2_number.setText(game01 + 2 + "/20");

        //获取第三个SeekBar的进度值
        mickey = bar3.getProgress();
        bar3_number.setText(mickey + 2 + "/20");


        //设置游戏回合数最低为2回合
//        if (highscore < 2) {
//            bar1.setProgress(2);
//        }
//        if (game01 < 2) {
//            bar2.setProgress(2);
//        }
//        if (mickey < 2) {
//            bar3.setProgress(2);
//        }
//		if(mickey>16){ 
//			bar3.setProgress(16);
//		}

        //滑动滑块后，将最后的值存入SP中
        PrefUtils.putInt(getApplicationContext(), "ronud_highscore", highscore);
        PrefUtils.putInt(getApplicationContext(), "ronud_01", game01);
        PrefUtils.putInt(getApplicationContext(), "ronud_mickey", mickey);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // result1 = bar1.getProgress();
        // SharedPreferences sp =
        // getSharedPreferences("round_setting",MODE_PRIVATE);
        // Editor editor = sp.edit();
        // editor.putInt("ronud_highscore", result1);
        // editor.commit();
    }
}
