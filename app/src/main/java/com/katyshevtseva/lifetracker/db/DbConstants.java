package com.katyshevtseva.lifetracker.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

class DbConstants {
    static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm Z");

    static final String ID = "id";
    static final String TITLE = "title";
    static final String ACTIVITY_ID = "activity_id";
    static final String BEGIN_TIME = "begin_time";
}
