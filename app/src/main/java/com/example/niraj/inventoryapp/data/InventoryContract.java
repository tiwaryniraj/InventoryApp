package com.example.niraj.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {
    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.niraj.inventoryapp";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_INVENTORYS = "inventorys";

    public static abstract class InventoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORYS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORYS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORYS;

        /** Name of database table for Inventorys */
        public final static String TABLE_NAME = "Inventorys";

        /**
         * Unique ID number for the Inventory (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the Inventory.
         *
         * Type: TEXT
         */
        public final static String COLUMN_INVENTORY_NAME ="Name";

        /**
         * Price of the Inventory.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_INVENTORY_PRICE = "Price";

        /**
         * Quantity of the Inventory.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_INVENTORY_QUANTITY = "Quantity";

        /**
         * Supplier's Name and Number for the Inventory.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_INVENTORY_SUP_NAME = "Supplier_Name";
        public final static String COLUMN_INVENTORY_SUP_NUMBER = "Supplier_Number";

    }
}
