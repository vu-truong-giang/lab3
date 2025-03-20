package com.example.lab2_2;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;
    private String imagePath; // Đường dẫn ảnh đại diện
    private boolean isSelected;

    public Contact(int id, String name, String phoneNumber, String imagePath) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imagePath = imagePath;
        this.isSelected = false;
    }

    // Getter và Setter
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getImagePath() { return imagePath; }
    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected) { isSelected = selected; }
}

