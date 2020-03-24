package com.shambu.passwordvault.Views.InnerFragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shambu.passwordvault.ClickAdapterListener;
import com.shambu.passwordvault.ViewModels.FavViewModel;
import com.shambu.passwordvault.ViewModels.RVadapters.FAV_adapter;
import com.shambu.passwordvault.Model.Entities.FAV_data;
import com.shambu.passwordvault.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class favourites_fragment extends Fragment implements ClickAdapterListener {

   // private SharedPreferences pref;
    @BindView(R.id.favourites_rv)
   RecyclerView rv;

    private FAV_adapter adapter;
    private FavViewModel viewModel;

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
        ButterKnife.bind(this, view);
    //    pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        adapter = new FAV_adapter(this);
        rv.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(FavViewModel.class);
        viewModel.getAllFavData().observe(getViewLifecycleOwner(), new Observer<List<FAV_data>>() {
            @Override
            public void onChanged(List<FAV_data> data) {
                adapter.refreshList(data);
            }
        });

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
        if(selectedItemPositions.size() == viewModel.getAllFavData().getValue().size()){
            viewModel.deleteAll();
            Toast.makeText(getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
        }
        else{
            for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                viewModel.delete(adapter.getDatafromList(selectedItemPositions.get(i)));
            }
            adapter.notifyDataSetChanged();

            actionMode = null;
            Toast.makeText(getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
        }

    }

}
