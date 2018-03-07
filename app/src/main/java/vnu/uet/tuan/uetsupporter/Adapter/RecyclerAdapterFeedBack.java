package vnu.uet.tuan.uetsupporter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Comment;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * Created by vmtuan on 3/12/2017.
 */

public class RecyclerAdapterFeedBack extends RecyclerView.Adapter {
    private final String TAG = this.getClass().getSimpleName();

    List<Comment> list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;

    public RecyclerAdapterFeedBack(Context context, List<Comment> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_comment_recycler, parent, false);
            RecyclerAdapterFeedBack.ItemViewHolder holder = new RecyclerAdapterFeedBack.ItemViewHolder(view);
            return holder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerAdapterFeedBack.ItemViewHolder) {
            final RecyclerAdapterFeedBack.ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Comment comment = list.get(position);
            itemViewHolder.avatar.setImageResource(R.drawable.filter_black_36x36);
            itemViewHolder.noiDung.setText(comment.getNoiDung());
            itemViewHolder.nameUser.setText(comment.getUserComment().getName());
            itemViewHolder.time.setText(Utils.getThoiGian(context,comment.getTime()));

            Log.e(TAG, comment.getUserComment().getName());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView nameUser, noiDung, time;
        ImageView avatar;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            nameUser = (TextView) itemView.findViewById(R.id.username);
            noiDung = (TextView) itemView.findViewById(R.id.noidung);
            time = (TextView) itemView.findViewById(R.id.time);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    //Listener
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public ClickListener clickListener;

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}