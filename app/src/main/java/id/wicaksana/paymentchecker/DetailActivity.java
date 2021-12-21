package id.wicaksana.paymentchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    TextView nama,email,password,jeniskelamin,fasilitas,umur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        jeniskelamin = findViewById(R.id.jk);
        fasilitas = findViewById(R.id.fas);
        umur = findViewById(R.id.umur);

        nama.setText(getIntent().getExtras().getString("Nama"));
        email.setText(getIntent().getExtras().getString("Email"));
        password.setText(getIntent().getExtras().getString("Password"));
        jeniskelamin.setText(getIntent().getExtras().getString("Jenis Kelamin"));
        fasilitas.setText(getIntent().getExtras().getString("Fasilitas"));
        umur.setText(getIntent().getExtras().getString("Umur"));
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Application On Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Application On Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Application On Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Selamat Tinggal", Toast.LENGTH_SHORT).show();
    }
}