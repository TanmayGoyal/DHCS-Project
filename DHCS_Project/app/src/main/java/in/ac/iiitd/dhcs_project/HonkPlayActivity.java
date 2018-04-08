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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class HonkPlayActivity extends AppCompatActivity {
    private static final String TAG = "HonkPlayActivity";

    private Integer images[] = {R.drawable.img01_original, R.drawable.img02_original, R.drawable.img03_original, R.drawable.img04_original, R.drawable.img05_original, R.drawable.img06_original, R.drawable.img07_original, R.drawable.img08_original, R.drawable.img09_original};
    private Integer labels[] = {R.drawable.img01_label, R.drawable.img02_label, R.drawable.img03_label, R.drawable.img04_label, R.drawable.img05_label, R.drawable.img06_label, R.drawable.img07_label, R.drawable.img08_label, R.drawable.img09_label};
    private int currImage;

    Random rand;

    private String answers[] = new  String[9];
    private int numOfClicks = 0;
    private int correctAnswers = 0;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honk_play);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("message");

        databaseReference.setValue("Hello World!");

        rand = new Random();
        currImage = rand.nextInt(9);

        setAnswers();
        setCurrentImage();

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
                if (correctAnswers >= obj.difficultyLevel+1) {
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

        final ProgressBarTimer progressBarTimer;
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


        final Button rotateButton = findViewById(R.id.go);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (String.valueOf(numOfClicks).equals(answers[currImage])) {
                    setNewImage();
                    addToast("Correct Answer");
                    correctAnswers++;
                }

                else {
                    progressBarTimer.cancel();
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

    private void setNewImage() {
        int prevcurrImage = currImage;
        while (prevcurrImage == currImage) {
            currImage = rand.nextInt(9);
        }
    }

    private void setCurrentImage() {
        final ImageView imageView = findViewById(R.id.imageDisplay);
//        imageView.setImageResource(images[currImage]);
//        Log.i(TAG, storageReference.toString());
        Glide.with(this)
                .load(images[currImage])
                .into(imageView);
    }

    private void addToast(String value) {
        CharSequence text = value;
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
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