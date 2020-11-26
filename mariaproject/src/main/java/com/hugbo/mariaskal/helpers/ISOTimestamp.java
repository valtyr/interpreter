package com.hugbo.mariaskal.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISOTimestamp {
    public static String getISOTimestamp() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone
                                                                          // offset
        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
