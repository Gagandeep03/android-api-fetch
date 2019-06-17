package com.androidsample.ui.activity.fragment.task1.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidsample.beans.ResultLiveBean;
import com.androidsample.databinding.DetailListItemViewBinding;
import com.androidsample.ui.baseclass.BaseViewHolder;

public class ResultAdapter extends PagedListAdapter<ResultLiveBean, BaseViewHolder> {


    private final Context context;
    DetailListItemViewBinding binding;
    private ResultAdapter.ItemModelListener itemClickInterface;

    public ResultAdapter(Context context) {
        super(ResultLiveBean.DIFF_CALLBACK);
        this.context = context;
    }

    public void setItemListener(ResultAdapter.ItemModelListener interfaceObj) {
        this.itemClickInterface = interfaceObj;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return getCurrentList() == null ? 0 : getCurrentList().size();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DetailListItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ResultAdapter.ItemViewHolder(binding);
    }


    public interface ItemModelListener {
        void onItemClick(int position, ResultLiveBean bean);
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
            ResultLiveBean model = getCurrentList().get(position);
            itemViewModel = new ListItemViewModel(context, model, this, position);
            binding.setViewModel(itemViewModel);
            binding.executePendingBindings();
        }

        @Override
        public void onItemClick(int position, ResultLiveBean bean) {
            if (itemClickInterface != null) {
                itemClickInterface.onItemClick(position, bean);
            }
        }
    }
}
