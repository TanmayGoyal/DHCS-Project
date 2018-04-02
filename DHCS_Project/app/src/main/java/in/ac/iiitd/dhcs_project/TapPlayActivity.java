package in.ac.iiitd.dhcs_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class TapPlayActivity extends AppCompatActivity {
    private Bitmap bm;

    private Integer images[] = {R.drawable.img01_original, R.drawable.img02_original, R.drawable.img03_original, R.drawable.img04_original, R.drawable.img05_original, R.drawable.img06_original, R.drawable.img07_original, R.drawable.img08_original, R.drawable.img09_original};
    private Integer labels[] = {R.drawable.img01_label, R.drawable.img02_label, R.drawable.img03_label, R.drawable.img04_label, R.drawable.img05_label, R.drawable.img06_label, R.drawable.img07_label, R.drawable.img08_label, R.drawable.img09_label};

    private static final String TAG = "TapPlayActivity";
    private String answers[] = new  String[9];

    private int currImage;
    Random rand;
    private int score = 0;
    private boolean isCorrect = false;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_play);
        setAnswers();
        final ImageView imageView = findViewById(R.id.imageView);
        rand = new Random();
        currImage = rand.nextInt(3);
        imageView.setImageResource(images[currImage]);
        bm = BitmapFactory.decodeResource(getResources(), labels[currImage]);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getX(), y = (int)event.getY();
                float scaleHeight = (float) imageView.getDrawable().getIntrinsicHeight() / imageView.getMeasuredHeight(), scaleWidth = (float) imageView.getDrawable().getIntrinsicWidth() / imageView.getMeasuredWidth();
                int pixel = bm.getPixel((int)(scaleWidth * x),(int)(scaleHeight * y));
                int redValue = Color.red(pixel);
                if (redValue == 26) score++;
                if (String.valueOf(score).equals(answers[currImage]) || score >= 1) isCorrect = true;
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
                final SharedClass obj = getObject();

                if (isCorrect) {
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

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(100);
        ProgressBarTimer progressBarTimer = new ProgressBarTimer(10000, 1, progressBar);
        final SharedClass obj = getObject();
        final Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCorrect) {
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

    //REmoving harcoding functions start from below here
    private void setAnswers() {
        for (int i = 1; i <= 9; i++) {
            answers[i - 1] = String.valueOf(getCarNum("img0" + String.valueOf(i) + ".json" ));
        }
    }

    public int getCarNum(String s) {
        int carNum = 0;
        JSONObject obj = null;
        try {
            obj = new JSONObject(loadJSONFromAsset(s));
            JSONArray m_jArry = obj.getJSONArray("objects");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("label"));
                String label_value = jo_inside.getString("label");
                if (label_value.equals("car")) {
                    carNum++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return carNum;
    }

    public String loadJSONFromAsset(String s) {
        String json = null;
        try {
            InputStream is = getAssets().open(s);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
