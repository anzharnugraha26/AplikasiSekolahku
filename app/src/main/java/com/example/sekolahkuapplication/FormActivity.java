package com.example.sekolahkuapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.sekolahkuapplication.datasource.DatabaseHelper;
import com.example.sekolahkuapplication.datasource.SiswaDataSource;
import com.example.sekolahkuapplication.model.Siswa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FormActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    private EditText namaDepan , noHp ,namaBelakang , email , tglLahir, alamat ;
    private RadioGroup genderR ;
    private Spinner education ;
    private CheckBox membacaCb , menulisCb , menggambarCb ;
    private Button btnUpdate ;

    private Siswa receivedSiswa;

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadDetailDataSiswa(Long idSiswa){
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDataSource dataSource = new SiswaDataSource(databaseHelper);
            receivedSiswa = dataSource.findById(idSiswa);

            namaDepan.setText(receivedSiswa.getNamaDepan());
            namaBelakang.setText(receivedSiswa.getNamaBelakang());
            email.setText(receivedSiswa.getEmail());
            tglLahir.setText(receivedSiswa.getTglLahir());
            alamat.setText(receivedSiswa.getAlamat());
            noHp.setText(receivedSiswa.getPhoneNumber());

            String gender = receivedSiswa.getGender();
            int selectedId = (gender.equals("Pria")) ? R.id.priaRb : R.id.wanitaRb;
            genderR.check(selectedId);

            String hobi = receivedSiswa.getHobi();
            membacaCb.setChecked(hobi.contains("Membaca"));
            menulisCb.setChecked(hobi.contains("Menulis"));
            menggambarCb.setChecked(hobi.contains("Menggambar"));

            SpinnerAdapter adapter = education.getAdapter();
            if (adapter instanceof ArrayAdapter){
                int position =((ArrayAdapter) adapter).getPosition(receivedSiswa.getEducation());
                education.setSelection(position);
            }

            btnUpdate.setText("Update");
            showToast("data Siswa Loaded Succesfully");
        } catch (Exception e){
            showToast(e.getMessage());
        }
    }

    private void initDataSiswa(Siswa siswa){
        String inputNamaDepan = namaDepan.getText().toString().trim();
        String inputNamablkng = namaBelakang.getText().toString().trim();
        String inputNoHp = noHp.getText().toString().trim();
        String inputEmail = email.getText().toString().trim();
        String inputAlamat = alamat.getText().toString().trim();
        String inputTglLAhir = tglLahir.getText().toString().trim();


        String selectedGender;
        if (genderR.getCheckedRadioButtonId()== R.id.priaRb){
            selectedGender = "Pria";
        } else {
            selectedGender = "Wanita";
        }

        List<String> selectedHobies = new ArrayList<>();
        if (membacaCb.isChecked()){
            selectedHobies.add("Membaca");
        }
        if (menulisCb.isChecked()){
            selectedHobies.add("Menulis");
        }
        if (menggambarCb.isChecked()){
            selectedHobies.add("Menggambar");
        }
        String joinHobies = TextUtils.join(",", selectedHobies);

        String selectEducation = education.getSelectedItem().toString();

        siswa.setNamaDepan(inputNamaDepan);
        siswa.setNamaBelakang(inputNamablkng);
        siswa.setPhoneNumber(inputNoHp);
        siswa.setEmail(inputEmail);
        siswa.setEducation(selectEducation);
        siswa.setGender(selectedGender);
        siswa.setTglLahir(inputTglLAhir);
        siswa.setHobi(joinHobies);
        siswa.setAlamat(inputAlamat);
    }

        private void addNewSiswa(){
            Siswa siswa = new Siswa();
            initDataSiswa(siswa);
            try {
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                SiswaDataSource dataSource = new SiswaDataSource(databaseHelper);
                dataSource.save(siswa);
                showToast("Add New Data Siswa Succes");
                finish();
            } catch (Exception e){
                showToast(e.getMessage());
            }
        }

        private void updateSiswa(){
            initDataSiswa(receivedSiswa);
            try {
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                SiswaDataSource dataSource = new SiswaDataSource(databaseHelper);
                dataSource.update(receivedSiswa);
                showToast("Data Siswa Updated succes");
                finish();
            } catch (Exception e){
                showToast(e.getMessage());
            }
        }

        private  boolean validateInputs(){
        boolean valid = true;
        String etnamaDepan = namaDepan.getText().toString().trim();
        String etnamaBelakang = namaBelakang.getText().toString().trim();
        String etNoHandphone = noHp.getText().toString().trim();
        String etEmail = email.getText().toString().trim();
        String etTglLahir = tglLahir.getText().toString().trim();
        String etAlamat = alamat.getText().toString().trim();

        if (etnamaDepan.isEmpty()){
            valid = false;
            namaDepan.setError("Nama Depan Required");
        }
        if (etnamaBelakang.isEmpty()){
            valid = false;
            namaBelakang.setError("nama belakang");
        }
        if (etNoHandphone.isEmpty()){
            valid = false;
            noHp.setError("empty");
        }
        if (etEmail.isEmpty()){
            valid = false ;
            email.setError("empty");
        }

        if (etTglLahir.isEmpty()){
            valid = false;
            tglLahir.setError("empty");
        }

        if (etAlamat.isEmpty()){
            valid = false;
            alamat.setError("empty");
        }


         return valid;

        }

        private void save(){
            boolean isInpuValid = validateInputs();

            if (isInpuValid){
            if (receivedSiswa != null){
                updateSiswa();
            } else{
                addNewSiswa();
            }
        } else {
            showToast("sory");
            }
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedMenuId = item.getItemId();
        if(selectedMenuId == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        namaDepan = findViewById(R.id.etNamaDepan);
        namaBelakang = findViewById(R.id.etNamaBelakang);
        noHp = findViewById(R.id.etNoHandphone);
        email = findViewById(R.id.etEmail);
        tglLahir = findViewById(R.id.tanggalLahirEt);
        alamat = findViewById(R.id.etAlamat);
        genderR = findViewById(R.id.genderRb);
        education = findViewById(R.id.educationSp);
        membacaCb = findViewById(R.id.cbMembaca);
        menulisCb = findViewById(R.id.cbMenulis);
        menggambarCb = findViewById(R.id.cbMenggambar);
        btnUpdate = findViewById(R.id.saveBtn);

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDatePickerDialog();
            }
        });

        long receivedSiswa = getIntent().getLongExtra("id_siswa", -1);

        if (receivedSiswa == -1){
            showToast("Tidak Menerima id Siswa");
        } else {
            loadDetailDataSiswa(receivedSiswa);
        }

        //long receivedIdSiswa = getIntent().getLongExtra("id_siswa", -1);


    }


    private void showDatePickerDialog(){
        Calendar today = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = String.format("%s-%s-%s", dayOfMonth, (month+1),year);
        tglLahir.setText(selectedDate);
    }

}
