package com.guggiemedia.fibermetric.lib;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.chain.CommandEnum;
import com.guggiemedia.fibermetric.lib.chain.CommandFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextList;
import com.guggiemedia.fibermetric.lib.chain.PartUpdateCtx;
import com.guggiemedia.fibermetric.lib.db.ItemModel;
import com.guggiemedia.fibermetric.lib.db.ItemTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Seed the database for various scenarios
 */
public class DataBaseScenario {

    public List<ItemModel> loadParts(Context context) {


        String[][] items = {
                {"Artichoke", "1U-8760"},
                {"Beans, baked, plain", "1U-8760"},
                {"Beans, black", "1U-8760"},
                {"Beans, kidney, canned", "1U-8760"},
                {"Beans, lima", "1U-8760"},
                {"Beans, navy", "1U-8760"},
                {"Beans, pinto", "1U-8760"},
                {"Beans, white, canned", "1U-8760"},
                {"Chickpeas, canned", "1U-8760"},
                {"Lentils", "1U-8760"},
                {"Mixed vegetables, frozen", "1U-8760"},
                {"Peas, green, frozen", "1U-8760"},
                {"Peas, split", "1U-8760"},
                {"Potato, baked, wi/skin", "1U-8760"},
        };

        String portions[] = {
                "1 medium",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "1 medium"
        };

        Double grams[] = {
                10.3,
                5.2,
                7.5,
                6.9,
                6.6,
                9.5,
                7.7,
                6.3,
                5.3,
                7.8,
                4.0,
                4.4,
                8.2,
                4.4
        };

        ItemTypeEnum types[] = {
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable,
                ItemTypeEnum.vegetable
        };

        List<ItemModel> models = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            ItemModel model = new ItemModel();
            model.setDefault();

            String name = items[i][0];

            model.setName(name);

            model.setPortion(portions[i]);

            model.setGrams(grams[i]);
            model.setType(types[i]);

            models.add(model);

            ContextList list = new ContextList();

            addToPartsContextList(model, list, context);

            CommandFactory.execute(list);
        }

        return models;
    }

    private void addToPartsContextList(ItemModel model, ContextList list, Context context) {
        PartUpdateCtx ctx
                = (PartUpdateCtx) ContextFactory.factory(CommandEnum.PART_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }


}