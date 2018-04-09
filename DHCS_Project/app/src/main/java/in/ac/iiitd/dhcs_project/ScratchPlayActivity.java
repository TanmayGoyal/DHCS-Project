package in.ac.iiitd.dhcs_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cooltechworks.utils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class ScratchPlayActivity extends AppCompatActivity {
    private Integer images[] = {R.drawable.img01_original, R.drawable.img02_original, R.drawable.img03_original, R.drawable.img04_original, R.drawable.img05_original, R.drawable.img06_original, R.drawable.img07_original, R.drawable.img08_original, R.drawable.img09_original};
    private Integer labels[] = {R.drawable.img01_label, R.drawable.img02_label, R.drawable.img03_label, R.drawable.img04_label, R.drawable.img05_label, R.drawable.img06_label, R.drawable.img07_label, R.drawable.img08_label, R.drawable.img09_label};
    private int currImage;

    SharedClass obj;
    TextView scoreText;


    Random rand;

    private String answers[] = new  String[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_play);

        setAnswers();
        rand = new Random();
        int randButton = (rand.nextInt() % 4) + 1;

        currImage = rand.nextInt(9);


        obj = getObject();
        scoreText = findViewById(R.id.scoreTextBox3);
        scoreText.setText("Score: " + Integer.toString(obj.score));

        final ImageView imageView = (ImageView) findViewById(R.id.sample_image);
        imageView.setImageResource(images[currImage]);

        Button button1 = (Button) findViewById(R.id.optionButton1);
        Button button2 = findViewById(R.id.optionButton2);
        Button button3 = findViewById(R.id.optionButton3);
        Button button4 = findViewById(R.id.optionButton4);

        int trueAnswer = Integer.parseInt(answers[currImage]);

        setButtons(button1, button2, button3, button4, randButton, trueAnswer);
    }


    private void setButtons(Button but1, Button but2, Button but3, Button but4, int randomButton, int trueAns) {
        if (randomButton == 1) {
            but1.setText(String.valueOf(trueAns));
            but2.setText(String.valueOf(trueAns - 1));
            but3.setText(String.valueOf(trueAns + 2));
            but4.setText(String.valueOf(trueAns + 1));
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScratchPlayActivity.this, "CORRECT", Toast.LENGTH_SHORT).show();
                    obj.incrementScore();
                    scoreText.setText("Score:" + Integer.toString(obj.score));
                    startQuestionActivity(obj);
                    finish();
                }
            });
            but2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
            but3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
            but4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
        }
        else if(randomButton == 2) {
            but1.setText(String.valueOf(trueAns - 1));
            but2.setText(String.valueOf(trueAns));
            but3.setText(String.valueOf(trueAns + 2));
            but4.setText(String.valueOf(trueAns + 1));
            but2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScratchPlayActivity.this, "CORRECT", Toast.LENGTH_SHORT).show();
                    obj.incrementScore();
                    scoreText.setText("Score:" + Integer.toString(obj.score));
                    startQuestionActivity(obj);
                    finish();
                }
            });
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
            but3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
            but4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
        }
        else if(randomButton == 3) {
            but1.setText(String.valueOf(trueAns + 2));
            but2.setText(String.valueOf(trueAns - 1));
            but3.setText(String.valueOf(trueAns));
            but4.setText(String.valueOf(trueAns + 1));
            but3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScratchPlayActivity.this, "CORRECT", Toast.LENGTH_SHORT).show();
                    obj.incrementScore();
                    scoreText.setText("Score:" + Integer.toString(obj.score));
                    startQuestionActivity(obj);
                    finish();
                }
            });
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
            but2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
            but4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
        }
        else if(randomButton == 4) {
            but1.setText(String.valueOf(trueAns - 1));
            but2.setText(String.valueOf(trueAns + 1));
            but3.setText(String.valueOf(trueAns + 2));
            but4.setText(String.valueOf(trueAns));
            but4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScratchPlayActivity.this, "CORRECT", Toast.LENGTH_SHORT).show();
                    obj.incrementScore();
                    scoreText.setText("Score:" + Integer.toString(obj.score));
                    startQuestionActivity(obj);
                    finish();
                }
            });
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
            but2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
            but3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlertDialog();
                }
            });
        }
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

    private void getAlertDialog() {
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(ScratchPlayActivity.this).create();
        alertDialog.setTitle("Wrong Answer");
        alertDialog.setMessage("Sorry, You Lost!");
        alertDialog.setIcon(R.drawable.wrong);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ScratchPlayActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });
        alertDialog.show();
    }
}
