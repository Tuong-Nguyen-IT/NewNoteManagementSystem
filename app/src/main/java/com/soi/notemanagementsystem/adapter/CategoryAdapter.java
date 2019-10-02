package com.soi.notemanagementsystem.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soi.notemanagementsystem.R;
import com.soi.notemanagementsystem.database.DatabaseHandler;
import com.soi.notemanagementsystem.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;

    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    private ArrayList<Category> categoryList = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtName.setText("Name: " + categoryList.get(position).getName());
        holder.txtCreateDate.setText("Create Date: " + categoryList.get(position).getCreateDateFomated());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(holder.parent_layout.getContext(), holder.parent_layout);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_category, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_edit:
                                final Dialog editCategoryDialog = new Dialog(holder.parent_layout.getContext());
                                editCategoryDialog.setContentView(R.layout.dialog_edit_category);
                                editCategoryDialog.show();
                                Button btnSave = editCategoryDialog.findViewById(R.id.btnSave);
                                Button btnCancle = editCategoryDialog.findViewById(R.id.btnCancle);
                                final EditText txtCategoryName = editCategoryDialog.findViewById(R.id.txtCategoryName);

                                btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Category category = new Category(categoryList.get(position).getId(), txtCategoryName.getText().toString());
                                        DatabaseHandler db = new DatabaseHandler(holder.parent_layout.getContext());
                                        db.editCateGory(category);
                                        editCategoryDialog.dismiss();
                                    }
                                });

                                btnCancle.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        editCategoryDialog.dismiss();
                                    }
                                });


                            case R.id.menu_Delete:

                                Toast.makeText(context, "Delete posstion " + position, Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        TextView txtCreateDate;
        LinearLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCreateDate = itemView.findViewById(R.id.txtCreateDate);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
