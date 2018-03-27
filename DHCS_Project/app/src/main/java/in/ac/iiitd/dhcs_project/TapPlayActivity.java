package in.ac.iiitd.dhcs_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TapPlayActivity extends AppCompatActivity {
    private Bitmap bitmap, bitmap2;
    private int answers[] = {0};
    private int currentImage = 0;

    private int leftX = 60;
    private int rightX = 695;
    private int topY = 250;
    private int bottomY = 350;

    private ImageCoordinates coordinateArr[] = new ImageCoordinates[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_play);

        setCoordinates(coordinateArr);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        bitmap2 = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        bitmap = bitmap2.copy(bitmap2.getConfig(), true);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                //Toast.makeText(TapMainActivity.this, String.valueOf(x) + " , " + String.valueOf(y), Toast.LENGTH_SHORT).show();
                for (int i = x - 20; i <= x + 20; i++) {
                    for (int j = y - 20; j <= y + 20; j++) {
                        bitmap.setPixel(i, j, Color.BLACK);
                    }
                }
                if (checkAnswer(x,y)) {
                    answers[0] = 1;
                }
                else {
                    finish();
                }
                imageView.setImageBitmap(bitmap);
                imageView.setImageDrawable(new BitmapDrawable(getResources(), bitmap));

                return false;
            }


        });

        final SharedClass obj = getObject();

        final Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answers[0] == 1) {
                    startQuestionActivity(obj);
                }
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

    private boolean checkAnswer(int x, int y) {
        return (x >= leftX && x <= rightX && y >= topY && y <= bottomY);
    }


    private void setCoordinates(ImageCoordinates coordinateArr[]) {

        leftX = 60;
        rightX = 695;
        topY = 250;
        bottomY = 350;

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
