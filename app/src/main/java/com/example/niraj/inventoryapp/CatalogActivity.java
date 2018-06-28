package com.example.niraj.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.niraj.inventoryapp.data.InventoryDbHelper;
import com.example.niraj.inventoryapp.data.InventoryContract.InventoryEntry;

public class CatalogActivity extends AppCompatActivity {

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new InventoryDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the Inventorys database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] project = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_INVENTORY_NAME,
                InventoryEntry.COLUMN_INVENTORY_PRICE,
                InventoryEntry.COLUMN_INVENTORY_QUANTITY,
                InventoryEntry.COLUMN_INVENTORY_SUP_NAME,
                InventoryEntry.COLUMN_INVENTORY_SUP_NUMBER,
        };
        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null
        );
        TextView displayView = (TextView) findViewById(R.id.text_view_inventory);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The Inventorys table contains <number of rows in Cursor> Inventorys.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The Inventorys table contains " + cursor.getCount() + " Inventorys.\n\n");
            displayView.append(InventoryEntry._ID + " - " +
                    InventoryEntry.COLUMN_INVENTORY_NAME + " - " +
                    InventoryEntry.COLUMN_INVENTORY_PRICE + " - " +
                    InventoryEntry.COLUMN_INVENTORY_QUANTITY + " - " +
                    InventoryEntry.COLUMN_INVENTORY_SUP_NAME +
                    InventoryEntry.COLUMN_INVENTORY_SUP_NUMBER + " - " +"\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_NAME);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_QUANTITY);
            int supNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_SUP_NAME);
            int supNumberColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_SUP_NUMBER);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupName = cursor.getString(supNameColumnIndex);
                String currentSupNumber = cursor.getString(supNumberColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupName + " - " +
                        currentSupNumber));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertInventory(){
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's Inventory attributes are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_INVENTORY_NAME, "Pen");
        values.put(InventoryEntry.COLUMN_INVENTORY_PRICE, 245);
        values.put(InventoryEntry.COLUMN_INVENTORY_QUANTITY, 2500);
        values.put(InventoryEntry.COLUMN_INVENTORY_SUP_NAME, "R.K.");
        values.put(InventoryEntry.COLUMN_INVENTORY_SUP_NUMBER, "8460226598");

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the Inventorys table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertInventory();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option will implement in future
            /*case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
}
