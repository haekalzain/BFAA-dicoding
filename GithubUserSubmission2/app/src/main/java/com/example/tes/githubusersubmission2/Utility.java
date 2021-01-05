package com.example.tes.githubusersubmission2;

public class Utility {
    public static String getData(String data) {
        if (data != null && !data.isEmpty() && !data.equals("null"))
            return data;
        return "-";
    }
    public static class Constants {
        public static final String EXTRA_DATA = "extra_data";
    }
}
