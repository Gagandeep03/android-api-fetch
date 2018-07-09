package com.androidsample.ui.activity.fragment.task1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidsample.beans.ResultsEntity;
import com.androidsample.databinding.DetailListItemViewBinding;
import com.androidsample.ui.baseclass.BaseViewHolder;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    DetailListItemViewBinding binding;
    private final Context context;
    ItemModelListener itemClickInterface;
    private List<ResultsEntity> modelList;

    public ListAdapter(Context context, List<ResultsEntity> modelList) {
        this.modelList = modelList;
        this.context = context;
    }

    public void setItemListener(ItemModelListener interfaceObj) {
        this.itemClickInterface = interfaceObj;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DetailListItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return modelList == null ? 0 : modelList.size();
    }


    public void updateList(List<ResultsEntity> list) {
        modelList = list;
        notifyItemRangeChanged(0, list.size(), list);
        // notifyDataSetChanged();
    }


    public interface ItemModelListener {
        void onItemClick(int position, ResultsEntity bean);
    }

    public class ItemViewHolder extends BaseViewHolder implements ListItemViewModel.TrayItemModelListener {
        DetailListItemViewBinding binding;
        ListItemViewModel itemViewModel;

        public ItemViewHolder(DetailListItemViewBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        @Override
        public void onBind(int position) {
            ResultsEntity model = modelList.get(position);
            itemViewModel = new ListItemViewModel(context, model, this, position);
            binding.setViewModel(itemViewModel);
            binding.executePendingBindings();
        }

        @Override
        public void onItemClick(int position, ResultsEntity bean) {
            if (itemClickInterface != null) {
                itemClickInterface.onItemClick(position, bean);
            }
        }
    }
}
