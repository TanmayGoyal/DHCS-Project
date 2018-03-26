package in.ac.iiitd.dhcs_project;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HonkPlayActivity extends AppCompatActivity {

    private Integer images[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8};
    private String answers[] = {"2", "1", "1", "2", "1", "3", "3", "4"};
    private int currImage = 0;
    private int answer = 0;
    private int score = 0;
//    public final static String SCORE_KEY = "in.ac.iiitd.dhcs_project.HonkPlayActivity.SCORE_KEY" ;

    ProgressBar progressBar;
    ProgressBarTimer progressBarTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honk_play);
        setCurrentImage();
        setImageRotateListener();

        progressBar = findViewById(R.id.abcprogressBar);

//        if (progressBar == null)
//            System.out.print("hiiii");
//        progressBar = new ProgressBar();
        progressBar.setProgress(100);
//
        progressBarTimer = new ProgressBarTimer(10000, 1, progressBar);
        progressBarTimer.start();
    }

    private void setImageRotateListener() {
        final Button rotatebutton = findViewById(R.id.button);
        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currImage++;
                if (currImage == 8)
                    currImage = 0;
                setCurrentImage();
            }
        });
    }

    private void setCurrentImage() {
        final ImageView imageView = findViewById(R.id.imageDisplay);
        imageView.setImageResource(images[currImage]);
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
