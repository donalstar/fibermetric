package com.guggiemedia.fibermetric;

import android.content.Context;

import com.guggiemedia.fibermetric.chain.AddedItemUpdateCtx;
import com.guggiemedia.fibermetric.chain.CommandEnum;
import com.guggiemedia.fibermetric.chain.CommandFactory;
import com.guggiemedia.fibermetric.chain.ContextFactory;
import com.guggiemedia.fibermetric.chain.ContextList;
import com.guggiemedia.fibermetric.chain.DailyRecordUpdateCtx;
import com.guggiemedia.fibermetric.chain.ItemUpdateCtx;
import com.guggiemedia.fibermetric.db.AddedItemModel;
import com.guggiemedia.fibermetric.db.DailyRecordModel;
import com.guggiemedia.fibermetric.db.ItemModel;
import com.guggiemedia.fibermetric.db.ItemTypeEnum;
import com.guggiemedia.fibermetric.db.PortionTypeEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Seed the database for various scenarios
 */
public class DataBaseScenario {

    public List<ItemModel> loadItems(Context context) {

        String[] items = {
                // vegetables
                "Artichoke",
                "Beans, baked, plain",
                "Beans, black",
                "Beans, kidney, canned",
                "Beans, lima",
                "Beans, navy",
                "Beans, pinto",
                "Beans, white, canned",
                "Chickpeas, canned",
                "Lentils",
                "Mixed vegetables, frozen",
                "Peas, green, frozen",
                "Peas, split",
                "Potato, baked, wi/skin",

                // Fruit
                "Blackberries",
                "Pear with peel",
                "Prunes (dried)",
                "Raspberries",

                // Grains
                "Almonds, roasted",
                "Bulgur",
                "Cereal, high fiber, bran",
                "Nuts, pistachios, pecans, walnuts",
                "Peanuts, roasted",
                "Quinoa"

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
                "1 medium",
                //Fruit
                "1/2 Cup",
                "1 medium",
                "1/2 Cup",
                "1/2 Cup",
                // Grains
                "1/2 Cup",
                "1/2 Cup",
                "1/2 Cup",
                "2 ounces",
                "1/2 Cup",
                "1/2 Cup"
        };

        PortionTypeEnum portionTypes[] = {
                PortionTypeEnum.unit,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.unit,
                PortionTypeEnum.cup,
                PortionTypeEnum.unit,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup,
                PortionTypeEnum.ounces,
                PortionTypeEnum.cup,
                PortionTypeEnum.cup
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
                4.4,

                3.8,
                4.0,
                5.7,
                4.2,

                6.4,
                4.1,
                6.5,
                5.0,
                6.1,
                5.0
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
                ItemTypeEnum.vegetable,

                ItemTypeEnum.fruit,
                ItemTypeEnum.fruit,
                ItemTypeEnum.fruit,
                ItemTypeEnum.fruit,

                ItemTypeEnum.grain,
                ItemTypeEnum.grain,
                ItemTypeEnum.grain,
                ItemTypeEnum.grain,
                ItemTypeEnum.grain,
                ItemTypeEnum.grain,
        };

        List<ItemModel> models = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            ItemModel model = new ItemModel();
            model.setDefault();

            String name = items[i];

            model.setName(name);

            model.setPortion(portions[i]);
            model.setPortionType(portionTypes[i]);

            model.setGrams(grams[i]);
            model.setType(types[i]);

            models.add(model);

            ContextList list = new ContextList();

            addToContextList(model, list, context);

            CommandFactory.execute(list);
        }

        return models;
    }

    private void addToContextList(ItemModel model, ContextList list, Context context) {
        ItemUpdateCtx ctx
                = (ItemUpdateCtx) ContextFactory.factory(CommandEnum.ITEM_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }

    public void loadAddedItems(Context context, List<DailyRecordModel> dailyRecords, List<ItemModel> itemModels) {
        // pick 2 days ago
        DailyRecordModel dailyRecord = dailyRecords.get(1);

        List<AddedItemModel> models = new ArrayList<>();

        int itemIndex[] = {2, 5, 8, 16, 20};

        // test: 5 added items
        for (int i = 0; i < 5; i++) {
            AddedItemModel model = new AddedItemModel();
            model.setDefault();

            ItemModel itemToAdd = itemModels.get(itemIndex[i]);

            model.setItemId(itemToAdd.getId());
            model.setSelectedPortion(itemToAdd.getPortion());
            model.setWeightMultiple(1.0);

            model.setDailyRecordId(dailyRecord.getId());

            ContextList list = new ContextList();

            addToAddedItemsContextList(model, list, context);

            CommandFactory.execute(list);
        }
    }

    private void addToAddedItemsContextList(AddedItemModel model, ContextList list, Context context) {
        AddedItemUpdateCtx ctx
                = (AddedItemUpdateCtx) ContextFactory.factory(CommandEnum.ADDED_ITEM_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }

    /**
     * @param context
     */
    public List<DailyRecordModel> loadHistory(Context context) {

        List<DailyRecordModel> models = new ArrayList<>();

        double dataValues[][] = new double[30][3];

        for (int i = 0; i < dataValues.length; i++) {

            double values[] = new double[3];

            for (int j = 0; j < values.length; j++) {
                values[j] = (Math.random() * 20);
            }

            dataValues[i] = values;
        }

        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < dataValues.length; i++) {
            double dailyValue[] = dataValues[i];

            DailyRecordModel model = new DailyRecordModel();
            model.setDefault();

            model.setFruit(dailyValue[0]);
            model.setVeg(dailyValue[1]);
            model.setGrain(dailyValue[2]);

            double total = dailyValue[0] + dailyValue[1] + dailyValue[2];
            model.setTotal(total);

            calendar.add(Calendar.DAY_OF_MONTH, -1);

            Date date = calendar.getTime();

            model.setDate(date);
            model.setDisplayDate(date.toString());
            ContextList list = new ContextList();

            addToHistoryContextList(model, list, context);

            CommandFactory.execute(list);

            models.add(model);
        }

        return models;
    }

    private void addToHistoryContextList(DailyRecordModel model, ContextList list, Context context) {
        DailyRecordUpdateCtx ctx
                = (DailyRecordUpdateCtx) ContextFactory.factory(CommandEnum.HISTORY_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }
}