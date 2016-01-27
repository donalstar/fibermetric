package com.guggiemedia.fibermetric.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;
import com.guggiemedia.fibermetric.ui.main.MainActivity;
import com.guggiemedia.fibermetric.ui.utility.PageViewHelper;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


/**
 * user authentication
 */
public class LoginActivity extends Activity {
    public static final String LOG_TAG = LoginActivity.class.getName();
    public static final String ACTIVITY_TAG = "ACTIVITY_LOGIN";
    public static final String ARG_INITIAL_LOGIN = "INITIAL_LOGIN";

    private EditText _editPassWord;
    private EditText _editUserName;

    private final UserPreferenceHelper _uph = new UserPreferenceHelper();

    private void happyLogIn() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);

        intent.putExtra(LoginActivity.ARG_INITIAL_LOGIN, true);

        startActivity(intent);

        finish();  // inhibit back stack navigation
    }

    private void attemptLogin() {
        String passWord = _editPassWord.getText().toString().trim();
        String userName = _editUserName.getText().toString().trim();

        /* TODO implement password check
        if (passWord.isEmpty()) {
            ToastHelper.show(R.string.toastMissingPassWord, this);
            return;
        }
        */

        if (userName.isEmpty()) {
            ToastHelper.show(R.string.toastMissingUserName, this);
            return;
        }

        boolean flag = CommandFacade.authenticationSignIn(userName, passWord, this);
        if (flag) {
            happyLogIn();
        } else {
            ToastHelper.show(R.string.toastSignInFailure, this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PageViewHelper.activityTransition(ACTIVITY_TAG, this);

        if (CommandFacade.authenticationTest(this)) {
            happyLogIn();
        } else {
            _editPassWord = (EditText) findViewById(R.id.editPassWord);
            _editUserName = (EditText) findViewById(R.id.editUserName);

            final Button button = (Button) findViewById(R.id.buttonSubmit);
            button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    attemptLogin();
                }
            });
        }
    }
}
