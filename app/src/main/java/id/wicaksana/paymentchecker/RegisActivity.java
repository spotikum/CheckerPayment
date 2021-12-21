package id.wicaksana.paymentchecker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisActivity extends AppCompatActivity {

    int textSize = 0;
    SeekBar seekBar;
    String pilih = "", s1 = "", s2 = "", s3 = "";
    TextView txtSeekBar;
    EditText nama, email, pass, comfrmpass;
    CheckBox kasur, lemari, kulkas;
    RadioGroup pilihan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        kasur = findViewById(R.id.kasur);
        lemari = findViewById(R.id.lemari);
        kulkas = findViewById(R.id.kulkas);
        nama = findViewById(R.id.inputName);
        email = findViewById(R.id.inputEmail);
        pass = findViewById(R.id.inputPass);
        comfrmpass = findViewById(R.id.inputConfirmPass);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtSeekBar = findViewById(R.id.ratingvalue);
        txtSeekBar.setText(Integer.toString(textSize));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                textSize = textSize + (progressValue - progress);
                progress = progressValue;
                txtSeekBar.setText(Integer.toString(textSize));
            }
        });

        pilihan = findViewById(R.id.radioGroup);
        pilihan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.rb1:
                        pilih = "Perempuan";
                        break;
                    case R.id.rb2:
                        pilih = "Laki-laki";
                }
            }
        });
    }
    public void submit(View view) {
        showDialog();
    }
    private void showDialog(){
        if(kasur.isChecked()){
            s1 = "\n Kasur";
        }
        if(lemari.isChecked()){
            s2 = "\n Lemari";
        }
        if(kulkas.isChecked()){
            s3 = "\n Kulkas";
        }

        if(nama.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                pilih == "" || pass.getText().toString().isEmpty() || comfrmpass.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Data belum lengkap",Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle("Data User");
            alertDialogBuilder
                    .setMessage("Nama: " +nama.getText().toString()+
                            "\nEmail: " + email.getText().toString()+
                            "\nPassword : " +pass.getText().toString()+
                            "\nJenis Kelamin: " +pilih+
                            "\nFasilitas: " +s1+s2+s3+
                            "\nUmur: " + textSize)
                    .setIcon(R.drawable.file)
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            Intent intent = new Intent(RegisActivity.this, DetailActivity.class);
            intent.putExtra("Nama", nama.getText().toString());
            intent.putExtra("Email", email.getText().toString());
            intent.putExtra("Password", pass.getText().toString());
            intent.putExtra("Jenis Kelamin", pilih);
            intent.putExtra("Fasilitas", s1+s2+s3);
            intent.putExtra("Umur", txtSeekBar.getText().toString());
            startActivity(intent);
        }
    }
}