package com.example.lab2_2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {

    private EditText edtName, edtPhoneNumber;
    private Button btnChooseImage, btnSave;
    private ImageView imgPreview;
    private String imagePath = "";

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        edtName = findViewById(R.id.edtName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSave = findViewById(R.id.btnSave);
        imgPreview = findViewById(R.id.imgPreview);

        // Xử lý chọn ảnh từ bộ nhớ
        btnChooseImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Lưu Contact và trả về MainActivity
        btnSave.setOnClickListener(view -> saveContact());
    }

    // Xử lý kết quả chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imagePath = selectedImageUri.toString();
                imgPreview.setImageURI(selectedImageUri);
                imgPreview.setVisibility(View.VISIBLE);
            }
        }
    }

    // Lưu Contact
    private void saveContact() {
        String name = edtName.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();

        if (name.isEmpty() || phoneNumber.isEmpty()) {
            edtName.setError("Nhập tên");
            edtPhoneNumber.setError("Nhập số điện thoại");
            return;
        }

        // 📌 Tạo Contact mới và trả về MainActivity
        Contact newContact = new Contact(0, name, phoneNumber, imagePath);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("phoneNumber", phoneNumber);
        resultIntent.putExtra("imagePath", imagePath);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
