package vnu.uet.tuan.uetsupporter.Adapter;

/**
 * Created by Administrator on 13/01/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import vnu.uet.tuan.uetsupporter.Animation.RecyclerAnim;
import vnu.uet.tuan.uetsupporter.Model.Notification;
import vnu.uet.tuan.uetsupporter.Model.TinTuc;
import vnu.uet.tuan.uetsupporter.R;

public class PatternRecyclerAdapterTinTuc extends RecyclerView.Adapter {
    private TinTuc[] list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private int previousposition = -1;

    public PatternRecyclerAdapterTinTuc(Context context, TinTuc[] list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getItemViewType(int position) {
//        return list[position].getTitle().equals("") ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.pattern_item_recycler_factory_tintuc, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }
//        else{
//            View view = inflater.inflate(R.layout.footer_view,parent,false);
//            LoadingViewHolder holder = new LoadingViewHolder(view);
//            return holder;
//        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.txt_title.setText(list[position].getTitle());
            itemViewHolder.txt_postat.setText(list[position].getPostAt());

            //animation
            if (position >= previousposition) {
                RecyclerAnim.animate(itemViewHolder, true);
            } else RecyclerAnim.animate(itemViewHolder, false);

            previousposition = position;

        }
//        else {
//            //can than cho nay nhe
//            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
//            if(getItemCount()==1&&list.get(position).getContent().equals("REFRESH")){
//                loadingViewHolder.progressBar.setVisibility(View.INVISIBLE);
//            }
//            else if(getItemCount()==1&&list.get(position).getContent().equals("UNREFRESH")){
//                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
//                loadingViewHolder.progressBar.setIndeterminate(true);
//            }
//            else {
//                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
//                loadingViewHolder.progressBar.setIndeterminate(true);
//            }
//
//        }


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.length;
    }


    //    static class LoadingViewHolder extends RecyclerView.ViewHolder {
//        public ProgressBar progressBar;
//
//        public LoadingViewHolder(View itemView) {
//            super(itemView);
//            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
//        }
//    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        TextView txt_postat;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            txt_title = (TextView) itemView.findViewById(R.id.recycle_item_title);
            txt_postat = (TextView) itemView.findViewById(R.id.recycle_item_postat);

        }
    }
}