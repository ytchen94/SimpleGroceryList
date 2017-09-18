package moonmarchnear.simplegrocerylist;

/**
 * Created by violetti on 8/24/17.
 */

import android.provider.BaseColumns;


public final class SQLSchema {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SQLSchema() {}
    /* Inner class that defines the table contents */
    public static class GroceryEntry implements BaseColumns {
        public static final String TABLE_NAME = "GroceryList";
        public static final String COLUMN_NAME_TITLE = "Item";
    }
}
