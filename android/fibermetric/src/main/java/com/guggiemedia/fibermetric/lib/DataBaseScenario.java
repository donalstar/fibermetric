package com.guggiemedia.fibermetric.lib;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.chain.CommandEnum;
import com.guggiemedia.fibermetric.lib.chain.CommandFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextList;
import com.guggiemedia.fibermetric.lib.chain.PartUpdateCtx;
import com.guggiemedia.fibermetric.lib.db.PartModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Seed the database for various scenarios
 */
public class DataBaseScenario {

    public List<PartModel> loadParts(Context context) {



        String[][] items = {
                {"Brussel Sprouts", "1U-8760"},
                {"Oil Filter Cutter GP", "175-7546"},
                {"Magnetic testing rod", "MK 160/14"},
                {"Used Oil Container", "BTZ11849"},
                {"Diesel Engine Servicing Kit", "TK-45"},
                {"Ultrasonic grease gun", "UP-201"},
                {"Torque Wrench Kit", "5WFL7"},
                {"Turbine Water Wash system", "GTE-W3418"},
                {"Temperature sensor", "TP-32154"},
                // parts
                {"Oil Filter - CAT", "Lf691"},
                {"Oil Filter - JD", "523-1693JD"},
                {"Engine Oil - CAT", "444-3551"},
                {"Engine Oil - JD", "TY26673"},
                {"Bearing Lubricant", "ISO-VG-68"}
        };

        String barcodes[] = {
                "722252161116",
                "0",
                "0",
                "0",
                "0",
                "009800007615",
                "312546629363",
                "0",
                "0",
                "0",
                "0",
                "0",
                "011594100373",
                "381370047544"
        };

        String bleAddresses[] = {
                "0",
                "0",
                "0",
                "0",
                "0",
                "C6:BC:A9:5E:B7:4E",
//                "67:34:0B:AE:CD:C4",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "67:34:0B:AE:CD:C4"
        };

        String manufacturers[] = {
                "Caterpillar",
                "Caterpillar",
                "Magsy",
                "Blitz",
                "Operations",
                "UE Systems",
                "Zoro",
                "GTE",
                "@Temp",
                "Caterpillar",
                "John Deere",
                "Caterpillar",
                "John Deere",
                "Sunoco"
        };

        String serialNumbers[] = {
                "CW-1453",
                "CG-4214",
                "MK-1287",
                "OC-7342",
                "TK-45",
                "UG-514",
                "TWK-12987",
                "TW-12",
                "TP-451",
                "OF-C187",
                "OF-JD42",
                "Oil-235",
                "Oil 263",
                "LU-3268"
        };

        String descriptions[] = {
                "Chain Wrench for removing oil filter in diesel powered industrial engines",
                "Oil Filter Cutter to assist in oil filter inspection",
                "Magnetic testing rod to inspect metal fragments in a used oil filter",
                "Blitz USA 12 QUART DISPOS-OIL CONTAINER",
                "General toolkit for diesel engine servicing jobs",
                "Ultraprobe 201 - Grease Caddy",
                "Torque Multiplier, 1250 ft-lb, 1/2x3/4 Dr",
                "GTE Turbine - Compressor Water Wash and Cleaning System",
                "Magnetic temperature sensor to monitor temperature of metal surfaces",
                "Oil Filter for CAT C18 Engines",
                "Oil Filter for John Deere Powertech 9.0l",
                "DEO Cold Weather synthetic engine oil",
                "Plus-50™ II Engine Oil synthetic 0w-40",
                "Sunep VG 68 Bearing Lubricant - standard temp"
        };

        Set<String> pickupParts = new HashSet<>(Arrays.asList(
                new String[]{"Ultrasonic grease gun", "Torque Wrench Kit", "Bearing Lubricant"}
        ));

        List<PartModel> models = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            PartModel model = new PartModel();
            model.setDefault();

            String name = items[i][0];

            model.setName(name);


            model.setManufacturer(manufacturers[i]);
            model.setModelNumber(items[i][1]);
            model.setSerial(serialNumbers[i]);
            model.setBarcode(barcodes[i]);
            model.setOwner("John Smith");
            model.setPickedUpDate(new Date());
            model.setCondition("Good");


            model.setBleAddress(bleAddresses[i]);


            model.setDescription(descriptions[i]);
            models.add(model);

            ContextList list = new ContextList();

            addToPartsContextList(model, list, context);

            CommandFactory.execute(list);
        }

        return models;
    }

    private void addToPartsContextList(PartModel model, ContextList list, Context context) {
        PartUpdateCtx ctx
                = (PartUpdateCtx) ContextFactory.factory(CommandEnum.PART_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }


}