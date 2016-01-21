package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.db.ChatModel;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.ui.utility.StringUtils;
import com.guggiemedia.fibermetric.ui.utility.TimeUtils;


/**
 *
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    public static final String LOG_TAG = ChatListAdapter.class.getName();

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;
    private ContentFacade _contentFacade;

    public ChatListAdapter(Activity activity) {
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

        Log.d(LOG_TAG, "itemCount:" + count);

        return count;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        _cursor.moveToPosition(position);

        final ChatModel model = new ChatModel();
        model.setDefault();
        model.fromCursor(_cursor);

        PersonModel personModel = (model.getPersonId() != null)
                ? _contentFacade.selectPersonByRowId(model.getPersonId(), _context) : null;

        final String participantName = (personModel == null) ? model.getParticipant() : personModel.getName();

        viewHolder.rowId = model.getId();
        viewHolder.name.setText(participantName);

        String message = model.getLastMessage();


        viewHolder.message.setText(message);

        String messageTime = TimeUtils.getDisplayableDateTime(model.getLastMessageTime());

        viewHolder.messageTime.setText(messageTime);

        viewHolder.initials.setText(StringUtils.getAsInitials(participantName));

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong(ChatFragment.ARG_PARAM_ROW_ID, viewHolder.rowId);
                bundle.putString(ChatFragment.ARG_PARTICIPANT_NAME, participantName);

                if (model.getPersonId() != null) {
                    bundle.putLong(ChatFragment.ARG_PERSON_ID, model.getPersonId());
                }

                bundle.putLong(ChatFragment.ARG_CHAT_ID, model.getId());

                _listener.fragmentSelect(MainActivityFragmentEnum.CHAT_VIEW, bundle);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public Long rowId;
        public final TextView name;
        public final TextView message;
        public final TextView messageTime;
        public final TextView initials;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            name = (TextView) view.findViewById(R.id.chatRowName);
            message = (TextView) view.findViewById(R.id.chatRowMessage);

            messageTime = (TextView) view.findViewById(R.id.messageTime);

            initials = (TextView) view.findViewById(R.id.initials);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText();
        }
    }
}