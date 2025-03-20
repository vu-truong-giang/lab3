package com.example.lab2_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.txtName.setText(contact.getName());
        holder.txtPhoneNumber.setText(contact.getPhoneNumber());

        // Hiển thị ảnh đại diện nếu có
        if (contact.getImagePath() != null && !contact.getImagePath().isEmpty()) {
            holder.imgAvatar.setImageURI(android.net.Uri.parse(contact.getImagePath()));
        } else {
            holder.imgAvatar.setImageResource(R.mipmap.ic_launcher_round);
        }

        // Xử lý sự kiện khi checkbox thay đổi
        holder.chkSelect.setChecked(contact.isSelected());
        holder.chkSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            contact.setSelected(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName, txtPhoneNumber;
        CheckBox chkSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            chkSelect = itemView.findViewById(R.id.chkSelect);
        }
    }

    // Xóa các contact được chọn
    public void deleteSelectedContacts() {
        contactList.removeIf(Contact::isSelected);
        notifyDataSetChanged();
    }
}
