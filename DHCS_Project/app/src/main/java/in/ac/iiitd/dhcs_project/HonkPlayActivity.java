package in.ac.iiitd.dhcs_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
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
    private int correctAnswers = 0;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honk_play);
        setCurrentImage();
        setImageRotateListener();

        class ProgressBarTimer extends CountDownTimer {
            private ProgressBar progressBar;
            private int finish = 0;
            private ProgressBarTimer(long millisInFuture, long countDownInterval, ProgressBar progressBar) {
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
                SharedClass obj = getObject();
                if (correctAnswers >= obj.minImages[obj.currentLevel]) {
                    finish();
                    startQuestionActivity(obj);
                }
                else {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(HonkPlayActivity.this).create();
                    alertDialog.setTitle("Wrong Answer");
                    alertDialog.setMessage("Sorry, You Lost!");
                    alertDialog.setIcon(R.drawable.wrong);
                    alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(HonkPlayActivity.this, HomeActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                }
            }
        }

        ProgressBarTimer progressBarTimer;

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(100);
        progressBarTimer = new ProgressBarTimer(10000, 1, progressBar);

//        final MediaPlayer mp = MediaPlayer.create(HonkPlayActivity.this, R.raw.car_honk);
        final Button honkButton = findViewById(R.id.honkButton);
        honkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mp.start();
                numOfClicks++;
            }
        });
        progressBarTimer.start();


    }

    private SharedClass getObject() {
        Intent i = getIntent();
        return (SharedClass) i.getSerializableExtra("sharedObject");
    }

    public void startQuestionActivity(SharedClass sharedObject) {
        Intent intent;
        intent = new Intent(this, QuestionPageActivity.class);
        intent.putExtra("sharedObject", sharedObject);

        startActivity(intent);

    }

    private void setImageRotateListener() {
        final Button rotateButton = findViewById(R.id.go);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                mp.release();
//                mp = null;
                if (numOfClicks == answers[currImage]) {
                    setNewImage();
                    addToast("Correct Answer");
                    correctAnswers++;
                }

                else {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(HonkPlayActivity.this).create();
                    alertDialog.setTitle("Wrong Answer");
                    alertDialog.setMessage("Sorry, You Lost!");
                    alertDialog.setIcon(R.drawable.wrong);
                    alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(HonkPlayActivity.this, HomeActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
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