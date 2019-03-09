package com.example.cnxlocalexperienceowner.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cnxlocalexperienceowner.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPass, edtPassConfirm;
    private Button btnRegis;
    private ImageView imgProfile;
    private ProgressBar progressRegis;

    static int PhotoReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;

//    private Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgProfile = findViewById(R.id.imgRegis);
        edtName = findViewById(R.id.edtNameRegis);
        edtEmail = findViewById(R.id.edtEmailRegis);
        edtPass = findViewById(R.id.edtPassRegis);
        edtPassConfirm = findViewById(R.id.edtConfirmPassRegis);
        progressRegis = findViewById(R.id.progressRegis);

        //Hide progress bar.
        progressRegis.setVisibility(View.INVISIBLE);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegis.setVisibility(View.INVISIBLE);
                progressRegis.setVisibility(View.VISIBLE);

                final String name = edtName.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                final String pass = edtPass.getText().toString().trim();
                final String passConfirm = edtPassConfirm.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || !pass.equals(passConfirm)) {
                    showMessage("Please verify your info again");
                } else {
                    //Everything is ok.
                    createUserAccount(email, name, pass);
                }
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    openGallery();
                }
            }
        });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESTCODE);
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PhotoReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void createUserAccount(String email, String name, String password) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null) {
            pickedImgUri = data.getData();
            imgProfile.setImageURI(pickedImgUri);
        }
    }
}
