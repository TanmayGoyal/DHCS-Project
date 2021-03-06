package in.ac.iiitd.dhcs_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "HomeActivity";
    private static final int RC_SIGN_IN = 9001;

    private int currentLevel = 0;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private String displayName;
    private String accountID;
    private int score = 0;
    SharedClass sharedObject;// = new SharedClass();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        sharedObject = new SharedClass();
        findViewById(R.id.sign_in_button).setOnClickListener(this);
//        findViewById(R.id.sign_out_button).setOnClickListener(this);
//        findViewById(R.id.disconnect_button).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
//        signInButton.setVisibility(View.GONE);
        // [END customize_button]
    }

//    private void signInSilently() {
//        final GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
//                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
//        signInClient.silentSignIn().addOnCompleteListener(this,
//                new OnCompleteListener<GoogleSignInAccount>() {
//                    @Override
//                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
//                        if (task.isSuccessful()) {
//                            // The signed in account is stored in the task's result.
//                            GoogleSignInAccount signedInAccount = task.getResult();
//                        } else {
//                            signIn();
//                            // Player will need to sign-in explicitly using via UI
//                        }
//                    }
//                });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        signInSilently();
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);
        alert.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        // [END on_start_sign_in]
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    // [START signOut]
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            final CircleImageView imgProfilePic = findViewById(R.id.profile_image);
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
            displayName = account.getDisplayName();
            accountID = account.getId();
            Log.e(TAG, "display name: " + displayName);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.child("users").child(accountID).exists()) {
                        Log.e(TAG, "display name: Did not exist");
                        reference.child("users").child(accountID).child("name").setValue(displayName);
                        reference.child("users").child(accountID).child("score").setValue(score);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });
//            String personPhotoUrl = account.getPhotoUrl().toString();
//
//            try {
//                Glide.with(this)
//                        .load(account.getPhotoUrl().toString())
//                        .apply(new RequestOptions()
//                                .placeholder(R.drawable.person2))
//                        .into(imgProfilePic);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            try {
                new ImageLoadTask(account.getPhotoUrl().toString(), imgProfilePic).execute();
            } catch (NullPointerException npe) {
                new ImageLoadTask("https://soygrowers.com/wp-content/uploads/2017/02/default_bio_600x600.jpg", imgProfilePic).execute();
            }

//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
//            case R.id.sign_out_button:
//                signOut();
//                break;
//            case R.id.disconnect_button:
//                revokeAccess();
//                break;
        }
    }

    public void goToHonkPlay(View view) {
        if (isSignedIn()) {
            Intent intent = new Intent(this, QuestionPageActivity.class);
            intent.putExtra("sharedObject", sharedObject);
            intent.putExtra("id", accountID);
            intent.putExtra("name", displayName);
            intent.putExtra("score", score);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You need to sign-in to start the game.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);
            alertDialog.show();
            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(Color.parseColor("#448AFF"));
        }
    }

    public void goToLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }
}

class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;

    public ImageLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }

}

class SharedClass implements Serializable {
    public int score;
    public int currentLevel;
    public int maxLevel;
    public String[] levelInfo;
    public String[] extraInfo;
    public int[] minImages;
    public int difficultyLevel;

    public SharedClass() {
        score = 0;
        currentLevel = 0;
        maxLevel = 4;
        difficultyLevel = 0;

        levelInfo = new String[maxLevel];
        extraInfo = new String[maxLevel];
        minImages = new int[maxLevel];

        minImages[0] = -1;
        minImages[1] = 3;
        minImages[2] = 1;
        minImages[3] = 1;

        levelInfo[1] = "Honk once per car!";
        levelInfo[2] = "Tap on all the cars!";
        levelInfo[3] = "Scratch to find the cars!";

        extraInfo[1] = "Press the horn as many number of cars in the image.";
        extraInfo[2] = "Tap on as many number of cars present in the image.";
        extraInfo[3] = "Scratch the image and then pick the right option from the available set of options.";
    }

    public void incrementCurrentLevel() {
        this.currentLevel++;

        if (currentLevel == maxLevel) {

            currentLevel = 1;
            this.difficultyLevel++;
        }
    }

    public void incrementScore() {
        this.score+=10;
    }

    public void incrementScore(int score) {
        this.score+=score;
    }

    public int getScore() {
        return score;
    }
}