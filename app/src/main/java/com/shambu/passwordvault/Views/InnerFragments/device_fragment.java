package com.shambu.passwordvault.Views.InnerFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.shambu.passwordvault.ClickAdapterListener;
import com.shambu.passwordvault.ViewModels.DevicesViewModel;
import com.shambu.passwordvault.ViewModels.RVadapters.DEVICE_adapter;
import com.shambu.passwordvault.Model.Entities.DEVICE_data;
import com.shambu.passwordvault.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class device_fragment extends Fragment implements ClickAdapterListener {

    private SharedPreferences pref;
    @BindView(R.id.devices_rv)
    RecyclerView rv;

    @BindView(R.id.devices_fab)
    FloatingActionButton fab;

    private DevicesViewModel viewModel;
    private DEVICE_adapter adapter;

    private DEVICE_data singleData, singleData_editDialog;
    private Dialog addNew, editDialog;
    private Spinner dtype_spin, dsecutype_spin;
    private TextView pin, pass, devicename;
    private PatternLockView mPatternLockView, mPatternLockView_editDialog;
    private LinearLayout hidePIN, hidePASS, hidePAT;
    private Button dSave;
    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(msg, "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(msg, "Pattern progress: " + PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(msg, "Pattern complete: " + PatternLockUtils.patternToString(mPatternLockView, pattern));
            singleData.setPINorPassorPattern(PatternLockUtils.patternToString(mPatternLockView, pattern));
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    }, mPatternLockViewListener_editDialog = new PatternLockViewListener() {
        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            singleData_editDialog.setPINorPassorPattern(PatternLockUtils.patternToString(mPatternLockView, pattern));
        }

        @Override
        public void onCleared() {

        }
    };
    private String msg = device_fragment.class.getSimpleName();
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
            if (adapter.DEVICEgetSelectedItemCount() > 1) {
                item.setVisible(false);
            } else {
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

                case R.id.action_select_all:
                    selectAll();
                    return true;

                case R.id.action_share_all:
                    shareSelected();
                    mode.finish();
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
            adapter.DEVICEclearSelections();
            actionMode = null;
        }
    };

   /* private void sorter(){
        if(pref.getString("SORT", "Alphabetically / Ascending").equals("Alphabetically / Ascending")){
            Collections.sort(data_list, new Comparator<DEVICE_data>() {
                @Override
                public int compare(DEVICE_data o1, DEVICE_data o2) {
                    return o1.getDevice_name().compareTo(o2.getDevice_name());
                }
            });
        }
        else if(pref.getString("SORT", "Alphabetically / Ascending").equals("Zalphabetically / Descending")){
            Collections.sort(data_list, new Comparator<DEVICE_data>() {
                @Override
                public int compare(DEVICE_data o1, DEVICE_data o2) {
                    return o2.getDevice_name().compareTo(o1.getDevice_name());
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
        View view = inflater.inflate(R.layout.device_frag_layout, container, false);
        ButterKnife.bind(this, view);
        pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        adapter = new DEVICE_adapter(this);
        rv.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(DevicesViewModel.class);
        viewModel.getAllDevices().observe(getViewLifecycleOwner(), new Observer<List<DEVICE_data>>() {
            @Override
            public void onChanged(List<DEVICE_data> device_data) {
                adapter.refreshList(device_data);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleData = new DEVICE_data();
                singleData.setData_type("DEVICE");

                if (pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")) {
                    addNew = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
                } else if (pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")) {
                    addNew = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                } else {
                    addNew = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                }
                addNew.setContentView(R.layout.add_new_device_dialog);
                dtype_spin = addNew.findViewById(R.id.dtype_spinner);
                dsecutype_spin = addNew.findViewById(R.id.dsecutype_spinner);
                devicename = addNew.findViewById(R.id.device_edt_dname);
                pin = addNew.findViewById(R.id.device_edt_PIN);
                pass = addNew.findViewById(R.id.device_edt_pass);
                mPatternLockView = addNew.findViewById(R.id.pattern_lock_view);
                mPatternLockView.addPatternLockListener(mPatternLockViewListener);
                hidePASS = addNew.findViewById(R.id.tohide_passtv_ll);
                hidePIN = addNew.findViewById(R.id.tohide_pintv_ll);
                hidePAT = addNew.findViewById(R.id.tohide_pattern_layout_dialog);
                dSave = addNew.findViewById(R.id.save_device_button);
                dtype_spin.setOnItemSelectedListener(new dtype_spinner_class());
                dsecutype_spin.setOnItemSelectedListener(new dsecutype_spinner_class());
                ArrayAdapter<CharSequence> dtype_spin_adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.DEVICE_TYPE, android.R.layout.simple_spinner_item);
                ArrayAdapter<CharSequence> dsecutype_spin_adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.DEVICE_SECURITY_TYPE, android.R.layout.simple_spinner_item);
                dtype_spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dtype_spin.setAdapter(dtype_spin_adapter);
                dsecutype_spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dsecutype_spin.setAdapter(dsecutype_spin_adapter);
                addNew.show();

                dSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        singleData.setDevice_name(TextUtils.isEmpty(devicename.getText().toString()) ? "" : devicename.getText().toString());
                        if (hidePIN.getVisibility() != View.GONE) {
                            singleData.setPINorPassorPattern(TextUtils.isEmpty(pin.getText().toString()) ? "" : pin.getText().toString());
                        }
                        if (hidePASS.getVisibility() != View.GONE) {
                            singleData.setPINorPassorPattern(TextUtils.isEmpty(pass.getText().toString()) ? "" : pass.getText().toString());
                        }

                        if (singleData.getSecurityType().equals("Pattern")) {
                            if (singleData.getPINorPassorPattern() != null) {
                                viewModel.insert(singleData);
                                addNew.dismiss();
                            } else {
                                addNew.dismiss();
                            }
                        } else {
                            if (!singleData.getPINorPassorPattern().equals("")) {
                                viewModel.insert(singleData);
                                addNew.dismiss();
                            } else {
                                addNew.dismiss();
                            }
                        }

                    }
                });

            }
        });


        return view;
    }

    private class dtype_spinner_class implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            singleData.setDevice_Type(parent.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class dsecutype_spinner_class implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).toString().equals("PIN")) {
                singleData.setSecurityType(parent.getItemAtPosition(position).toString());
                hidePASS.setVisibility(View.GONE);
                hidePAT.setVisibility(View.GONE);
                hidePIN.setVisibility(View.VISIBLE);
            } else if (parent.getItemAtPosition(position).toString().equals("Password")) {
                singleData.setSecurityType(parent.getItemAtPosition(position).toString());
                hidePAT.setVisibility(View.GONE);
                hidePIN.setVisibility(View.GONE);
                hidePASS.setVisibility(View.VISIBLE);
            } else {
                singleData.setSecurityType(parent.getItemAtPosition(position).toString());
                hidePIN.setVisibility(View.GONE);
                hidePASS.setVisibility(View.GONE);
                hidePAT.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class dtype_spinner_class_editDialog implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            singleData_editDialog.setDevice_Type(parent.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class dsecutype_spinner_class_editDialog implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).toString().equals("PIN")) {
                singleData_editDialog.setSecurityType(parent.getItemAtPosition(position).toString());
                hidePASS.setVisibility(View.GONE);
                hidePAT.setVisibility(View.GONE);
                hidePIN.setVisibility(View.VISIBLE);
            } else if (parent.getItemAtPosition(position).toString().equals("Password")) {
                singleData_editDialog.setSecurityType(parent.getItemAtPosition(position).toString());
                hidePAT.setVisibility(View.GONE);
                hidePIN.setVisibility(View.GONE);
                hidePASS.setVisibility(View.VISIBLE);
            } else {
                singleData_editDialog.setSecurityType(parent.getItemAtPosition(position).toString());
                hidePIN.setVisibility(View.GONE);
                hidePASS.setVisibility(View.GONE);
                hidePAT.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
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
        adapter.DEVICEtoggleSelection(position);
        int count = adapter.DEVICEgetSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void selectAll() {
        adapter.DEVICEselectAll();
        int count = adapter.DEVICEgetSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }

        //  actionMode = null;
    }

    private void deleteRows() {
        List<Integer> selectedItemPositions =
                adapter.DEVICEgetSelectedItems();
        if(adapter.getItemCount()==selectedItemPositions.size()){
            viewModel.deleteAll();
            actionMode = null;
        }
        else {
            for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                viewModel.delete(adapter.getDataFromList(selectedItemPositions.get(i)));
            }
            actionMode = null;
        }
    }

    private void shareSelected() {
        List<Integer> selectedItemPositions =
                adapter.DEVICEgetSelectedItems();
        StringBuilder builder = new StringBuilder();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            builder.append(adapter.DEVICEgetShareData(selectedItemPositions.get(i)) + "\n");
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
                adapter.DEVICEgetSelectedItems();

        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            viewModel.addtoFav(adapter.getDataFromList(selectedItemPositions.get(i)));
        }
        actionMode = null;
        Toast.makeText(getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
    }

    private void editSelected() {
        singleData_editDialog = new DEVICE_data();
        final List<Integer> selectedItemPositions =
                adapter.DEVICEgetSelectedItems();

        if (pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")) {
            editDialog = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
        } else if (pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")) {
            editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        } else {
            editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        }
        editDialog.setContentView(R.layout.add_new_device_dialog);

        dtype_spin = editDialog.findViewById(R.id.dtype_spinner);
        dsecutype_spin = editDialog.findViewById(R.id.dsecutype_spinner);
        devicename = editDialog.findViewById(R.id.device_edt_dname);
        pin = editDialog.findViewById(R.id.device_edt_PIN);
        pass = editDialog.findViewById(R.id.device_edt_pass);
        mPatternLockView = editDialog.findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener_editDialog);
        hidePASS = editDialog.findViewById(R.id.tohide_passtv_ll);
        hidePIN = editDialog.findViewById(R.id.tohide_pintv_ll);
        hidePAT = editDialog.findViewById(R.id.tohide_pattern_layout_dialog);
        dSave = editDialog.findViewById(R.id.save_device_button);
        dtype_spin.setOnItemSelectedListener(new dtype_spinner_class_editDialog());
        dsecutype_spin.setOnItemSelectedListener(new dsecutype_spinner_class_editDialog());
        ArrayAdapter<CharSequence> dtype_spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.DEVICE_TYPE, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> dsecutype_spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.DEVICE_SECURITY_TYPE, android.R.layout.simple_spinner_item);
        dtype_spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dtype_spin.setAdapter(dtype_spin_adapter);
        dsecutype_spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dsecutype_spin.setAdapter(dsecutype_spin_adapter);

        int spinnerPositiondtype = dtype_spin_adapter.getPosition(adapter.getDataFromList(selectedItemPositions.get(0)).getDevice_Type());
        dtype_spin.setSelection(spinnerPositiondtype);
        int spinnerPositiondsecutype = dsecutype_spin_adapter.getPosition(adapter.getDataFromList(selectedItemPositions.get(0)).getSecurityType());
        dsecutype_spin.setSelection(spinnerPositiondsecutype);
        devicename.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getDevice_name());
        if (adapter.getDataFromList(selectedItemPositions.get(0)).getSecurityType().equals("PIN")) {
            pin.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getPINorPassorPattern());
            hidePASS.setVisibility(View.GONE);
            hidePIN.setVisibility(View.VISIBLE);
            hidePAT.setVisibility(View.GONE);
        }
        if (adapter.getDataFromList(selectedItemPositions.get(0)).getSecurityType().equals("Password")) {
            pass.setText(adapter.getDataFromList(selectedItemPositions.get(0)).getPINorPassorPattern());
            hidePIN.setVisibility(View.GONE);
            hidePASS.setVisibility(View.VISIBLE);
            hidePAT.setVisibility(View.GONE);
        }
        if (adapter.getDataFromList(selectedItemPositions.get(0)).getSecurityType().equals("Pattern")) {
            hidePIN.setVisibility(View.GONE);
            hidePASS.setVisibility(View.GONE);
            hidePAT.setVisibility(View.VISIBLE);
        }

        editDialog.show();

        dSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleData_editDialog.setData_type("DEVICE");

                singleData_editDialog.setSqldeviceID(adapter.getDataFromList(selectedItemPositions.get(0)).getSqldeviceID());

                singleData_editDialog.setDevice_name(TextUtils.isEmpty(devicename.getText().toString()) ? "" : devicename.getText().toString());
                if (hidePIN.getVisibility() != View.GONE) {
                    Log.d(msg, "PIN set in singleData_editDialog");
                    singleData_editDialog.setPINorPassorPattern(TextUtils.isEmpty(pin.getText().toString()) ? "" : pin.getText().toString());
                }
                if (hidePASS.getVisibility() != View.GONE) {
                    Log.d(msg, "Pass set in singleData_editDialog");
                    singleData_editDialog.setPINorPassorPattern(TextUtils.isEmpty(pass.getText().toString()) ? "" : pass.getText().toString());
                }

                if (singleData_editDialog.getSecurityType().equals("Pattern")) {
                    if (singleData_editDialog.getPINorPassorPattern() != null) {
                        viewModel.update(singleData_editDialog);
                        editDialog.dismiss();
                    } else {
                        editDialog.dismiss();
                    }
                } else {
                    if (!singleData_editDialog.getPINorPassorPattern().equals("")) {
                        viewModel.update(singleData_editDialog);
                        editDialog.dismiss();
                    } else {
                        editDialog.dismiss();
                    }
                }
            }
        });
    }

}
