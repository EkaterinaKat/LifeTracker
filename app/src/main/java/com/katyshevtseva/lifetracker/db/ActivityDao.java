package com.katyshevtseva.lifetracker.db;

import static com.katyshevtseva.lifetracker.db.DbConstants.ID;
import static com.katyshevtseva.lifetracker.db.DbConstants.TITLE;
import static com.katyshevtseva.lifetracker.db.DbTable.ColumnActualType.LONG;
import static com.katyshevtseva.lifetracker.db.DbTable.ColumnActualType.STRING;

import android.database.sqlite.SQLiteDatabase;

import com.katyshevtseva.lifetracker.core.entity.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityDao extends AbstractDao<Activity> {
    static final String NAME = "activity";

    ActivityDao(SQLiteDatabase database) {
        super(database, new DbTable<>(NAME, createIdColumn(), createColumns(), Activity::new));
    }

    private static DbTable.Column<Activity> createIdColumn() {
        return new DbTable.Column<>(ID, LONG, Activity::getId,
                (log, o) -> log.setId((Long) o));
    }

    private static List<DbTable.Column<Activity>> createColumns() {
        List<DbTable.Column<Activity>> columns = new ArrayList<>();

        columns.add(new DbTable.Column<>(TITLE, STRING, Activity::getTitle,
                (activity, o) -> activity.setTitle((String) o)));

        return columns;
    }
}
