package br.com.infracea.fiscalapp.screens.login;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.basic.BasicActivity;
import br.com.infracea.fiscalapp.util.Mask;

public class RegisterActivity extends BasicActivity {

    private EditText userEmail;
    private EditText userName;
    private EditText userCpf;
    private EditText userPhone;
    private TextView datePicker;
    private TextView toolbarTitle;
    private DatePickerDialog datePickerDialog;
    private Button registerButton;

    private Calendar myCalendar = Calendar.getInstance();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        datePickerDialog = new DatePickerDialog(RegisterActivity.this, dateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        findViewItems();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //if (firebaseAuth.getC)
    }

    private void findViewItems() {

        userEmail = findViewById(R.id.register_email);
        userName = findViewById(R.id.register_username);
        userCpf = findViewById(R.id.register_cpf);
        datePicker = findViewById(R.id.register_data_picker);
        registerButton = findViewById(R.id.register_send);
        toolbarTitle = findViewById(R.id.toolbar_container_title);
        userPhone = findViewById(R.id.register_phone);

        registerButton.setOnClickListener(registerClickListener);
        datePicker.setOnClickListener(dataClickListener);
        toolbarTitle.setText("CADASTRAR");
        userCpf.addTextChangedListener(Mask.insert(Mask.MaskType.CPF, userCpf));
        userPhone.addTextChangedListener(Mask.insert(Mask.MaskType.PHONE, userPhone));
    }

    public boolean validates() {

        boolean isValidated = true;

        if (userEmail.getText().toString().contains("@")) {
            userEmail.setError("Email incorreto");
            userEmail.requestFocus();
            userEmail.setText("");
            isValidated = false;
        }

        if (userName.getText().toString().length() < 4) {
            userName.setError("Preencha o nome");
            userName.requestFocus();
            userName.setText("");
            isValidated = false;
        }

        if (userCpf.getText().toString().length() <= 13) {
            userCpf.setError(getResources().getString(R.string.register_cpf_error));
            userCpf.requestFocus();
            userCpf.setText("");
            isValidated = false;
        }

        if (userPhone.getText().toString().length() <= 12) {
            userPhone.setError("Telefone inválido");
            userPhone.requestFocus();
            userPhone.setText("");
            isValidated = false;
        }

        if (!datePicker.getText().toString().contains("/")) {
            datePicker.setError(getResources().getString(R.string.register_date_error));
            datePicker.requestFocus();
            datePicker.setText("Insira uma data");
            isValidated = false;
        }

        return isValidated;
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        datePicker.setText(sdf.format(myCalendar.getTime()));
        datePicker.setError(null);
    }

    View.OnClickListener dataClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
            datePickerDialog.show();
        }
    };

    View.OnClickListener registerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (validates()) {

            }
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

}
