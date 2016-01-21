package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.PersonPartModel;


/**
 * Created by donal on 9/29/15.
 */
public class PersonPartUpdateCtx extends AbstractCmdCtx {
    private PersonPartModel _model = new PersonPartModel();

    public PersonPartUpdateCtx(Context androidContext) {
        super(CommandEnum.PERSON_PART_UPDATE, androidContext);
    }

    public PersonPartModel getModel() {
        return _model;
    }

    public void setModel(PersonPartModel arg) {
        _model = arg;
    }
}
