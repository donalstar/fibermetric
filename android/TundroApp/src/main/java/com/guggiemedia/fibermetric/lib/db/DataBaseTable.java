package com.guggiemedia.fibermetric.lib.db;

/**
 * common parent for table abstraction
 */
public interface DataBaseTable {

    /**
     * @return associated table name
     */
    String getTableName();

    /**
     *
     * @return default sort order
     */
    String getDefaultSortOrder();

    /**
     *
     * @return default projection (column names);
     */
    String[] getDefaultProjection();
}
