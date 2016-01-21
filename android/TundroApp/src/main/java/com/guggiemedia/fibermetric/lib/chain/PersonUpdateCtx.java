package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.PersonModel;


/**
 * insert/update person command context
 */
public class PersonUpdateCtx extends AbstractCmdCtx {
    private PersonModel _personModel;

    public PersonUpdateCtx(Context androidContext) {
        super(CommandEnum.PERSON_UPDATE, androidContext);
    }

    public PersonModel getModel() {
        return _personModel;
    }

    public void setModel(PersonModel arg) {
        _personModel = arg;
    }
}
