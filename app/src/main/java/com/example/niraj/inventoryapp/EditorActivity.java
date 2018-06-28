package com.example.niraj.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.example.niraj.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.niraj.inventoryapp.data.InventoryDbHelper;

public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the Inventory's name */
    private EditText mNameEditText;

    /** EditText field to enter the Inventory's breed */
    private EditText mPriceEditText;

    /** EditText field to enter the Inventory's weight */
    private EditText mQuantityEditText;

    /** EditText field to enter the Inventory's weight */
    private EditText mSupNameEditText;

    /** EditText field to enter the Inventory's weight */
    private EditText mSupNumEditText;

    /**
     * Gender of the Inventory. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = findViewById(R.id.edit_inventory_name);
        mPriceEditText = findViewById(R.id.edit_inventory_price);
        mQuantityEditText = findViewById(R.id.edit_inventory_quantity);
        mSupNameEditText = findViewById(R.id.edit_inventory_suplierName);
        mSupNumEditText = findViewById(R.id.edit_inventory_suplierNum);
    }

    private void insertInventory() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supNameString = mSupNameEditText.getText().toString().trim();
        String supNumberString = mSupNumEditText.getText().toString().trim();
        int price = Integer.parseInt(priceString);
        int quantity = Integer.parseInt(quantityString);

        // Create database helper
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Inventory attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_INVENTORY_NAME, nameString);
        values.put(InventoryEntry.COLUMN_INVENTORY_PRICE, price);
        values.put(InventoryEntry.COLUMN_INVENTORY_QUANTITY, quantity);
        values.put(InventoryEntry.COLUMN_INVENTORY_SUP_NAME, supNameString);
        values.put(InventoryEntry.COLUMN_INVENTORY_SUP_NUMBER, supNumberString);

        // Insert a new row for Inventory in the database, returning the ID of that new row.
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving Inventory", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Inventory saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save Inventory to database
                insertInventory();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option Will implement in future
            /*case R.id.action_delete:
                // Do nothing for now
                return true;*/
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
