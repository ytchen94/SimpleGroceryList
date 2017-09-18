package moonmarchnear.simplegrocerylist;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "ex.simplegrocerylisttemp.myfirstapp.MESSAGE";
    public static final String EXTRA_ARRAY_LIST = "ex.simplegrocerylisttemp.ARRAY_LIST";
    public static final int RESULT_OK = 1;
    public static final int REQUEST_CODE = 100;
    public ArrayList<String> test;
    private ArrayAdapter<String> adapter;
    private GroceryDBHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new GroceryDBHelper(this);
        test = new ArrayList<String>();

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();


        //dfvdvdfvfdv

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                SQLSchema.GroceryEntry._ID,
                SQLSchema.GroceryEntry.COLUMN_NAME_TITLE,
        };

        // Filter results WHERE "title" = 'My Title'
        //String selection = SQLSchema.GroceryEntry.COLUMN_NAME_TITLE + " = ?";
        //String[] selectionArgs = { "My Title" };

        String sortOrder =
                SQLSchema.GroceryEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor cursor = db.query(
                SQLSchema.GroceryEntry.TABLE_NAME,        // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        while(cursor.moveToNext()) {
            String itemID = cursor.getString(
                    cursor.getColumnIndexOrThrow(SQLSchema.GroceryEntry.COLUMN_NAME_TITLE));
            test.add(itemID);
        }
        cursor.close();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, test);
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(adapter);

        final Button addItemButton = (Button) findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText itemET = (EditText) findViewById(R.id.itemET);
                String item = itemET.getText().toString();
                if (!item.isEmpty()) {
                    test.add(item);
                }
                itemET.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        final Button clearAllButton = (Button) findViewById(R.id.clearAllButton);
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.delete(SQLSchema.GroceryEntry.TABLE_NAME, null, null);
                test.clear();
                adapter.notifyDataSetChanged();
            }
        });


        /*FloatingActionButton addGrocery = (FloatingActionButton) findViewById(R.id.fab);
        addGrocery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                test.add("FUCK");
                adapter.notifyDataSetChanged();
            }
        });*/

    }



    public void newItem(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddNewActivity.class);
        //EditText editText = (EditText) findViewById(R.id.itemET);
        //String message = editText.getText().toString();
        //String message = "fuck";
        //intent.putExtra(EXTRA_MESSAGE, message);
        //Bundle b = new Bundle();
        //b.putStringArrayList(EXTRA_ARRAY_LIST, test);
        //intent.putExtras(b);
        //startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE);
        adapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String item = data.getStringExtra(AddNewActivity.ADD_ITEM);
            test.add(item);
            adapter.notifyDataSetChanged();


            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(SQLSchema.GroceryEntry.COLUMN_NAME_TITLE, item);
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(SQLSchema.GroceryEntry.TABLE_NAME, null, values);


        }
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
