package com.soi.notemanagementsystem.ui.category;

import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soi.notemanagementsystem.R;
import com.soi.notemanagementsystem.database.DatabaseHandler;
import com.soi.notemanagementsystem.model.Category;
import com.soi.notemanagementsystem.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class CategoryFagment extends Fragment {

    private CategoryViewModel mViewModel;
    private FloatingActionButton btnAddCategory;
    private Dialog addCategoryDialog;
    private EditText txtCategoryName;
    private Button btnAdd, btnClose;
    private RecyclerView recyclerView;
    private ArrayList categories;
    private CategoryAdapter categoryAdapter;



    public static CategoryFagment newInstance() {
        return new CategoryFagment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        // TODO: Use the ViewModel
        addComponents();
        addEvents();
        initCategories();
    }

    private void initCategories() {
        DatabaseHandler db = new DatabaseHandler(getContext());
        categories = db.getAllCategories();
        categoryAdapter = new CategoryAdapter(getActivity(),categories);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addEvents() {

        //TEST
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCategoryName.setText("");
                showAddCategoryDialog();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCatergory = txtCategoryName.getText().toString();
                if (newCatergory.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter category", Toast.LENGTH_SHORT).show();
                    return;
                }
                Category category = new Category(txtCategoryName.getText().toString());
                new DatabaseHandler(getContext()).addCategory(category);
                categoryAdapter.notifyDataSetChanged();
                recyclerView.removeAllViews();
                initCategories();
                addCategoryDialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategoryDialog.dismiss();
            }
        });


        //TEST






    }

    private void addComponents() {
        recyclerView = getView().findViewById(R.id.recycler_view);
        addCategoryDialog = new Dialog(getActivity());
        addCategoryDialog.setContentView(R.layout.dialog_category);
        btnAddCategory = getView().findViewById(R.id.btnAddCategory);

        txtCategoryName = addCategoryDialog.findViewById(R.id.txtNewCategory);
        btnAdd = addCategoryDialog.findViewById(R.id.btnAdd);
        btnClose = addCategoryDialog.findViewById(R.id.btnClose);



    }

    private void showAddCategoryDialog() {
        addCategoryDialog.show();

    }

}
