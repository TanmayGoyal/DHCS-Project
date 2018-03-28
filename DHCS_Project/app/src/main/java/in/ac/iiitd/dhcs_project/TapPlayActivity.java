package in.ac.iiitd.dhcs_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class TapPlayActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private static final String TAG = "TapPlayActivity";
    private int answers[] = {0,0};
    private int currentImage = 0;
    private int correctAnswers = 0;

    private int leftX = 190;
    private int rightX = 478;
    private int topY = 232;
    private int bottomY = 355;

    private int leftX2 = 0;
    private int rightX2 = 202;
    private int topY2 = 252;
    private int bottomY2 = 339;

    private int wrongAnswer = 0;
    ProgressBar progressBar;

    private ImageCoordinates coordinateArr[] = new ImageCoordinates[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_play);

        setCoordinates(coordinateArr);
        final ImageView imageView = findViewById(R.id.imageView);
        bitmap2 = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Log.e(TAG, bitmap2.toString());
        bitmap = bitmap2.copy(bitmap2.getConfig(), true);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getX();
                int y = (int)event.getY();
//                Toast.makeText(TapPlayActivity.this, String.valueOf(x) + " , " + String.valueOf(y), Toast.LENGTH_SHORT).show();
                for (int i = x - 20; i <= x + 20; i++) {
                    for (int j = y - 20; j <= y + 20; j++) {
                        bitmap.setPixel(i, j, Color.BLACK);
                    }
                }
                if (checkAnswer1(x,y)) {
                    answers[0] = 1;
                }
                else if (checkAnswer2(x,y)) {
                    answers[1] = 1;
                }
                else {
//                    finish();
//                    AlertDialog alertDialog;
//                    alertDialog = new AlertDialog.Builder(TapPlayActivity.this).create();
//                    alertDialog.setTitle("Wrong Answer");
//                    alertDialog.setMessage("Sorry, You Lost!");
//                    alertDialog.setIcon(R.drawable.wrong);
//                    alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(TapPlayActivity.this, HomeActivity.class);
////                            finish();
//                            startActivity(intent);
//                        }
//                    });
//                    alertDialog.show();

                    wrongAnswer = 1;
                }
                imageView.setImageBitmap(bitmap);
                imageView.setImageDrawable(new BitmapDrawable(getResources(), bitmap));

                return false;
            }


        });

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
//                SharedClass obj = getObject();
//                if (correctAnswers >= obj.minImages[obj.currentLevel]) {
//                    startQuestionActivity(obj);
//                }
                final SharedClass obj = getObject();

                if (answers[0] == 1 && answers[1] == 1 && wrongAnswer == 0) {
                    finish();
                    startQuestionActivity(obj);
                }
                else {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(TapPlayActivity.this).create();
                    alertDialog.setTitle("Wrong Answer");
                    alertDialog.setMessage("Sorry, You Lost!");
                    alertDialog.setIcon(R.drawable.wrong);
                    alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(TapPlayActivity.this, HomeActivity.class);
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

        final SharedClass obj = getObject();

        final Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answers[0] == 1 && answers[1] == 1) {
                    startQuestionActivity(obj);
                }
                else {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(TapPlayActivity.this).create();
                    alertDialog.setTitle("Wrong Answer");
                    alertDialog.setMessage("Sorry, You Lost!");
                    alertDialog.setIcon(R.drawable.wrong);
                    alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(TapPlayActivity.this, HomeActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                }
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

    private boolean checkAnswer1(int x, int y) {
        return ((x >= leftX && x <= rightX) && (y >= topY && y <= bottomY));
    }

    private boolean checkAnswer2(int x, int y) {
        return ((x >= leftX2 && x <= rightX2) && (y >= topY2 && y <= bottomY2));
    }


    private void setCoordinates(ImageCoordinates coordinateArr[]) {

        int leftX = 297;
        int rightX = 605;
        int topY = 256;
        int bottomY = 371;

//        coordinateArr[0].leftX = 60;
//        coordinateArr[0].rightX = 695;
//        coordinateArr[0].topY = 250;
//        coordinateArr[0].bottomY = 350;
    }

    class ImageCoordinates {
        int leftX;
        int rightX;
        int topY;
        int bottomY;
    }

}
