package com.katyshevtseva.lifetracker.db;

import static com.katyshevtseva.lifetracker.db.DbConstants.ID;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.katyshevtseva.lifetracker.db.DbTable.Column;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class AbstractDao<T extends Entity> {
    protected final SQLiteDatabase database;
    private final DbTable<T> table;

    void saveNew(T t) {
        ContentValues values = getContentValues(t);
        database.insert(table.getName(), null, values);
    }

    List<T> findAll() {
        List<T> items = new ArrayList<>();
        try (KomCursorWrapper cursor = getCursor()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getT());
                cursor.moveToNext();
            }
        }
        return items;
    }

    T findFirst(String columnName, String value) {
        Cursor cursor = database.query(table.getName(), null, columnName + "=?",
                new String[]{"" + value}, null, null, null, null);

        try (KomCursorWrapper cursorWrapper = new KomCursorWrapper(cursor)) {
            cursorWrapper.moveToFirst();
            if (!cursorWrapper.isAfterLast()) {
                return cursorWrapper.getT();
            }
        }
        return null;
    }

    List<T> find(String[] columnNames, String[] values, String orderBy, String limit) {
        String selection = null;
        if (columnNames != null) {
            StringBuilder selectionBuilder = new StringBuilder(columnNames[0]).append(" = ?");
            for (int i = 1; i < columnNames.length; i++) {
                selectionBuilder.append(" and ").append(columnNames[i]).append(" = ?");
            }
            selection = selectionBuilder.toString();
        } else {
            values = null;
        }

        List<T> result = new ArrayList<>();
        Cursor cursor = database.query(table.getName(), null, selection,
                values, null, null, orderBy, limit);

        try (KomCursorWrapper cursorWrapper = new KomCursorWrapper(cursor)) {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                result.add(cursorWrapper.getT());
                cursor.moveToNext();
            }
        }
        return result;
    }

    public T findLastAdded() {
        Cursor cursor = database.query(table.getName(), null, null,
                null, null, null, " id DESC ", "1");

        try (KomCursorWrapper cursorWrapper = new KomCursorWrapper(cursor)) {
            cursorWrapper.moveToFirst();
            if (!cursorWrapper.isAfterLast()) {
                return cursorWrapper.getT();
            }
        }
        return null;
    }

    public void update(T t) {
        update(t, ID, "" + t.getId());
    }

    void update(T t, String columnName, String value) {
        ContentValues values = getContentValues(t);
        String selection = columnName + "=?";
        String[] selectionArgs = {value};
        database.update(
                table.getName(),
                values,
                selection,
                selectionArgs);
    }

    public void delete(T t) {
        delete(ID, "" + t.getId());
    }

    void delete(String columnName, String value) {
        String selection = columnName + "=?";
        String[] selectionArgs = {value};
        database.delete(table.getName(), selection, selectionArgs);
    }

    private KomCursorWrapper getCursor() {
        Cursor cursor = database.query(table.getName(), null, null, null,
                null, null, null);
        return new KomCursorWrapper(cursor);
    }

    private ContentValues getContentValues(T t) {
        ContentValues values = new ContentValues();
        for (Column<T> column : table.getContentColumns()) {
            values.put(column.getName(), column.getDbValueByActualValue(t));
        }
        return values;
    }

    class KomCursorWrapper extends CursorWrapper {
        KomCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        T getT() {
            T t = table.getEmptyObjectSupplier().execute();
            for (Column<T> column : table.getAllColumns()) {
                switch (column.getDbType()) {
                    case STRING:
                        column.getActualValueReceiver().execute(t, column.getActualValueByDbValue(getString(getColumnIndex(column.getName()))));
                        break;
                    case LONG:
                        column.getActualValueReceiver().execute(t, column.getActualValueByDbValue(getLong(getColumnIndex(column.getName()))));
                }
            }
            return t;
        }
    }
}
