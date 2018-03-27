package in.ac.iiitd.dhcs_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class QuestionPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        Intent i = getIntent();
        SharedClass sharedObject = (SharedClass) i.getSerializableExtra("sharedObject");
        
        addToast(sharedObject.levelInfo[1]);
    }

    private void addToast(String value) {

        CharSequence text = value;
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }
}
