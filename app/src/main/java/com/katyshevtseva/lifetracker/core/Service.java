package com.katyshevtseva.lifetracker.core;

import static com.katyshevtseva.lifetracker.core.utils.TimeUtils.after;
import static com.katyshevtseva.lifetracker.core.utils.TimeUtils.getTime;
import static com.katyshevtseva.lifetracker.core.utils.TimeUtils.setTime;
import static com.katyshevtseva.lifetracker.core.utils.Utils.READABLE_DATE_FORMAT;
import static com.katyshevtseva.lifetracker.core.utils.Utils.READABLE_TIME_FORMAT;
import static com.katyshevtseva.lifetracker.core.utils.Utils.addDays;
import static com.katyshevtseva.lifetracker.core.utils.Utils.equalsIgnoreTime;
import static com.katyshevtseva.lifetracker.core.utils.Utils.isEmpty;

import com.katyshevtseva.lifetracker.core.entity.Activity;
import com.katyshevtseva.lifetracker.core.entity.Entry;
import com.katyshevtseva.lifetracker.core.utils.Time;
import com.katyshevtseva.lifetracker.db.DlDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    public static Service INSTANCE;
    private final DlDao dao;

    public static void init(DlDao dao) {
        INSTANCE = new Service(dao);
    }

    private Service(DlDao dao) {
        this.dao = dao;
    }

    public List<Activity> getActivities() {
        return dao.getAllActivities();
    }

    public void undoLastEntry() {
        dao.delete(dao.findLatestEntry());
    }

    public List<String> getEntries() {
        List<Entry> entries = dao.getAllEntriesOrderByDate();
        List<String> result = new ArrayList<>();

        if (entries.isEmpty()) {
            return result;
        }

        Date currentDate = null;
        for (Entry entry : entries) {
            if (currentDate == null || !equalsIgnoreTime(entry.getBegin(), currentDate)) {
                currentDate = entry.getBegin();
                result.add(" *** " + READABLE_DATE_FORMAT.format(currentDate) + " *** ");
            }
            result.add(READABLE_TIME_FORMAT.format(entry.getBegin())
                    + " " + dao.getActivityById(entry.getActivityId()).getTitle());
        }

        return result;
    }

    public void saveActivity(Activity existing, String title) {
        if (isEmpty(title)) {
            return;
        }
        title = title.trim();
        if (existing == null) {
            existing = new Activity();
            existing.setTitle(title);
            dao.saveNew(existing);
        } else {
            existing.setTitle(title);
            dao.update(existing);
        }
    }

    public String getCurrentStateInfo() {
        Entry entry = dao.findLatestEntry();
        if (entry == null)
            return "There is no started activity";
        Activity activity = dao.getActivityById(entry.getActivityId());

        String time;
        if (equalsIgnoreTime(entry.getBegin(), new Date())) {
            time = "at " + READABLE_TIME_FORMAT.format(entry.getBegin());
        } else if (equalsIgnoreTime(entry.getBegin(), addDays(new Date(), -1))) {
            time = "yesterday at " + READABLE_TIME_FORMAT.format(entry.getBegin());
        } else {
            time = READABLE_DATE_FORMAT.format(entry.getBegin()) + " at " + READABLE_TIME_FORMAT.format(entry.getBegin());
        }

        return activity.getTitle() + " was started " + time;
    }

    public void startActivity(Activity activity, Time time) {
        Entry entry = new Entry();
        entry.setActivityId(activity.getId());
        Date date = time == null ? new Date() : changeDatesTime(new Date(), time);
        entry.setBegin(date);
        dao.saveNew(entry);
    }

    private Date changeDatesTime(Date date, Time time) {
        if (after(time, getTime(date))) {
            date = addDays(date, -1);
        }
        return setTime(date, time);
    }
}
