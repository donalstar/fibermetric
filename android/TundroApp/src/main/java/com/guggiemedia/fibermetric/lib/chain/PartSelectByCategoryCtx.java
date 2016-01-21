package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;

import java.util.ArrayList;
import java.util.List;

/**
 * select jobtask by parent ID command context
 */
public class PartSelectByCategoryCtx extends AbstractCmdCtx {

    private List<PartModel> _selected = new ArrayList<>();
    private InventoryCategoryEnum _category;

    public PartSelectByCategoryCtx(Context androidContext) {
        super(CommandEnum.PART_SELECT_BY_CATEGORY, androidContext);
    }

    public List<PartModel> getSelected() {
        return _selected;
    }

    public void setSelected(List<PartModel> arg) {
        _selected = arg;
    }

    public InventoryCategoryEnum getCategory() {
        return _category;
    }

    public void setCategory(InventoryCategoryEnum category) {
        this._category = category;
    }
}