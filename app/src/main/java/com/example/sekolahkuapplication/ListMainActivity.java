package com.example.sekolahkuapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;

import com.example.sekolahkuapplication.adapter.SiswaItemAdapter;
import com.example.sekolahkuapplication.datasource.DatabaseHelper;
import com.example.sekolahkuapplication.datasource.SiswaDataSource;
import com.example.sekolahkuapplication.model.Siswa;

import java.util.List;

public class ListMainActivity extends AppCompatActivity {

    private ListView siswaLv;
    private SiswaItemAdapter adapter;
    private SearchView searchSiswa;

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void startFormEdit(int position) {
        Intent intent = new Intent(this, FormActivity.class);
        Siswa selectedSiswa = adapter.getItem(position);
        intent.putExtra("id_siswa", selectedSiswa.getId());
        startActivity(intent);
    }

    private void startDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        Siswa selectedSiswa = adapter.getItem(position);
        intent.putExtra("id_siswa", selectedSiswa.getId());
        startActivity(intent);
    }


    private void loadDataSiswa() {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDataSource siswaDataSource = new SiswaDataSource(databaseHelper);
            List<Siswa> foundSiswaList = siswaDataSource.getAll();
            adapter = new SiswaItemAdapter(this, foundSiswaList);
            siswaLv.setAdapter(adapter);
            siswaLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startDetailActivity(position);
                }
            });
            showToast("Data Load Succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            showToast("failed" + e.getMessage());
        }
    }

    private void searchSiswa(String keyword) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDataSource dataSource = new SiswaDataSource(databaseHelper);
            List<Siswa> foundSiswaList = dataSource.findByNameLike(keyword);

            if (!adapter.isEmpty()) {
                adapter.clear();
            }
            adapter.addAll(foundSiswaList);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            showToast("unable to search siswa caused by :" + e.getMessage());
        }
    }


    private void delete(Siswa siswa) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SiswaDataSource dataSource = new SiswaDataSource(databaseHelper);
        dataSource.remove(siswa);
        adapter.notifyDataSetChanged();
    }


    private void startFormActivity() {
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedMenuId = item.getItemId();
        if (selectedMenuId == R.id.AddSiswaMenu) {
            startFormActivity();
        }
        if (selectedMenuId == R.id.searchView) {
            showToast("hai");
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    showToast(newText);
                    return true;
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);

    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedPosition = info.position;

        switch (id) {
            case R.id.action_edit:
                startFormEdit(selectedPosition);
                break;
            case R.id.action_delete:
                delete(adapter.getItem(selectedPosition));
                Intent intent = new Intent(ListMainActivity.this, ListMainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Delete Sukses", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);
        siswaLv = findViewById(R.id.siswaLv);
        // SearchView searchView = findViewById(R.id.search_view);

        SearchView searchView = findViewById(R.id.siswa_sv);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchSiswa(newText);
                return true;
            }
        });

        registerForContextMenu(siswaLv);


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataSiswa();
    }
}
