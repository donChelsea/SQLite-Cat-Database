package android.example.com.sqlitecatexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText catNameEt;
    private EditText catBreedEt;
    private EditText catAgeEt;
    private Button saveButton;
    private CatDatabaseHelper databaseHelper;
    private String TAG = "Cat database";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new CatDatabaseHelper(getApplicationContext());

        catNameEt = findViewById(R.id.name_edittext);
        catBreedEt = findViewById(R.id.breed_edittext);
        catAgeEt = findViewById(R.id.age_edittext);
        saveButton = findViewById(R.id.save_cat_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = catNameEt.getText().toString();
                String breed = catBreedEt.getText().toString();
                int age = Integer.parseInt(catAgeEt.getText().toString());
                databaseHelper.addCat(new Cat(name, breed, age));
                Toast.makeText(v.getContext(), "Cat saved!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Main Activity: " + databaseHelper.getCatList());
            }
        });
    }


}
