package id.wicaksana.paymentchecker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import id.wicaksana.paymentchecker.Adapter.AdapterUser;
import id.wicaksana.paymentchecker.GetSet.GetSetUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Database DB;
    List<GetSetUser> listDataUser;
    AdapterUser adapterUser;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button_add_data);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisActivity.class));
            }
        });

        DB = new Database(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onLongItemClick(View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        final GetSetUser item = listDataUser.get(itemPosition);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                MainActivity.this);
                        alertDialogBuilder.setTitle("Warning");

                        alertDialogBuilder
                                .setMessage("Apakah Anda ingin menghapus data dengan nama " + item.getNama() + " ?")
                                .setIcon(R.mipmap.ic_launcher)
                                .setCancelable(false)
                                .setPositiveButton("Ya", (dialog, id) -> {
                                    DB.deleteUser(item.getId());
                                    Toast.makeText(MainActivity.this, "Berhasil menghapus data.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    finish();
                                })
                                .setNegativeButton("Tidak", (dialogInterface, i) -> {

                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();

                        alertDialog.show();

                    }

                    @Override public void onItemClick(View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        final GetSetUser item = listDataUser.get(itemPosition);
                        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                        intent.putExtra("id", item.getId());
                        intent.putExtra("nama", item.getNama());
                        intent.putExtra("telepon", item.getTelepon());
                        intent.putExtra("alamat", item.getAlamat());
                        intent.putExtra("jenis_kelamin", item.getJk());
                        intent.putExtra("umur", item.getUmur());
                        startActivity(intent);
                        finish();
                    }
                })
        );

        Cursor res = DB.getDataUser();
        if(res.getCount()==0){
            Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        listDataUser = new ArrayList<>();
        while(res.moveToNext()){
            listDataUser.add(new GetSetUser(res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5)));
        }

        adapterUser = new AdapterUser(listDataUser, MainActivity.this);
        recyclerView.setAdapter(adapterUser);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button button = (Button) findViewById(R.id.button_add_data);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, RegisActivity.class));
//            }
//        });
//    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return false;
    }
}
