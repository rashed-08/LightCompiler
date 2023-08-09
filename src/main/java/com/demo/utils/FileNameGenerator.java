package com.demo.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public enum FileNameGenerator {
    GENERATOR;
    private Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    public String getFileName() {
        Date today = Calendar.getInstance().getTime();
        String date = formatter.format(today);
        return date;
    }

}
