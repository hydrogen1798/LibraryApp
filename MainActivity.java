
package com.example.librarymemberapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText nameInput, phoneInput, addressInput;
    Button dateButton, saveButton;
    Switch feeSwitch;
    String joinDate = "";

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        addressInput = findViewById(R.id.addressInput);
        dateButton = findViewById(R.id.dateButton);
        saveButton = findViewById(R.id.saveButton);
        feeSwitch = findViewById(R.id.feeSwitch);

        db = new DatabaseHelper(this);

        dateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    joinDate = dayOfMonth + "/" + (month+1) + "/" + year;
                    dateButton.setText(joinDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String address = addressInput.getText().toString();
            boolean feePaid = feeSwitch.isChecked();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || joinDate.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                db.insertMember(name, phone, address, joinDate, feePaid ? "Paid" : "Unpaid");
                Toast.makeText(this, "Member Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
