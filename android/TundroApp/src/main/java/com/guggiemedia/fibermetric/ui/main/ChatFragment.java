package com.guggiemedia.fibermetric.ui.main;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.ChatMessageModel;
import com.guggiemedia.fibermetric.lib.db.ChatMessageTable;
import com.guggiemedia.fibermetric.lib.db.ChatMessageTypeEnum;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.DataBaseTable;
import com.guggiemedia.fibermetric.lib.db.PersonModel;

import net.go_factory.tundro.R;



import java.util.Date;

/**
 * Created by donal on 9/28/15.
 */
public class ChatFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String FRAGMENT_TAG = "FRAGMENT_CHAT";
    public static final String ARG_PARAM_ROW_ID = "PARAM_ROW_ID";
    public static final String ARG_PARTICIPANT_NAME = "PARTICIPANT_NAME";
    public static final String ARG_PERSON_ID = "PERSON_ID";
    public static final String ARG_CHAT_ID = "CHAT_ID";
    public static final String ARG_PHONE_NUMBER = "PHONE_NUMBER";

    public static final String LOG_TAG = ChatFragment.class.getName();

    private ChatAdapter _adapter;
    private MainActivityListener _listener;
    private ContentFacade _contentFacade;
    private PersonModel _participantPersonModel;

    private EditText messageEditField;

    public static final int LOADER_ID = 271920;

    // LoaderCallback
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader:" + id);

        DataBaseTable table = new ChatMessageTable();

        String[] projection = table.getDefaultProjection();
        String orderBy = table.getDefaultSortOrder();

        String selection = "chat_id=?";

        String[] selectionArgs = {String.valueOf(args.getLong(ChatFragment.ARG_PARAM_ROW_ID))};

        return new CursorLoader(getActivity(), ChatMessageTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
    }

    // LoaderCallback
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            _adapter.setCursor(cursor);
            _adapter.notifyDataSetChanged();
        }
    }

    // LoaderCallback
    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.setCursor(null);
    }

    public static ChatFragment newInstance(Bundle args) {
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
        _adapter = new ChatAdapter(activity);
        _contentFacade = new ContentFacade();

        String participant = getArguments().getString(ChatFragment.ARG_PARTICIPANT_NAME);
        ((AppCompatActivity) this.getActivity()).getSupportActionBar().setTitle(participant);

        _participantPersonModel = loadParticipantPersonModel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        messageEditField = (EditText) view.findViewById(R.id.messageEdit);

        final RecyclerView messagesView = (RecyclerView) view.findViewById(R.id.messagesContainer);

        messagesView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        messagesView.setAdapter(_adapter);

        ImageView sendButtonView = (ImageView) view.findViewById(R.id.sendButtonView);

        sendButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditField.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(messageEditField.getWindowToken(), 0);

                messageEditField.setText("");

                addChatMessage(messageText);

                _adapter.notifyDataSetChanged();

                messagesView.scrollToPosition(_adapter.getItemCount() - 1);
            }
        });

        return view;
    }

    /**
     * @param messageText
     */
    private void addChatMessage(String messageText) {
        ChatMessageModel model = new ChatMessageModel();

        model.setName(getArguments().getString(ChatFragment.ARG_PARTICIPANT_NAME));
        model.setChatId(getArguments().getLong(ChatFragment.ARG_CHAT_ID));
        model.setMessageTime(new Date());
        model.setMessage(messageText);
        model.setType(ChatMessageTypeEnum.outbound);

        CommandFacade.chatMessageUpdate(model, getActivity().getApplicationContext());
    }

    /**
     * @return
     */
    private PersonModel loadParticipantPersonModel() {
        PersonModel personModel = null;

        Long personId = getArguments().getLong(ChatFragment.ARG_PERSON_ID);

        if (personId != null) {
            personModel = _contentFacade.selectPersonByRowId(personId, getActivity());
        }

        return personModel;
    }

    /**
     *
     */
    private void makePhoneCall() {
        if (_participantPersonModel != null) {
            String number = _participantPersonModel.getPhone();

            Bundle bundle = new Bundle();
            bundle.putString(ChatFragment.ARG_PHONE_NUMBER, number);

            _listener.activitySelect(MainActivityEnum.PHONE_CALL_ACTIVITY, bundle);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, this.getArguments(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chat_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.fragmentSelect(MainActivityFragmentEnum.CHAT_LIST, new Bundle());
                break;
            case R.id.actionSearch:
                break;
            case R.id.actionPhone:
                makePhoneCall();
                break;
            case R.id.actionAddGroup:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }
}

