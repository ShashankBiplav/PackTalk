package com.example.packtalk.activities.allactivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packtalk.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class CreatePost extends AppCompatActivity implements View.OnClickListener {

    //UI components
    private TextView cPActivityUsername, cPActivityEmail;
    private EditText editTextUserCPActivity;
    private Button buttonUploadPostCPActivity;
    private ImageView profilePictureCPActivity, imageViewCPActivityUploadImage, imageViewBackButtonCPActivity;
    private Bitmap receivedImageBitmap;
    private ParseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        cPActivityUsername =findViewById(R.id.cPActivityUsername);
        cPActivityEmail = findViewById(R.id.cPActivityEmail);
        editTextUserCPActivity = findViewById(R.id.editTextUserCPActivity);
        buttonUploadPostCPActivity = findViewById(R.id.buttonUploadPostCPActivity);
        profilePictureCPActivity = findViewById(R.id.profilePictureCPActivity);
        imageViewCPActivityUploadImage = findViewById(R.id.imageViewCPActivityUploadImage);
        imageViewBackButtonCPActivity = findViewById(R.id.imageViewBackButtonCPActivity);
        //calling parseServer for user information access
        currentUser = ParseUser.getCurrentUser();

        //setting up profile picture from parse server
        ParseFile profilePicture = (ParseFile) currentUser.get("profilePicture");
        if(profilePicture !=null) {
            profilePicture.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (data != null && e == null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        profilePictureCPActivity.setImageBitmap(bitmap);
                    } else {
                        profilePictureCPActivity.setImageResource(R.drawable.ic_defaultprofilepicture);
                    }
                }
            });
        }else {
            profilePictureCPActivity.setImageResource(R.drawable.ic_defaultprofilepicture);
        }
        cPActivityUsername.setText(currentUser.getUsername());
        cPActivityEmail.setText(currentUser.getEmail());

        //setting on click listener on image view and button
        imageViewCPActivityUploadImage.setOnClickListener(CreatePost.this);
        buttonUploadPostCPActivity.setOnClickListener(CreatePost.this);
        imageViewBackButtonCPActivity.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageViewCPActivityUploadImage:
                //if user's device has Android Marshmallow or API 23 or above and whether user has already given
                //the permission is not equal to Permission Granted then ask for permission again
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                }
                else{

                    getChosenImage();

                }
                break;
            case R.id.buttonUploadPostCPActivity:

                if (receivedImageBitmap != null ){
                    if (editTextUserCPActivity.getText().toString().equals("")){
                        FancyToast.makeText(CreatePost.this,
                                "Please say something , description cannot be empty !" ,
                                Toast.LENGTH_LONG, FancyToast.WARNING,
                                true).show();
                    }
                    else {

                        //uploading the Image to the parse server
                        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("uploaded.png",bytes);
                        ParseObject parseObject = new ParseObject("Posts");
                        parseObject.put("uploadedImage", parseFile);

                        parseObject.put("description",editTextUserCPActivity.getText().toString());
                        parseObject.put("username",ParseUser.getCurrentUser().getUsername());
                        parseObject.put("email",ParseUser.getCurrentUser().getEmail());

                        final ProgressDialog progressDialog = ProgressDialog.show(this,
                                "Uploading Post", "Please wait...", true);


                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(CreatePost.this,
                                            currentUser.getUsername() + "'s post has been uploaded successfully!",
                                            Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                            true).show();
                                    Intent intent = new Intent(CreatePost.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    FancyToast.makeText(CreatePost.this,
                                            "An error occurred!" + "\n" + e.getMessage(),
                                            Toast.LENGTH_LONG, FancyToast.ERROR,
                                            true).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

                    }
                }
                else{
                    FancyToast.makeText(CreatePost.this,
                            "Please select an image to upload !" ,
                            Toast.LENGTH_LONG, FancyToast.WARNING,
                            true).show();
                }

                break;
            case R.id.imageViewBackButtonCPActivity:
                Intent intent = new Intent(CreatePost.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    private void getChosenImage() {
//        FancyToast.makeText(CreatePost.this, "now we can access images",
//                Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000){

            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){

                getChosenImage();

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000){

            if (resultCode == Activity.RESULT_OK){

                try {
                    Uri selectedImage = data.getData();
                    String[] filePathCoulmn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().
                            query(selectedImage, filePathCoulmn, null, null,null);
                    cursor.moveToFirst();
                    int coulmnIndex = cursor.getColumnIndex(filePathCoulmn[0]);
                    String picturePath = cursor.getString(coulmnIndex);
                    cursor.close();

                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imageViewCPActivityUploadImage.setImageBitmap(receivedImageBitmap);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
    }
}
