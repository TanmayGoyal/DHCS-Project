package in.ac.iiitd.dhcs_project;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class HonkPlayActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ProgressBarTimer progressBarTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honk_play);

        progressBar = findViewById(R.id.abcprogressBar);

//        if (progressBar == null)
//            System.out.print("hiiii");
//        progressBar = new ProgressBar();
        progressBar.setProgress(100);
//
        progressBarTimer = new ProgressBarTimer(10000, 1, progressBar);
        progressBarTimer.start();
    }
}

class ProgressBarTimer extends CountDownTimer {
    ProgressBar progressBar;
    public ProgressBarTimer(long millisInFuture, long countDownInterval, ProgressBar progressBar) {
        super(millisInFuture, countDownInterval);
        this.progressBar = progressBar;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        int progress = (int) (millisUntilFinished/100);
//        System.out.print(progress);
        progressBar.setProgress(progress);
    }

    @Override
    public void onFinish() {
        progressBar.setProgress(0);
    }
}
//class Timer {
//    CountDownTimer timer;
//    int startCounter;
//    int stride;
//
//    Timer(int startCounter, int stride) {
//        this.startCounter = startCounter;
//        this.stride = stride;
//    }
//
//}
//
