package com.example.androidassignment1;

public class User {
    String name, gender, birthdate, country, address, photouri;
    int age, id;

    User(String n, String g, String b, int a, String c, String ad, int i, String pu){
        this.name = n;
        this.gender = g;
        this.birthdate = b;
        this.age = a;
        this.country = c;
        this.id = i;
        this.photouri = pu;
        if(ad.length() > 0)
            this.address = ad;
        else
            this.address = "";
    }
}
