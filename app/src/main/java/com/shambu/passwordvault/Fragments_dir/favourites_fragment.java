package com.shambu.passwordvault.Fragments_dir;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shambu.passwordvault.Adapters_dir.FAV_adapter;
import com.shambu.passwordvault.Data_classes.FAV_data;
import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.FAV_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.List;

public class favourites_fragment extends Fragment implements FAV_adapter.ClickAdapterListenerFav {

   // private SharedPreferences pref;
    RecyclerView recyclerView;
    FAV_adapter adapter;
    List<FAV_data> data_list;
    FAV_sqlHelper database;
    private ActionMode actionMode;
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.cab_favmenu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_favdelete:
                    deleteFav();
                    mode.finish();
                    return true;

                case R.id.action_favselect_all:
                    selectAllFav();
                    return true;

                case R.id.action_favshare_all:
                    shareFav();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.FAVclearSelections();
            actionMode = null;
        }
    };

    private void initDB(){
        database = new FAV_sqlHelper(getContext());
    }

    private void initList(){
        data_list = database.getALLfavData(database.getReadableDatabase(MainActivity.lepass));
      //  sorter();
        adapter = new FAV_adapter(data_list, getContext(), favourites_fragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

  /*  private void sorter(){
        if(pref.getString("SORT", "Alphabetically / Ascending").equals("Alphabetically / Ascending")){
            Collections.sort(data_list, new Comparator<FAV_data>() {
                @Override
                public int compare(FAV_data o1, FAV_data o2) {

                    return o1.getD_provider().compareTo(o2.getD_provider());
                }
            });
        }
        else if(pref.getString("SORT", "Alphabetically / Ascending").equals("Zalphabetically / Descending")){
            Collections.sort(data_list, new Comparator<FAV_data>() {
                @Override
                public int compare(FAV_data o1, FAV_data o2) {
                    return o2.getD_provider().compareTo(o1.getD_provider());
                }
            });
        }
        else if(pref.getString("SORT", "Alphabetically / Ascending").equals("Newest first")){
            Collections.reverse(data_list);
        }
        else {

        }
    } */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourites_fragment_layout, container, false);
    //    pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.favourites_rv);
        SQLiteDatabase.loadLibs(getContext());
        initDB();
        initList();

        return view;
    }

    @Override
    public void onRowClicked(int position) {
        enableActionMode(position);
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        adapter.FAVtoggleSelection(position);
        int count = adapter.FAVgetSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void selectAllFav(){
        adapter.FAVselectAll();
        int count = adapter.FAVgetSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
        //  actionMode = null;
    }

    private void shareFav(){
        List<Integer> selectedItemPositions =
                adapter.FAVgetSelectedItems();
        StringBuilder builder = new StringBuilder();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            builder.append(adapter.FAVgetShareData(selectedItemPositions.get(i))+"\n");
        }
        adapter.notifyDataSetChanged();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Here are the passwords");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, builder.toString());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

        actionMode = null;
    }

    private void deleteFav(){
        List<Integer> selectedItemPositions =
                adapter.FAVgetSelectedItems();
        if(selectedItemPositions.size() == data_list.size()){
            database.deleteTABLE(database.getWritableDatabase(MainActivity.lepass));
            initList();
            Toast.makeText(getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
        }
        else{
            for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                adapter.FAVremoveData(selectedItemPositions.get(i));
            }
            adapter.notifyDataSetChanged();

            actionMode = null;
            Toast.makeText(getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
        }

    }

}
