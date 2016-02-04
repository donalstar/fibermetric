package com.guggiemedia.fibermetric.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * models act as containers, bridge between database and runtime
 *
 * children should implement equals()/hashCode() without comparing row ID
 */
public interface DataBaseModel {

    /**
     * set reasonable default values
     */
    void setDefault();

    /**
     *
     * @return load content from model
     */
    ContentValues toContentValues();

    /**
     *
     * @param cursor convert from cursor to model
     */
    void fromCursor(Cursor cursor);

    /**
     *
     * @return associated table name
     */
    String getTableName();

    /**
     *
     * @return associated table URI
     */
    Uri getTableUri();

    // for BaseColumns
    Long getId();
    void setId(Long id);
}
