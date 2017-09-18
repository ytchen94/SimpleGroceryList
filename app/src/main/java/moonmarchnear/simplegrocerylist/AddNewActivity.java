package moonmarchnear.simplegrocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


public class AddNewActivity extends AppCompatActivity {

    public static final String ADD_ITEM = "addNewItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        final Button addItemButton = (Button) findViewById(R.id.button2);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent additionalItem = new Intent();

                EditText itemET = (EditText) findViewById(R.id.editText2);
                String item = itemET.getText().toString();
                additionalItem.putExtra(ADD_ITEM, item);

                setResult(MainActivity.RESULT_OK, additionalItem);
                finish();

            }
        });

    }
}
