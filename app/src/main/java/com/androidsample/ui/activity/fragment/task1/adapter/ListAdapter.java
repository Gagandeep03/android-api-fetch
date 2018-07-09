package com.androidsample.ui.activity.fragment.task1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidsample.beans.ListBeanModel;
import com.androidsample.databinding.ListImageviewItemBinding;
import com.androidsample.ui.baseclass.BaseViewHolder;

import java.util.List;

/**
 * Created by Gagandeep on 16-06-2018.
 */
public class ListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ListBeanModel> modelList;
    private final Context context;
    private int selectedItem = -1;
    private final int VIEW_ITEM_SELECTED = 1;
    private final int VIEW_ITEM_NORMAL = 0;
    DisplayNameInterface displayNameInterface;

    public ListAdapter(Context context, List<ListBeanModel> modelList) {
        this.modelList = modelList;
        this.context = context;
    }

    public void setDisplaySelectionListener(DisplayNameInterface interfaceObj) {
        this.displayNameInterface = interfaceObj;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListImageviewItemBinding binding = ListImageviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_ITEM_SELECTED) {
            holder.onBind(position, true);
        } else {
            holder.onBind(position, false);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == selectedItem)
            return VIEW_ITEM_SELECTED;
        else return VIEW_ITEM_NORMAL;
    }

    @Override
    public int getItemCount() {
        return modelList == null ? 0 : modelList.size();
    }

    public void setSelectedItem(int selectedItem) {

        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public void updateList(List<ListBeanModel> list) {
        modelList = list;
        notifyDataSetChanged();
    }


    public class ImageViewHolder extends BaseViewHolder {
        ListImageviewItemBinding binding;
        ImageItemViewModel itemViewModel;

        public ImageViewHolder(ListImageviewItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        @Override
        public void onBind(int position, boolean isSelected) {
            ListBeanModel model = modelList.get(position);
            model.setSelected(isSelected);
            if(isSelected)
            {
                displayNameInterface.showSelectedName(model.getName());
            }
            itemViewModel = new ImageItemViewModel(context, model);
            binding.setViewModel(itemViewModel);
            binding.executePendingBindings();
        }
    }

    public interface DisplayNameInterface {
        void showSelectedName(String name);
    }
}
