package com.katyshevtseva.lifetracker.db;

import static com.katyshevtseva.lifetracker.db.DbConstants.BEGIN_TIME;
import static com.katyshevtseva.lifetracker.db.DbConstants.ID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.katyshevtseva.lifetracker.core.entity.Activity;
import com.katyshevtseva.lifetracker.core.entity.Entry;

import java.util.List;

public class DlDao {
    private final EntryDao entryDao;
    private final ActivityDao activityDao;

    public DlDao(Context context) {
        SQLiteDatabase database = new DbHelper(context).getWritableDatabase();
        entryDao = new EntryDao(database);
        activityDao = new ActivityDao(database);
    }

    ////////////////////////////  Activity  //////////////////////////////////

    public void saveNew(Activity activity) {
        activityDao.saveNew(activity);
    }

    public List<Activity> getAllActivities() {
        return activityDao.findAll();
    }

    public Activity getActivityById(long id) {
        return activityDao.findFirst(ID, "" + id);
    }

    public void update(Activity activity) {
        activityDao.update(activity);
    }

    public void delete(Activity activity) {
        activityDao.delete(activity);
    }

    ////////////////////////////  Entry  //////////////////////////////////

    public void saveNew(Entry entry) {
        entryDao.saveNew(entry);
    }

    public List<Entry> getAllEntries() {
        return entryDao.findAll();
    }

    public void delete(Entry entry) {
        entryDao.delete(entry);
    }

    public List<Entry> getAllEntriesOrderByDate() {
        return entryDao.find(null, null, BEGIN_TIME, null);
    }

    public Entry findLatestEntry() {
        List<Entry> entries = entryDao.find(null, null, BEGIN_TIME + " DESC ", "1");
        return entries.isEmpty() ? null : entries.get(0);
    }
}
