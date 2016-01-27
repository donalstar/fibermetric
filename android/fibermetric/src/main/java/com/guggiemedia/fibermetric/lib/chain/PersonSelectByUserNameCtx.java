package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.PersonModel;


/**
 * select person by user name command context
 */
public class PersonSelectByUserNameCtx extends AbstractCmdCtx {
    private String _userName;
    private PersonModel _selected;

    public PersonSelectByUserNameCtx(Context androidContext) {
        super(CommandEnum.PERSON_SELECT_BY_USER_NAME, androidContext);
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String arg) {
        _userName = arg;
    }

    public PersonModel getSelected() {
        return _selected;
    }

    public void setSelected(PersonModel arg) {
        _selected = arg;
    }
}