package br.com.infracea.fiscalapp.screens.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.basic.BasicActivity;
import br.com.infracea.fiscalapp.models.User;
import br.com.infracea.fiscalapp.screens.container.ContainerActivity;
import br.com.infracea.fiscalapp.util.FormattterString;
import br.com.infracea.fiscalapp.util.Mask;

public class RegisterActivity extends BasicActivity {

    private static final int RC_SIGN_IN = 123;

    User user = User.getInstance();

    private EditText userEmail;
    private EditText userName;
    private EditText userCpf;
    private EditText userPhone;
    private EditText userPassword;
    private TextView datePicker;
    private TextView toolbarTitle;
    private DatePickerDialog datePickerDialog;
    private Button registerButton;
    private ProgressBar progressBar;

    private Calendar myCalendar = Calendar.getInstance();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference usersReference = firebaseDatabase.getReference().child("users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        datePickerDialog = new DatePickerDialog(RegisterActivity.this, dateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        findViewItems();

    }


    private void findViewItems() {

        userEmail = findViewById(R.id.register_email);
        userName = findViewById(R.id.register_username);
        userCpf = findViewById(R.id.register_cpf);
        datePicker = findViewById(R.id.register_data_picker);
        registerButton = findViewById(R.id.register_send);
        toolbarTitle = findViewById(R.id.toolbar_container_title);
        userPhone = findViewById(R.id.register_phone);
        userPassword = findViewById(R.id.register_password);
        progressBar = findViewById(R.id.register_progress_bar);

        registerButton.setOnClickListener(registerClickListener);
        datePicker.setOnClickListener(dataClickListener);
        toolbarTitle.setText("CADASTRAR");
        userCpf.addTextChangedListener(Mask.insert(Mask.MaskType.CPF, userCpf));
        userPhone.addTextChangedListener(Mask.insert(Mask.MaskType.PHONE, userPhone));
    }

    public boolean validates() {

        boolean isValidated = true;

        if (!userEmail.getText().toString().contains("@")) {
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

        if (userCpf.getText().toString().length() <= 12) {
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

//        if (!datePicker.getText().toString().contains("/")) {
//            datePicker.setError(getResources().getString(R.string.register_date_error));
//            datePicker.requestFocus();
//            datePicker.setText("Insira uma data");
//            isValidated = false;
//        } descoment if has date picker

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

    public Map<String, Object> newUserMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("email", User.getInstance().getEmail());
        result.put("name", User.getInstance().getName());
        result.put("phone", User.getInstance().getPhone());
        result.put("cpf", User.getInstance().getCpf());
        result.put("id", User.getInstance().getId());
        //result.put("device_language", User.getInstance().getDevice_language());

        return result;
    }

    private void createUser() {
        user.setCpf(FormattterString.formatCpf(userCpf.getText().toString()));
        user.setEmail(userEmail.getText().toString());
        user.setName(userName.getText().toString());
        user.setPhone(userPhone.getText().toString());
        user.setId(user.getId());
    }

    private void checkIfUserAlreadyExists () {
        final String userId = firebaseAuth.getCurrentUser().getUid();
        usersReference.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        if(!dataSnapshot.exists()) {
                            //user doesnt exists, create user
                            usersReference.child(userId).push().setValue(newUserMap());
                        } else {
                            Log.w("REGISTER", "USER EXISTS");
                            //user already exists, update User
                            //User.getInstance().setNewCurrentUser(dataSnapshot);
                            //usersReference.child(userId).updateChildren(updateUserMap(User.getInstance()));
                        }



//                        if (ActivityCompat.checkSelfPermission(RegisterActivity.this,
//                                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                            Intent intentToContainer = new Intent(LoginActivity.this, ContainerActivity.class);
//                            startActivity(intentToContainer);
//                        } else {
//                            startActivity(new Intent(RegisterActivity.this, RequestPermissionActivity.class));
//                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("REGISTER", "getUser:onCancelled", databaseError.toException());
                        // ...
                    }
                });
    }


    View.OnClickListener registerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (validates()) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //store user in firebase database
                            createUser();
                            //checkIfUserAlreadyExists();
                            final String userId = firebaseAuth.getCurrentUser().getUid();
//                            usersReference.push().child(userId).setValue(newUserMap());
                            usersReference.push().setValue(user); //TEM QUE DAR UM PUSH porra
                            Toast.makeText(RegisterActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, ContainerActivity.class));
                            finish();
                        } else {
                            try
                            {
                                throw task.getException();
                            }
                            // if user enters wrong password.
                            catch (FirebaseAuthWeakPasswordException weakPassword)
                            {
                                Toast.makeText(RegisterActivity.this, "Senha fraca. Tente outra senha", Toast.LENGTH_SHORT).show();
                            }
                            // if user enters wrong email.
                            catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                            {
                                Toast.makeText(RegisterActivity.this, "Email inválido", Toast.LENGTH_SHORT).show();
                            }
                            catch (FirebaseAuthUserCollisionException existEmail)
                            {
                                Toast.makeText(RegisterActivity.this, "Email já cadastrado", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e)
                            {
                                Log.d("Register", "onComplete: " + e.getMessage());
                            }
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
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
