package vnu.uet.tuan.uetsupporter.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Animation.RecyclerAnim;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * Created by vmtuan on 3/1/2017.
 */

public class RecyclerAdapterInboxAndSentMessage extends RecyclerView.Adapter {
    private ArrayList<Email> list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private int previousposition = -1;
    private final String TAG = this.getClass().getName();

    public RecyclerAdapterInboxAndSentMessage(Context context, ArrayList<Email> list) {
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
            View view = inflater.inflate(R.layout.item_email_recycler_inbox, parent, false);
            RecyclerAdapterInboxAndSentMessage.ItemViewHolder holder = new RecyclerAdapterInboxAndSentMessage.ItemViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerAdapterInboxAndSentMessage.ItemViewHolder) {
            //===================INIT ITEM==============================

            RecyclerAdapterInboxAndSentMessage.ItemViewHolder itemViewHolder = (RecyclerAdapterInboxAndSentMessage.ItemViewHolder) holder;
            Email email = list.get(position);

            itemViewHolder.from.setText(email.getFrom());
            itemViewHolder.timeReceived.setText(email.getReceiveDate());
            if (!email.isRead()) {
                itemViewHolder.title.setTypeface(null, Typeface.BOLD);
            }
            itemViewHolder.title.setText(email.getTitle());
            String content = email.getContent().trim().toLowerCase();
            itemViewHolder.content.setText(content);

            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .fontSize(30) /* size in px */
                    .bold()
                    .toUpperCase()
                    .endConfig()
                    .buildRoundRect(Utils.getFirstChar(email.getFrom()), Utils.getRandomColor(email.getFrom()), 15);
            itemViewHolder.avatar.setImageDrawable(drawable);

            itemViewHolder.hasFile.setVisibility(
                    email.isHasFile() ? View.VISIBLE : View.INVISIBLE);

            Log.e(TAG, email.getImportance());

            if (email.getImportance().toLowerCase().contains("high") || email.getImportance().toLowerCase().equals("high")) {
                itemViewHolder.importance.setImageResource(R.drawable.red_dot);
            }

            itemViewHolder.reply_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Reply", Toast.LENGTH_SHORT).show();
                }
            });

            itemViewHolder.tool.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(v);
                }
            });

            //====================ANIMATION=========================
            if (position >= previousposition) {
                RecyclerAnim.animate(itemViewHolder, true);
            } else RecyclerAnim.animate(itemViewHolder, false);

            previousposition = position;

        }


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.hopthongbao_popup_menu);
        popupMenu.show();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView from, timeReceived, title, content;
        ImageView tool, hasFile, importance, reply_now, avatar;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            from = (TextView) itemView.findViewById(R.id.from);
            timeReceived = (TextView) itemView.findViewById(R.id.timeRecived);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.description);

            tool = (ImageView) itemView.findViewById(R.id.tool);
            hasFile = (ImageView) itemView.findViewById(R.id.img_hasFile);
            importance = (ImageView) itemView.findViewById(R.id.img_importance);
            reply_now = (ImageView) itemView.findViewById(R.id.img_reply);
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

    public RecyclerAdapterInboxAndSentMessage.ClickListener clickListener;

    public void setOnItemClickListener(RecyclerAdapterInboxAndSentMessage.ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}