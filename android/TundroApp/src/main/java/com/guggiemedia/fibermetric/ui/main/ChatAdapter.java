package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.db.ChatMessageModel;
import com.guggiemedia.fibermetric.lib.db.ChatMessageTypeEnum;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.ui.utility.StringUtils;
import com.guggiemedia.fibermetric.ui.utility.TimeUtils;


/**
 * Created by donal on 9/30/15.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public static final String LOG_TAG = ChatListAdapter.class.getName();

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;
    private ContentFacade _contentFacade;

    public ChatAdapter(Activity activity) {
        _listener = (MainActivityListener) activity;
        _context = activity;

        _contentFacade = new ContentFacade();
    }

    public void setCursor(Cursor cursor) {
        _cursor = cursor;
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (_cursor != null) {
            count = _cursor.getCount();
        }

        return count;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        _cursor.moveToPosition(position);

        ChatMessageModel model = new ChatMessageModel();
        model.setDefault();
        model.fromCursor(_cursor);

        viewHolder.rowId = model.getId();

        PersonModel personModel = (model.getPersonId() != null)
                ? _contentFacade.selectPersonByRowId(model.getPersonId(), _context) : null;

        String participantName = (personModel == null) ? model.getName() : personModel.getName();

        setAlignment(viewHolder, model, participantName);

        String message = model.getMessage();


        viewHolder.txtMessage.setText(message);

        String messageTime = TimeUtils.getDisplayableDateTime(model.getMessageTime());

        viewHolder.dateTimeLabel.setText(messageTime);
    }

    private void setAlignment(ViewHolder holder, ChatMessageModel model, String participantName) {
        int layoutGravity = (model.getType().equals(ChatMessageTypeEnum.outbound))
                ? Gravity.RIGHT : Gravity.LEFT;

        int backgroundResource = (model.getType().equals(ChatMessageTypeEnum.outbound))
                ? R.drawable.in_message_bg : R.drawable.out_message_bg;

        holder.messageBubble.setBackgroundResource(backgroundResource);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.messageBubble.getLayoutParams();

        layoutParams.gravity = layoutGravity;

        holder.messageBubble.setLayoutParams(layoutParams);

        layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        layoutParams.gravity = layoutGravity;
        holder.txtMessage.setLayoutParams(layoutParams);

        layoutParams = (LinearLayout.LayoutParams) holder.dateTimeLabel.getLayoutParams();
        layoutParams.gravity = layoutGravity;
        holder.dateTimeLabel.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();

        if (model.getType().equals(ChatMessageTypeEnum.outbound)) {
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }

        holder.content.setLayoutParams(lp);

        if (model.getType().equals(ChatMessageTypeEnum.inbound)) {
            holder.initialsFrame.setVisibility(View.VISIBLE);

            holder.initials.setText(StringUtils.getAsInitials(participantName));
        } else {
            holder.initialsFrame.setVisibility(View.GONE);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_message, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public Long rowId;
        public TextView dateTimeLabel;

        public TextView txtMessage;
        public LinearLayout content;
        public LinearLayout messageBubble;
        public LinearLayout messageRow;

        public FrameLayout initialsFrame;
        public TextView initials;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            dateTimeLabel = (TextView) view.findViewById(R.id.dateTimeLabel);

            content = (LinearLayout) view.findViewById(R.id.content);
            messageBubble = (LinearLayout) view.findViewById(R.id.messageBubble);

            messageRow = (LinearLayout) view.findViewById(R.id.messageRow);

            txtMessage = (TextView) view.findViewById(R.id.txtMessage);

            initialsFrame = (FrameLayout) view.findViewById(R.id.initialsFrame);
            initials = (TextView) view.findViewById(R.id.initials);
        }
    }
}
