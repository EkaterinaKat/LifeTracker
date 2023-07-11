package com.katyshevtseva.lifetracker.db;

import static com.katyshevtseva.lifetracker.core.utils.Utils.getDateByUnixTime;
import static com.katyshevtseva.lifetracker.core.utils.Utils.getUnixTimeByDate;
import static com.katyshevtseva.lifetracker.db.DbConstants.ACTIVITY_ID;
import static com.katyshevtseva.lifetracker.db.DbConstants.BEGIN_TIME;
import static com.katyshevtseva.lifetracker.db.DbConstants.ID;
import static com.katyshevtseva.lifetracker.db.DbTable.ColumnActualType.LONG;

import android.database.sqlite.SQLiteDatabase;

import com.katyshevtseva.lifetracker.core.entity.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryDao extends AbstractDao<Entry> {
    static final String NAME = "entry";

    EntryDao(SQLiteDatabase database) {
        super(database, new DbTable<>(NAME, createIdColumn(), createColumns(), Entry::new));
    }

    private static DbTable.Column<Entry> createIdColumn() {
        return new DbTable.Column<>(ID, LONG, Entry::getId,
                (entry, o) -> entry.setId((Long) o));
    }

    private static List<DbTable.Column<Entry>> createColumns() {
        List<DbTable.Column<Entry>> columns = new ArrayList<>();

        columns.add(new DbTable.Column<>(ACTIVITY_ID, LONG, Entry::getActivityId,
                (entry, o) -> entry.setActivityId(((Long) o))));

        columns.add(new DbTable.Column<>(BEGIN_TIME, LONG,
                entry -> getUnixTimeByDate(entry.getBegin()),
                (entry, o) -> entry.setBegin(getDateByUnixTime((long) o))));

        return columns;
    }
}
