package in.ac.iiitd.dhcs_project;

import android.content.Context;
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
    private int answers[] = {1, 1, 1, 2, 1, 3, 3, 4};
    private int currImage = 0;
    private int numOfClicks = 0;
    private int score = 0;
//    public final static String SCORE_KEY = "in.ac.iiitd.dhcs_project.HonkPlayActivity.SCORE_KEY" ;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honk_play);
        setCurrentImage();
        setImageRotateListener();

        class ProgressBarTimer extends CountDownTimer {
            ProgressBar progressBar;
            int finish = 0;
            public ProgressBarTimer(long millisInFuture, long countDownInterval, ProgressBar progressBar) {
                super(millisInFuture, countDownInterval);
                this.progressBar = progressBar;
            }

            @Override
            public void onTick(long millisUntilFinished) {

                int progress = (int) (millisUntilFinished/100);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(0);
                finish = 1;

                finish();
            }
        }

        ProgressBarTimer progressBarTimer;


        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(100);
        progressBarTimer = new ProgressBarTimer(10000, 1, progressBar);


        final Button honkButton = findViewById(R.id.honkButton);
        honkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numOfClicks++;
            }
        });
        progressBarTimer.start();


    }

    private void setImageRotateListener() {
        final Button rotatebutton = findViewById(R.id.go);
        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (numOfClicks == answers[currImage]) {
                    setNewImage();
                    addToast("Correct Answer");
                }

                else {
                    // INCOMPLETE go to home page
                    addToast("Wrong Answer");
                }
                numOfClicks = 0;
                setCurrentImage();
            }
        });
    }

    private void setNewImage() {
        currImage++;
        if (currImage == 8)
            currImage = 0;
    }

    private void setCurrentImage() {
        final ImageView imageView = findViewById(R.id.imageDisplay);
        imageView.setImageResource(images[currImage]);
    }

    private void addToast(String value) {

        CharSequence text = value;
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }
}



