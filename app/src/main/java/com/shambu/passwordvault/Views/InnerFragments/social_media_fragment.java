package com.shambu.passwordvault.Views.InnerFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shambu.passwordvault.ClickAdapterListener;
import com.shambu.passwordvault.Model.Entities.GSMOWOM_data;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.ViewModels.GSMOWOMViewModel;
import com.shambu.passwordvault.ViewModels.RVadapters.GSMOWOM_adapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shambu.passwordvault.Constants.OTHER_TYPE;
import static com.shambu.passwordvault.Constants.SOCIAL_TYPE;

public class social_media_fragment extends Fragment implements ClickAdapterListener {

    private SharedPreferences pref;

    @BindView(R.id.social_rv)
    RecyclerView rv;

    @BindView(R.id.social_fab)
    FloatingActionButton fab;

    private GSMOWOMViewModel viewModel;
    private GSMOWOM_adapter adapter;

    private Dialog addNew, editDialog;
    private String msg = otherwebsites_fragment.class.getSimpleName();
    private ActionMode actionMode;
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.cab_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            MenuItem item = menu.getItem(1);
            if(adapter.getSelectedItemCount() > 1){
                item.setVisible(false);
            }
            else {
                item.setVisible(true);
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Log.d("API123", "here");
            switch (item.getItemId()) {


                case R.id.action_delete:
                    // delete all the selected rows
                    deleteRows();
                    mode.finish();
                    return true;

                case R.id.action_edit:
                    editSelected();
                    mode.finish();
                    return true;

                case R.id.action_share_all:
                    shareSelected();
                    mode.finish();
                    return true;

                case R.id.action_select_all:
                    selectAll();
                    return true;

                case R.id.action_fav_all:
                    favAll();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelections();
            actionMode = null;
        }
    };

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
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void selectAll() {
        adapter.selectAll();
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }

        //   actionMode = null;
    }

    private void deleteRows() {
        List<Integer> selectedItemPositions =
                adapter.getSelectedItems();
        if(selectedItemPositions.size()==adapter.getItemCount()){
            viewModel.deleteAll(SOCIAL_TYPE);
            actionMode = null;
        } else {
            for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                viewModel.delete(adapter.getDataFromList(selectedItemPositions.get(i)));
            }
            actionMode = null;
        }

    }

    private void shareSelected(){
        List<Integer> selectedItemPositions =
                adapter.getSelectedItems();
        StringBuilder builder = new StringBuilder();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            builder.append(adapter.getShareData(selectedItemPositions.get(i))+"\n");
        }
        adapter.notifyDataSetChanged();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Here are the passwords");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, builder.toString());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

        actionMode = null;
    }

    private void favAll() {
        List<Integer> selectedItemPositions =
                adapter.getSelectedItems();

        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            viewModel.addtoFav(adapter.getDataFromList(selectedItemPositions.get(i)));
        }
        actionMode = null;
        Toast.makeText(getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
    }

    private void editSelected(){
        final List<Integer> selectedItemPositions =
                adapter.getSelectedItems();

        if(pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")){
            editDialog = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
        }
        else if(pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")){
            editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        }
        else{
            editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        }
        editDialog.setContentView(R.layout.add_new_gsmowom_dialog);
        final TextView site, mail, phnum, userid, passwrd, notes;
        Button save_butt;
        site = editDialog.findViewById(R.id.gsmowom_edt_siteName);
        mail = editDialog.findViewById(R.id.gsmowom_edt_assoEmail);
        phnum = editDialog.findViewById(R.id.gsmowom_edt_assoPhno);
        userid = editDialog.findViewById(R.id.gsmowom_edt_username);
        passwrd = editDialog.findViewById(R.id.gsmowom_edt_pass);
        notes = editDialog.findViewById(R.id.gsmowom_edt_adiInfo);
        save_butt = editDialog.findViewById(R.id.save_gsmowom_button);
        site.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getD_provider());
        mail.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getD_assoEmail());
        phnum.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getD_assoPhno());
        userid.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getD_username());
        passwrd.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getD_pass());
        notes.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getD_adiInfo());
        editDialog.show();

        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GSMOWOM_data sample_data = new GSMOWOM_data(adapter.getDataFromList(selectedItemPositions.get(0)).getD_ID(),
                        SOCIAL_TYPE,
                        TextUtils.isEmpty(site.getText().toString()) ? "" : site.getText().toString(),
                        TextUtils.isEmpty(mail.getText().toString()) ? "" : mail.getText().toString(),
                        TextUtils.isEmpty(phnum.getText().toString()) ? "" : phnum.getText().toString(),
                        TextUtils.isEmpty(userid.getText().toString()) ? "" : userid.getText().toString(),
                        TextUtils.isEmpty(passwrd.getText().toString()) ? "" : passwrd.getText().toString(),
                        TextUtils.isEmpty(notes.getText().toString()) ? "" : notes.getText().toString());
                if(!sample_data.getD_provider().equals("") ||
                        !sample_data.getD_assoEmail().equals("") ||
                        !sample_data.getD_assoPhno().equals("") ||
                        !sample_data.getD_username().equals("") ||
                        !sample_data.getD_pass().equals("") ||
                        !sample_data.getD_adiInfo().equals("")) {
                    viewModel.update(sample_data);
                    editDialog.dismiss();
                }
                else{
                    editDialog.dismiss();
                }

            }
        });
    }

   /* private void sorter(){
        if(pref.getString("SORT", "Alphabetically / Ascending").equals("Alphabetically / Ascending")){
            Collections.sort(data_list, new Comparator<GSMOWOM_data>() {
                @Override
                public int compare(GSMOWOM_data o1, GSMOWOM_data o2) {
                    return o1.getD_provider().compareTo(o2.getD_provider());
                }
            });
        }
        else if(pref.getString("SORT", "Alphabetically / Ascending").equals("Zalphabetically / Descending")){
            Collections.sort(data_list, new Comparator<GSMOWOM_data>() {
                @Override
                public int compare(GSMOWOM_data o1, GSMOWOM_data o2) {
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
        View view = inflater.inflate(R.layout.social_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        adapter = new GSMOWOM_adapter(this);
        rv.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(GSMOWOMViewModel.class);
        viewModel.getAllGSMOWOMData(SOCIAL_TYPE).observe(getViewLifecycleOwner(), new Observer<List<GSMOWOM_data>>() {
            @Override
            public void onChanged(List<GSMOWOM_data> data) {
                adapter.refreshList(data);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")){
                    addNew = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
                }
                else if(pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")){
                    addNew = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                }
                else{
                    addNew = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                }
                addNew.setContentView(R.layout.add_new_gsmowom_dialog);
                final TextView site, mail, phnum, userid, passwrd, notes;
                Button save_butt;
                site = addNew.findViewById(R.id.gsmowom_edt_siteName);
                mail = addNew.findViewById(R.id.gsmowom_edt_assoEmail);
                phnum = addNew.findViewById(R.id.gsmowom_edt_assoPhno);
                userid = addNew.findViewById(R.id.gsmowom_edt_username);
                passwrd = addNew.findViewById(R.id.gsmowom_edt_pass);
                notes = addNew.findViewById(R.id.gsmowom_edt_adiInfo);
                save_butt = addNew.findViewById(R.id.save_gsmowom_button);
                addNew.show();
                save_butt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GSMOWOM_data sample_data = new GSMOWOM_data(SOCIAL_TYPE,
                                TextUtils.isEmpty(site.getText().toString()) ? "" : site.getText().toString(),
                                TextUtils.isEmpty(mail.getText().toString()) ? "" : mail.getText().toString(),
                                TextUtils.isEmpty(phnum.getText().toString()) ? "" : phnum.getText().toString(),
                                TextUtils.isEmpty(userid.getText().toString()) ? "" : userid.getText().toString(),
                                TextUtils.isEmpty(passwrd.getText().toString()) ? "" : passwrd.getText().toString(),
                                TextUtils.isEmpty(notes.getText().toString()) ? "" : notes.getText().toString());
                        if(!sample_data.getD_provider().equals("") ||
                                !sample_data.getD_assoEmail().equals("") ||
                                !sample_data.getD_assoPhno().equals("") ||
                                !sample_data.getD_username().equals("") ||
                                !sample_data.getD_pass().equals("") ||
                                !sample_data.getD_adiInfo().equals("")) {
                            viewModel.insert(sample_data);
                            addNew.dismiss();
                        }
                        else{
                            addNew.dismiss();
                        }

                    }
                });

            }
        });
        return view;
    }


}