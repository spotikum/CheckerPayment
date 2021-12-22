package id.wicaksana.paymentchecker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisActivity extends AppCompatActivity {

    private EditText textNama, textTelepon, textAlamat;
    private TextView textUmur;
    private RadioGroup radioGroup;
    private String jenisKelamin = "";
    private SeekBar seekBarUmur;
    private CheckBox jenisPelanggan;
    private Button buttonDaftar;

    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        DB = new Database(this);
        textNama = findViewById(R.id.textNama);
        textTelepon = findViewById(R.id.textTelepon);
        textAlamat = findViewById(R.id.textAlamat);
        textUmur = findViewById(R.id.textUmur);
        jenisPelanggan = findViewById(R.id.jenisPelannggan);
        seekBarUmur = findViewById(R.id.seekBarUmur);
        seekBarUmur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textUmur.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((radioGroup, id) -> {
            switch (id){
                case R.id.radioButtonLakiLaki:
                    jenisKelamin = "Laki - laki";
                    break;
                case R.id.radioButtonPerempuan:
                    jenisKelamin = "Perempuan";
                    break;
            }
        });

        buttonDaftar = findViewById(R.id.buttonSimpan);
        buttonDaftar.setOnClickListener(view -> {
                if(!jenisKelamin.equals("")){
                    boolean check = DB.insertUser(
                            textNama.getText().toString(),
                            textTelepon.getText().toString(),
                            textAlamat.getText().toString(),
                            jenisKelamin,
                            Integer.parseInt(textUmur.getText().toString())
                    );
                    if (check){
                        Toast.makeText(RegisActivity.this, "Berhasil menambahkan data User ke SQLite", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(RegisActivity.this, "Gagal menambahkan data User ke SQLite", Toast.LENGTH_LONG).show();
                    }
                    showData();
                }else{
                    Toast.makeText(RegisActivity.this, "Pilih jenis kelamin Anda terlebih dahulu.", Toast.LENGTH_SHORT).show();
                }
        });
    }

    private void showData(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("Informasi");

        StringBuilder sb = new StringBuilder();
        sb.append("Pendaftaran Anda Berhasil!!\n");
        sb.append("Nama : " + textNama.getText().toString() + "\n");
        sb.append("Telepon : " + textTelepon.getText().toString() + "\n");
        sb.append("Alamat : " + textAlamat.getText().toString() + "\n");
        sb.append("Umur : " + textUmur.getText().toString() + "\n");
        sb.append("Jenis Kelamin : " + jenisKelamin + "\n");

        alertDialogBuilder
                .setMessage(sb.toString())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, id) -> {
                    Intent intent = new Intent(RegisActivity.this, DetailActivity.class);
                    intent.putExtra("nama", textNama.getText().toString());
                    intent.putExtra("telepon", textTelepon.getText().toString());
                    intent.putExtra("alamat", textAlamat.getText().toString());
                    intent.putExtra("umur", textUmur.getText().toString());
                    intent.putExtra("jenis_kelamin", jenisKelamin);
                    startActivity(intent);
                    finish();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}