package com.example.niraj.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niraj.inventoryapp.data.InventoryContract.InventoryEntry;

public class InventoryCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link InventoryCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the inventory data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current inventory can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final long id;
        final int mQuantity;

        id = cursor.getLong(cursor.getColumnIndex(InventoryEntry._ID));
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView summarysecTextView = (TextView) view.findViewById(R.id.summarysec);
        TextView buyTextView = (TextView) view.findViewById(R.id.buy);

        // Find the columns of inventory attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_QUANTITY);

        // Read the inventory attributes from the Cursor for the current inventory
        final String inventoryName = cursor.getString(nameColumnIndex);
        final String inventoryPrice = cursor.getString(priceColumnIndex);
        String inventoryQuantity = cursor.getString(quantityColumnIndex);
        mQuantity = Integer.parseInt(inventoryQuantity);

        // Update the TextViews with the attributes for the current inventory
        nameTextView.setText(inventoryName);
        summaryTextView.setText(inventoryPrice);
        summarysecTextView.setText(inventoryQuantity);

        buyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.COLUMN_INVENTORY_NAME, inventoryName);
                    values.put(InventoryEntry.COLUMN_INVENTORY_PRICE, inventoryPrice);
                    values.put(InventoryEntry.COLUMN_INVENTORY_QUANTITY, mQuantity >= 1? mQuantity-1: 0);

                    Uri currentInventoryUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

                    int rowsAffected = context.getContentResolver().update(currentInventoryUri, values, null, null);
                    if (rowsAffected == 0 || mQuantity == 0) {
                        Toast.makeText(context, context.getString(R.string.sell_product_failed), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
