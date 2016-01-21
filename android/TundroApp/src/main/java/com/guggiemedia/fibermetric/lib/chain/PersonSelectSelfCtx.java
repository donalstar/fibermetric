package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.PersonModel;


/**
 * discover which person owns the application
 */
public class PersonSelectSelfCtx extends AbstractCmdCtx {
    private PersonModel _selected;

    public PersonSelectSelfCtx(Context androidContext) {
        super(CommandEnum.PERSON_SELECT_SELF, androidContext);
    }

    public PersonModel getSelected() {
        return _selected;
    }

    public void setSelected(PersonModel arg) {
        _selected = arg;
    }
}
