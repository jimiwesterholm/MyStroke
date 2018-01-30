package com.example.jimi.mystroke;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by jimi on 16/01/2018.
 */

public class PersistenceTypeConverters {

    @TypeConverter
    public long dateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public long timeToLong(Time time) {
        return time.getTime();
    }

    @TypeConverter
    public Date longToDate(long l) {
        return new Date(l);
    }

    @TypeConverter
    public Time longToTime(long l) {
        return new Time(l);
    }

}
