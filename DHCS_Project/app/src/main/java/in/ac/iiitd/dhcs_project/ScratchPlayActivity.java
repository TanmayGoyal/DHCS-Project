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
import android.widget.Toast;

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

    Random rand;

    private String answers[] = new  String[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_play);

        setAnswers();
        rand = new Random();
        currImage = rand.nextInt(9);

        final ImageView imageView = (ImageView) findViewById(R.id.sample_image);
        imageView.setImageResource(images[currImage]);

        Button button1 = (Button) findViewById(R.id.optionButton1);
        Button button2 = findViewById(R.id.optionButton2);
        Button button3 = findViewById(R.id.optionButton3);
        Button button4 = findViewById(R.id.optionButton4);

        int trueAnswer = Integer.parseInt(answers[currImage]);
        button2.setText(String.valueOf(trueAnswer));
        button1.setText(String.valueOf(trueAnswer + 1));
        button3.setText(String.valueOf(trueAnswer - 1));
        button4.setText(String.valueOf(trueAnswer + 2));

        final SharedClass obj = getObject();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScratchPlayActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(ScratchPlayActivity.this).create();
                alertDialog.setTitle("Wrong Answer");
                alertDialog.setMessage("Sorry, You Lost!");
                alertDialog.setIcon(R.drawable.wrong);
                alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ScratchPlayActivity.this, HomeActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScratchPlayActivity.this, "CORRECT", Toast.LENGTH_SHORT).show();
                finish();
                startQuestionActivity(obj);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScratchPlayActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(ScratchPlayActivity.this).create();
                alertDialog.setTitle("Wrong Answer");
                alertDialog.setMessage("Sorry, You Lost!");
                alertDialog.setIcon(R.drawable.wrong);
                alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ScratchPlayActivity.this, HomeActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScratchPlayActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(ScratchPlayActivity.this).create();
                alertDialog.setTitle("Wrong Answer");
                alertDialog.setMessage("Sorry, You Lost!");
                alertDialog.setIcon(R.drawable.wrong);
                alertDialog.setButton("HOME", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ScratchPlayActivity.this, HomeActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });
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
