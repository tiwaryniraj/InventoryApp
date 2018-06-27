package com.example.niraj.inventoryapp.data;

import android.provider.BaseColumns;

public final class InventoryContract {
    public static abstract class InventoryEntry implements BaseColumns {
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
