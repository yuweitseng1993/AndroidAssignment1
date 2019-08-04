package com.example.androidassignment1;

import android.provider.BaseColumns;

public class DatabaseUtil {
    public static final String databaseName = "accountdb";
    public static final int databaseVersion = 1;

    public class AccountTable{
        public static final String tableName = "account";
        public static final String accountNum = "id";
        public static final String nameColumn = "name";
        public static final String genderColumn = "gender";
        public static final String birthdateColumn = "birthdate";
        public static final String ageColumn = "age";
        public static final String countryColumn = "country";
        public static final String addressColumn = "address";
        public static final String photouriColumn = "photo";
    }

    public class EmailTable implements BaseColumns {
        public static final String tableName = "email";
        public static final String emailColumn = "usr_email";
    }
}
