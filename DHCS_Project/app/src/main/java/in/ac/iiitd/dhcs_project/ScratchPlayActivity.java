package in.ac.iiitd.dhcs_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ScratchPlayActivity extends AppCompatActivity {

    private int answers[] = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_play);

        Button button1 = (Button) findViewById(R.id.optionButton1);
        Button button2 = findViewById(R.id.optionButton2);
        Button button3 = findViewById(R.id.optionButton3);
        Button button4 = findViewById(R.id.optionButton4);
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
}
