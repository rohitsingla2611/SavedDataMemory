package rohitsingla.rdrock.saveddatamemory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String DATA = "namePhone";
    public static final String SWITCH_SAVED_DATA = "switchSavedData";
    TextView textViewSavedData;
    EditText editTextEnterName, editTextEnterPhone;
    Button buttonApplyText, buttonSaveData;
    Switch switchSavedData;
    String text, concatData, strName, strPhone;
    boolean switchOnOff;
    long backKeyPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    void initViews() {

        textViewSavedData = findViewById(R.id.textViewSavedData);
        editTextEnterName = findViewById(R.id.editTextEnterName);
        editTextEnterPhone = findViewById(R.id.editTextEnterPhone);
        buttonApplyText = findViewById(R.id.buttonApplyText);
        buttonSaveData = findViewById(R.id.buttonSaveData);
        switchSavedData = findViewById(R.id.switchSavedData);

        buttonApplyText.setOnClickListener(this);
        buttonSaveData.setOnClickListener(this);
        loadData();
        updateViews();


    }

    @Override
    public void onClick(View view) {
        strName = editTextEnterName.getText().toString();
        strPhone = editTextEnterPhone.getText().toString();
        if ((TextUtils.isEmpty(strName)) || (TextUtils.isEmpty(strPhone))) {


            if ((TextUtils.isEmpty(strName)) && (TextUtils.isEmpty(strPhone))) {
                Toast.makeText(this, "Please Enter Your Details", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(strName)) {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please Enter Your Phone", Toast.LENGTH_SHORT).show();
            }
        } else {
            concatData = strName + "\n" + " Phone :- " + strPhone;
            if (view.getId() == R.id.buttonApplyText) {
                textViewSavedData.setText(concatData);
            } else if (view.getId() == R.id.buttonSaveData) {
                saveData();
            }

        }


    }

    void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        if (switchSavedData.isChecked()) {
            editor.putString(DATA, concatData);
            editor.putBoolean(SWITCH_SAVED_DATA, switchSavedData.isChecked());

            editor.apply();

            Toast.makeText(this, "DATA SAVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "PLEASE TURN ON SWITCH", Toast.LENGTH_SHORT).show();


    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(DATA, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH_SAVED_DATA, false);


    }

    public void updateViews() {
        textViewSavedData.setText(text);
        switchSavedData.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        if (backKeyPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();
        }
        backKeyPressedTime = System.currentTimeMillis();
    }
}

