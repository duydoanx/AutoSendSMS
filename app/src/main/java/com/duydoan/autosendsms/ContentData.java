package com.duydoan.autosendsms;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ContentData {
    @PrimaryKey (autoGenerate = true)
    public int id = 0;
    public String code, number, text;

    public ContentData(String code, String number, String text) {
        this.code = code;
        this.number = number;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ContentData{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", number='" + number + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
