package com.example.lab2_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewContacts;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;
    private Button btnAddContact, btnDeleteSelected;
    private DatabaseHelper databaseHelper;

    private static final int ADD_CONTACT_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 📌 Khởi tạo Database khi ứng dụng chạy
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase(); // Mở database, nếu chưa có thì tạo mới

        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);
        btnAddContact = findViewById(R.id.btnAddContact);
        btnDeleteSelected = findViewById(R.id.btnDeleteSelected);

        // 📌 Nạp danh sách Contact từ SQLite
        contactList = databaseHelper.getAllContacts();

        contactAdapter = new ContactAdapter(this, contactList);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(contactAdapter);

        // 📌 Xóa các Contact đã chọn khỏi SQLite
        btnDeleteSelected.setOnClickListener(view -> {
            List<Contact> selectedContacts = new ArrayList<>();
            for (Contact contact : contactList) {
                if (contact.isSelected()) {
                    selectedContacts.add(contact);
                }
            }
            databaseHelper.deleteSelectedContacts(selectedContacts);
            contactList.removeAll(selectedContacts);
            contactAdapter.notifyDataSetChanged();
        });

        // 📌 Mở màn hình thêm Contact
        btnAddContact.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, ADD_CONTACT_REQUEST);
        });
    }


    // 📌 Nhận dữ liệu Contact mới và lưu vào SQLite
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String phoneNumber = data.getStringExtra("phoneNumber");
            String imagePath = data.getStringExtra("imagePath");

            Contact newContact = new Contact(contactList.size() + 1, name, phoneNumber, imagePath);
            databaseHelper.addContact(newContact); // 📌 Lưu vào SQLite
            contactList.add(newContact);
            contactAdapter.notifyDataSetChanged();
        }
    }
}
