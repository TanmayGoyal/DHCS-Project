package in.ac.iiitd.dhcs_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        final SharedClass sharedObject = getObject();
        setActivityText(sharedObject);

        Button button = (Button) findViewById(R.id.StartButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startNewActivity(sharedObject);
            }
        });

    }

    public void startNewActivity(SharedClass sharedObject) {
        Intent intent;
        if (sharedObject.currentLevel == 1) {
            intent = new Intent(this, HonkPlayActivity.class);
            intent.putExtra("sharedObject", sharedObject);
        }
        else if (sharedObject.currentLevel == 2) {
            intent = new Intent(this, TapPlayActivity.class);
            intent.putExtra("sharedObject", sharedObject);
        }
        else  {
            //ADD INTENT FOR LEVEL3 ACTIVITY
            intent = new Intent(this, HonkPlayActivity.class);
            intent.putExtra("sharedObject", sharedObject);
        }
        startActivity(intent);

    }

    private void setActivityText(SharedClass sharedObject) {
        TextView levelText = findViewById(R.id.levelText);
        TextView levelInformation = findViewById(R.id.levelInformation);


        sharedObject.incrementCurrentLevel();

        levelText.setText("Level " + Integer.toString(sharedObject.currentLevel));
        levelInformation.setText(sharedObject.levelInfo[sharedObject.currentLevel]);
    }
    private SharedClass getObject() {
        Intent i = getIntent();
        return (SharedClass) i.getSerializableExtra("sharedObject");
    }
    private void addToast(String value) {

        CharSequence text = value;
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }
}
