package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.PersonModelList;


/**
 * select all person
 */
public class PersonSelectAllCtx extends AbstractCmdCtx {
    private long _rowId;
    private PersonModelList _selected;

    public PersonSelectAllCtx(Context androidContext) {
        super(CommandEnum.PERSON_SELECT_ALL, androidContext);
    }

    public Long getRowId() {
        return _rowId;
    }

    public void setRowId(Long arg) {
        _rowId = arg;
    }

    public PersonModelList getSelected() {
        return _selected;
    }

    public void setSelected(PersonModelList arg) {
        _selected = arg;
    }
}
