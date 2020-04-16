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
import android.widget.Toast;

import com.example.packtalk.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class UpdateProfile extends AppCompatActivity implements View.OnClickListener{

    //UI components
    private EditText editTextProfileNameUPActivity, editTextBioUPActivity,
            editTextEmailUPActivity,editTextProfessionUPActivity,editTextHobbiesUPActivity;
    private ImageView profilePictureUPActivity, imageViewBackButtonUPActivity;
    private Button buttonUpdateProfileUPActivity;
    private Bitmap receivedImageBitmap;

    //getting the current user info that is logged into the application
    final ParseUser parseUser =ParseUser.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        //binding UI components to respective ID's
        editTextProfileNameUPActivity =findViewById(R.id.editTextProfileNameUPActivity);
        editTextBioUPActivity = findViewById(R.id.editTextBioUPActivity);
        editTextEmailUPActivity = findViewById(R.id.editTextEmailUPActivity);
        editTextProfessionUPActivity = findViewById(R.id.editTextProfessionUPActivity);
        editTextHobbiesUPActivity = findViewById(R.id.editTextHobbiesUPActivity);
        profilePictureUPActivity = findViewById(R.id.profilePictureUPActivity);
        buttonUpdateProfileUPActivity = findViewById(R.id.buttonUpdateProfileUPActivity);
        imageViewBackButtonUPActivity = findViewById(R.id.imageViewBackButtonUPActivity);

        //getting the current user info that is logged into the application
        final ParseUser parseUser =ParseUser.getCurrentUser();

        //do something for the empty values of editTexts on the first bootup..
        //please don't display null

        //filling up data of user from the server
        //don't call toString() method as it will cause null pointer exception during first runtime
        editTextProfileNameUPActivity.setText(parseUser.get("username")+"");
        editTextBioUPActivity.setText(parseUser.get("profileBio")+"");
        editTextEmailUPActivity.setText(parseUser.get("email")+"");
        editTextProfessionUPActivity.setText(parseUser.get("profileProfession")+"");
        editTextHobbiesUPActivity.setText(parseUser.get("profileHobbies")+"");

        //setting up profile picture from parse server
        ParseFile profilePicture = (ParseFile) parseUser.get("profilePicture");
        if(profilePicture !=null) {
            profilePicture.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (data != null && e == null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        profilePictureUPActivity.setImageBitmap(bitmap);
                    } else {
                        profilePictureUPActivity.setImageResource(R.drawable.ic_defaultprofilepicture);
                    }
                }
            });
        }
        else {
            profilePictureUPActivity.setImageResource(R.drawable.ic_defaultprofilepicture);

        }

        //setting on click listener on the button to update the profile
        buttonUpdateProfileUPActivity.setOnClickListener(UpdateProfile.this);

        //setting onClickListener on the image View of back button
        imageViewBackButtonUPActivity.setOnClickListener(UpdateProfile.this);

        //setting onClickListener on the image View of Profile picture
        profilePictureUPActivity.setOnClickListener(UpdateProfile.this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageViewBackButtonUPActivity:
                Intent intent = new Intent(UpdateProfile.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.buttonUpdateProfileUPActivity:



                if (receivedImageBitmap != null){

                    //uploading the Image to the parse server
                    ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                    receivedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile("img.png",bytes);
                    parseUser.put("profilePicture", parseFile);

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Updating...");
                    progressDialog.show();

                    parseUser.put("username",editTextProfileNameUPActivity.getText().toString());
                    parseUser.put("profileBio",editTextBioUPActivity.getText().toString());
                    parseUser.put("email",editTextEmailUPActivity.getText().toString());
                    parseUser.put("profileProfession",editTextProfessionUPActivity.getText().toString());
                    parseUser.put("profileHobbies",editTextHobbiesUPActivity.getText().toString());


                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null){
                                FancyToast.makeText(UpdateProfile.this,
                                        parseUser.getUsername() + "'s Profile has been updated successfully'",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                        true).show();
                            }
                            else{
                                FancyToast.makeText(UpdateProfile.this,
                                        "An error occurred!" + "\n" + e.getMessage(),
                                        Toast.LENGTH_LONG, FancyToast.ERROR,
                                        true).show();
                            }
                            progressDialog.dismiss();

                        }
                    });

                }
                else {

                    FancyToast.makeText(UpdateProfile.this,
                            "Please select an image for your Profile Picture !" ,
                            Toast.LENGTH_LONG, FancyToast.WARNING,
                            true).show();

                }


                break;

            case R.id.profilePictureUPActivity:

                //if user's device has Android Marshmallow or API 23 or above and whether user has already given
                //the permission is not equal to Permission Granted then ask for permission again
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                }
                else{

                    getChosenImage();

                }

        }

    }

    //function to choose image from the device
    private void getChosenImage() {

//        FancyToast.makeText(UpdateProfile.this, "now we can access images",
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
                    profilePictureUPActivity.setImageBitmap(receivedImageBitmap);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

        }

    }
}
