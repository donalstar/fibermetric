package com.guggiemedia.fibermetric.lib;

import android.content.Context;
import android.location.Location;
import android.util.Log;


import com.guggiemedia.fibermetric.lib.chain.ChainOfCustodyUpdateCtx;
import com.guggiemedia.fibermetric.lib.chain.ChatMessageUpdateCtx;
import com.guggiemedia.fibermetric.lib.chain.ChatUpdateCtx;
import com.guggiemedia.fibermetric.lib.chain.CommandEnum;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.chain.CommandFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextList;
import com.guggiemedia.fibermetric.lib.chain.JobPartUpdateCtx;
import com.guggiemedia.fibermetric.lib.chain.PartUpdateCtx;
import com.guggiemedia.fibermetric.lib.chain.PersonPartUpdateCtx;
import com.guggiemedia.fibermetric.lib.chain.PersonUpdateCtx;
import com.guggiemedia.fibermetric.lib.chain.SiteUpdateCtx;
import com.guggiemedia.fibermetric.lib.db.ChainOfCustodyModel;
import com.guggiemedia.fibermetric.lib.db.ChatMessageModel;
import com.guggiemedia.fibermetric.lib.db.ChatMessageTypeEnum;
import com.guggiemedia.fibermetric.lib.db.ChatModel;
import com.guggiemedia.fibermetric.lib.db.ChatTypeEnum;
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.InventoryStatusEnum;
import com.guggiemedia.fibermetric.lib.db.JobPartModel;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskPriorityEnum;
import com.guggiemedia.fibermetric.lib.db.JobTypeEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.lib.db.PersonPartModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionEnum;
import com.guggiemedia.fibermetric.lib.db.TaskActionModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionModelList;
import com.guggiemedia.fibermetric.lib.db.TaskDetailModel;
import com.guggiemedia.fibermetric.lib.db.TaskDetailModelList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private void personList(PersonModel model, ContextList list, Context context) {
        PersonUpdateCtx ctx = (PersonUpdateCtx) ContextFactory.factory(CommandEnum.PERSON_UPDATE, context);
        ctx.setModel(model);
        list.add(ctx);
    }

    /**
     * NOTE: do not change load order, need reliable database keys
     */
    public List<PersonModel> loadPerson(Context context) {
        List<PersonModel> models = new ArrayList<>();

        String names[][] = {
                {"Bryan", "Hughes", ""},
                {"Allan", "McNichol", ""},
                {"Donal", "Carroll", ""},
                {"Guy", "Cole", "IV"},
        };

        String userNames[] = {
                "bryan",
                "allan",
                "donal",
                "guy"
        };

        String emails[] = {
                "bryan@go-factory.net",
                "allan@go-factory.net",
                "donal@go-factory.net",
                "guy@go-factory.net"
        };

        ContextList list = new ContextList();

        for (int i = 0; i < names.length; i++) {
            String name[] = names[i];

            PersonModel model = new PersonModel();
            model.setDefault();
            model.setNameFirst(name[0]);
            model.setNameLast(name[1]);

            if (!name[2].isEmpty()) {
                model.setNameSuffix(name[2]);
            }

            model.setNameUser(userNames[i]);
            model.setEmail(emails[i]);

            // set phone, for user "donal"
            if (userNames[i].equals("donal")) {
                model.setPhone("4154487023");
            }

            personList(model, list, context);

            models.add(model);
        }

        CommandFactory.execute(list);

        return models;
    }

    private void siteList(SiteModel model, ContextList list, Context context) {
        SiteUpdateCtx ctx = (SiteUpdateCtx) ContextFactory.factory(CommandEnum.SITE_UPDATE, context);
        ctx.setModel(model);
        list.add(ctx);
    }

    public List<SiteModel> loadSite(Context context) {
        List<SiteModel> siteModels = new ArrayList<>();

        ContextList list = new ContextList();

        String names[] = {
                // Texas
                "Wellpad 34",
                "Wellpad 52",
                "Wellpad 17",
                "Wellpad 22",
                "Wellpad 26",
                "Wellpad 147",
                "Wellpad 94",
                "Wellpad 115",
                "Wellpad 34",
                "Wellpad 52",
                "Wellpad 17",
                "Wellpad 22",
                "Wellpad 26",
                "Wellpad 147",
                "Wellpad 94",
                "Wellpad 115",
                "Pipeline AA2",
                "Pipeline AA2",
                "Pipeline AA3",
                "Pipeline AA4",
                "Equipment Shelter1"
        };

        String addresses[][] = {
                {"street1a", "street1b", "street1c", "street1d"},
                {"street2a", "street2b", "street2c", "street2d"}
        };

        String emptyAddress[] = {"", "", "", ""};

        Double points[][] = {
                // Midland, Texas
                {32.355556, -102.200002},    //Wellpad 34
                {32.371796, -102.184895},    //Wellpad 52
                {32.368316, -102.216481},    //Wellpad 17
                {32.364836, -102.270039},    //Wellpad 22
                {32.367156, -102.341451},    //Wellpad 26
                {32.442520, -102.315358},    //Wellpad 147
                {32.495817, -102.345570},    //Wellpad 94
                {32.506240, -102.272786},    //Wellpad 115
                {32.355556, -102.200002},    //Wellpad 34
                {32.371796, -102.184895},    //Wellpad 52
                {32.368316, -102.216481},    //Wellpad 17
                {32.364836, -102.270039},    //Wellpad 22
                {32.367156, -102.341451},    //Wellpad 26
                {32.442520, -102.315358},    //Wellpad 147
                {32.495817, -102.345570},    //Wellpad 94
                {32.506240, -102.272786},    //Wellpad 115
                {35.543356, -101.197156},    //Pipeline AA2
                {35.543354, -101.197155},    //Pipeline AA2
                {35.536651, -101.213635},    //Pipeline AA3
                {35.541121, -101.199902},    //Pipeline AA4
                {32.710998, -102.636370},    //Equipment Shelter1
        };


        for (int i = 0; i < names.length; i++) {
            SiteModel model = new SiteModel();
            model.setDefault();
            model.setName(names[i]);

            String address[] = (i < 2) ? addresses[i] : emptyAddress;

            model.setStreet1(address[0]);
            model.setStreet2(address[1]);

            Location location = new Location(names[i]);
            location.setLatitude(points[i][0]);
            location.setLongitude(points[i][1]);

            model.setLocation(location);

            siteList(model, list, context);

            siteModels.add(model);
        }

        SiteModel partsSiteModel = getSiteForParts();
        siteList(partsSiteModel, list, context);

        siteModels.add(partsSiteModel);

        CommandFactory.execute(list);

        return siteModels;
    }


    // set a location for all our parts (test!)
    private SiteModel getSiteForParts() {
        Double latLng[] = {32.355556, -102.200002};

        SiteModel model = new SiteModel();

        model.setDefault();
        model.setName("Home");
        model.setStreet1("645 Harrison St.");
        model.setStreet2("#200");
        model.setCity("San Francisco");
        model.setState("CA");
        model.setZip("94107");

        Location location = new Location(model.getName());
        location.setLatitude(latLng[0]);
        location.setLongitude(latLng[1]);

        model.setLocation(location);
        return model;
    }

    private void oilChangeTask(JobTaskModel job, Context context) {

        String taskNames[] = {
                "Drain the Engine Oil",
                "Remove the oil filter",
                "Cut the filter and Inspect for Metal Debris",
                "Clean the sealing surface",
                "Install the new oil filter",
                "Fill the Engine Crankcase with Oil",
                "Start Engine and check for leaks",
                "Inspect final oil level"
        };

        String taskDetails[][] = {
                {
                        "Position Blitz Dispos-Oil Container directly below valve",
                        "Turn the drain valve knob counterclockwise in order to drain the oil",
                        "After the oil has drained, turn the drain valve knob clockwise in order to close the drain valve"
                },

                {
                        "Remove the oil filter with a 1U-8760 Chain Wrench",
                        "Prepare for inspection"
                },

                {
                        "Cut the oil filter open with a 175-7546 Oil Filter Cutter Gp",
                        "Break apart the pleats and inspect the oil filter for metal debris",
                        "Use a magnet to differentiate between the ferrous metals and the nonferrous metals that are found in the oil filter element",
                        "Enter notes in the task about any concerns with the debris"
                },

                {
                        "Clean the sealing surface of the filter mounting base.",
                        "Ensure that all remnants of the old oil filter gasket are removed."
                },

                {
                        "Apply clean engine oil to the new oil filter gasket.",
                        "Tighten the oil filter until the oil filter gasket contacts the base.",
                        "Tighten the oil filter by hand according to the instructions that are shown on the oil filter.",
                        "Do not overtighten the oil filter."
                },
                {
                        "Remove the oil filler cap.",
                        "Fill the crankcase with oil.",
                        "Replace the oil filler cap."
                },
                {
                        "Start the engine and run the engine at 'LOW IDLE' for 2 minutes.",
                        "Inspect the oil filter for oil leaks."
                },

                {
                        "Remove the oil level gauge in order to check the oil level.",
                        "Ensure the oil level is between the 'ADD' and 'FULL' marks on the oil level gauge.",
                        "Add additional oil if necessary."
                }
        };


        TaskActionEnum taskActions[][] = {
                null,
                {TaskActionEnum.SCAN_BARCODE},
                {TaskActionEnum.CAMERA},
                null,
                {TaskActionEnum.SCAN_BARCODE},
                null,
                null,
                null
        };

        String taskActionName[] = {
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        };


        for (int i = 0; i < taskNames.length; i++) {
            JobTaskModel task = new JobTaskModel();
            task.setDefault();
            task.setName(taskNames[i]);
            task.setJobFlag(false);
            task.setOrderNdx(i + 1);
            task.setParent(job.getId());
            task.setJobPriority(job.getJobPriority());
            task.setType(job.getType());
            task.setAsset(job.getAsset());
            task.setState(JobStateEnum.SCHEDULE);
            task.setAssignedBy(job.getAssignedBy());
            task.setDeadLine(job.getDeadLine());
            task.setDuration(job.getDuration());
            task.setSite(job.getSite());
            task.setTicket(job.getTicket());

            String details[] = taskDetails[i];
            TaskDetailModelList taskList = new TaskDetailModelList();

            if (details.length > 0) {
                for (int j = 0; j < details.length; j++) {
                    TaskDetailModel detail = new TaskDetailModel();
                    detail.setDefault();
                    detail.setOrderNdx(j + 1);
                    detail.setDescription(details[j]);

                    taskList.add(detail);
                }
            }

            CommandFacade.jobTaskDetailUpdate(task, taskList, context);

            TaskActionEnum actions[] = taskActions[i];
            String actionName = taskActionName[i];

            if (actions != null) {
                for (int j = 0; j < actions.length; j++) {
                    TaskActionModel action = new TaskActionModel();
                    action.setDefault();
                    action.setAction(actions[j]);

                    if (actionName != null) {
                        action.setDescription(actionName);
                    }

                    TaskActionModelList taskActionModelList = new TaskActionModelList();
                    taskActionModelList.add(action);

                    CommandFacade.jobTaskActionUpdate(task, taskActionModelList, context);
                }
            }
        }
    }

    /**
     * @param context
     * @return
     */
    public List<JobTaskModel> loadJobTasks(Context context, List<SiteModel> sites) {
        List<JobTaskModel> jobs = new ArrayList<>();

        jobs.add(loadJobTask1(context, sites));
        jobs.add(loadJobTask2(context, sites));
        jobs.add(loadJobTask3(context, sites));
        jobs.add(loadJobTask4(context, sites));
        jobs.add(loadJobTask5(context, sites));

        return jobs;
    }

    public JobTaskModel loadJobTask1(Context context, List<SiteModel> jobSites) {
        JobTaskModel job1 = new JobTaskModel();
        job1.setDefault();
        job1.setName("Cat Engine Oil and Filter Change");
        job1.setDescription("Regular 500 hour Engine Oil and Engine Oil Filter Change");
        job1.setJobFlag(true);
        job1.setJobPriority(JobTaskPriorityEnum.NORMAL);
        job1.setType(JobTypeEnum.MAINTENANCE);
        job1.setAsset("CAT-C18 Industrial Engine");
        job1.setState(JobStateEnum.SCHEDULE);
        job1.setAssignedBy("Bogus Bob");
        job1.setDeadLine(new Date());
        job1.setDuration(60);
        job1.setTicket("ticket1");

        job1.setSite(getJobSiteId("Wellpad 34", jobSites));

        CommandFacade.jobTaskUpdate(job1, context);

        oilChangeTask(job1, context);

        return job1;
    }

    public JobTaskModel loadJobTask2(Context context, List<SiteModel> jobSites) {
        JobTaskModel job1 = new JobTaskModel();
        job1.setDefault();
        job1.setName("John Deere Engine Oil and Filter Change");
        job1.setDescription("Regular 500 hour Engine Oil and Engine Oil Filter Change");
        job1.setJobFlag(true);
        job1.setJobPriority(JobTaskPriorityEnum.NORMAL);
        job1.setType(JobTypeEnum.MAINTENANCE);
        job1.setAsset("Powertech 9.0l Industrial Engine");
        job1.setState(JobStateEnum.ACTIVE);
        job1.setAssignedBy("Bogus Bob");
        job1.setDeadLine(new Date());
        job1.setDuration(60);
        job1.setTicket("ticket2");

        job1.setSite(getJobSiteId("Wellpad 52", jobSites));

        CommandFacade.jobTaskUpdate(job1, context);

        oilChangeTask(job1, context);

        return job1;
    }

    private Long getJobSiteId(String siteName, List<SiteModel> jobSites) {
        SiteModel model = getJobSite(siteName, jobSites);

        return (model == null) ? null : model.getId();
    }

    private SiteModel getJobSite(String siteName, List<SiteModel> jobSites) {
        SiteModel siteModel = null;

        for (SiteModel model : jobSites) {
            if (model.getName().equals(siteName)) {
                siteModel = model;
                break;
            }
        }

        return siteModel;
    }

    private SiteModel getJobSiteById(Long siteId, List<SiteModel> jobSites) {
        SiteModel siteModel = null;

        for (SiteModel model : jobSites) {
            if (model.getId() == siteId) {
                siteModel = model;
                break;
            }
        }

        return siteModel;
    }

    /**
     * @param job
     * @param context
     */
    private void monitorPumpTask(JobTaskModel job, Context context) {

        String taskNames[] = {
                "Attach the magnetic acoustic sensor",
                "Monitor current db level of bearings",
                "Apply lubricant until optimal db level achieved",
                "Enter Decibel Level",
                "Record usage hours"
        };

        String taskDetails[][] = {
                {
                        "Attach the magnetic acoustic sensor of the Ultraprobe 201 to the bearing while the pump is in operation.",
                        "View the LED display on the Ultrasonic grease gun to ensure the sensor data is being received"

                },
                {
                        "Using headphone and LED display monitor current db level of bearings and input value into GOFACTORY app.",
                        "Compare to previous benchmark reading."
                },
                {
                        "Apply small amount of lubricant and monitor decible level for 2-3 minutes.",
                        "Compare to existing benchmark and repeat unti benchmark is attained.",
                        "Repeat process for all bearings of pump system."
                },
                {
                        "Visually inspect critical fittings and fasteners of pump systems.",
                        "Capture photos of each item inspected."
                },
                {
                        "Manually input usage hours",
                        "Take picture of usage gauge"
                }
        };

        TaskActionEnum taskActions[][] = {
                null,
                null,
                {TaskActionEnum.CAPTURE_INTEGER},
                {TaskActionEnum.CAMERA},
                {TaskActionEnum.CAPTURE_INTEGER, TaskActionEnum.CAMERA}
        };

        String taskActionNames[][] = {
                null,
                null,
                {"Enter Decibel Level"},
                {null},
                {"Enter usage hours from gauge", null}
        };

        for (int i = 0; i < taskNames.length; i++) {
            String taskName = taskNames[i];

            JobTaskModel task = new JobTaskModel();
            task.setDefault();
            task.setName(taskName);
            task.setJobFlag(false);
            task.setOrderNdx(1);
            task.setParent(job.getId());
            task.setJobPriority(job.getJobPriority());
            task.setType(job.getType());
            task.setAsset(job.getAsset());
            task.setState(job.getState());
            task.setAssignedBy(job.getAssignedBy());
            task.setDeadLine(job.getDeadLine());
            task.setDuration(job.getDuration());
            task.setSite(job.getSite());
            task.setTicket(job.getTicket());

            TaskDetailModelList taskDetailModelList = new TaskDetailModelList();

            String taskDetailNames[] = taskDetails[i];

            for (int j = 0; j < taskDetailNames.length; j++) {
                TaskDetailModel detail = new TaskDetailModel();
                detail.setDefault();
                detail.setOrderNdx(1);
                detail.setDescription(taskDetailNames[j]);

                taskDetailModelList.add(detail);
            }

            CommandFacade.jobTaskDetailUpdate(task, taskDetailModelList, context);

            TaskActionEnum actions[] = taskActions[i];
            String actionNames[] = taskActionNames[i];

            if (actions != null) {
                for (int j = 0; j < actions.length; j++) {
                    TaskActionModel action = new TaskActionModel();
                    action.setDefault();
                    action.setAction(actions[j]);

                    if (actionNames[j] != null) {
                        action.setDescription(actionNames[j]);
                    }

                    TaskActionModelList taskActionModelList = new TaskActionModelList();
                    taskActionModelList.add(action);

                    CommandFacade.jobTaskActionUpdate(task, taskActionModelList, context);
                }
            }
        }
    }

    public JobTaskModel loadJobTask3(Context context, List<SiteModel> jobSites) {
        JobTaskModel job1 = new JobTaskModel();
        job1.setDefault();
        job1.setName("Monitor pump operation and lubrication of bearings");
        job1.setDescription("Routine inspection and lubrication");
        job1.setJobFlag(true);
        job1.setJobPriority(JobTaskPriorityEnum.NORMAL);
        job1.setType(JobTypeEnum.MAINTENANCE);
        job1.setAsset("Cornell 8NHTH Industrial Pump");
        job1.setState(JobStateEnum.COMPLETE);
        job1.setAssignedBy("Bogus Bob");
        job1.setDeadLine(new Date());
        job1.setDuration(30);
        job1.setTicket("ticket3");

        job1.setSite(getJobSiteId("Wellpad 115", jobSites));

        CommandFacade.jobTaskUpdate(job1, context);

        monitorPumpTask(job1, context);

        return job1;
    }


    public JobTaskModel loadJobTask4(Context context, List<SiteModel> jobSites) {
        JobTaskModel job1 = new JobTaskModel();
        job1.setDefault();
        job1.setName("Monitor pump operation and lubrication of bearings");
        job1.setDescription("Routine inspection and lubrication");
        job1.setJobFlag(true);
        job1.setJobPriority(JobTaskPriorityEnum.NORMAL);
        job1.setType(JobTypeEnum.MAINTENANCE);
        job1.setAsset("Cornell 8NHTH Industrial Pump");
        job1.setState(JobStateEnum.SUSPEND);
        job1.setAssignedBy("Bogus Bob");
        job1.setDeadLine(new Date());
        job1.setDuration(30);
        job1.setTicket("ticket4");

        job1.setSite(getJobSiteId("Wellpad 147", jobSites));

        CommandFacade.jobTaskUpdate(job1, context);

        monitorPumpTask(job1, context);

        return job1;
    }

    public JobTaskModel loadJobTask5(Context context, List<SiteModel> jobSites) {
        JobTaskModel job1 = new JobTaskModel();
        job1.setDefault();
        job1.setName("Offline Gas Turbine Washing process and Inspection of weld joints and torque settings");
        job1.setDescription("Offline Gas Turbine Washing process and Inspection of weld joints and torque settings");
        job1.setJobFlag(true);
        job1.setJobPriority(JobTaskPriorityEnum.NORMAL);
        job1.setType(JobTypeEnum.MAINTENANCE);
        job1.setAsset("Siemens SGT-100 Power Gas Turbine");
        job1.setState(JobStateEnum.SCHEDULE);
        job1.setAssignedBy("Bogus Bob");
        job1.setDeadLine(new Date());
        job1.setDuration(120);
        job1.setTicket("ticket5");

        job1.setSite(getJobSiteId("Pipeline AA2", jobSites));

        CommandFacade.jobTaskUpdate(job1, context);

        JobTaskModel task1 = new JobTaskModel();
        task1.setDefault();
        task1.setName("Check temperature of turbine");
        task1.setJobFlag(false);
        task1.setOrderNdx(1);
        task1.setParent(job1.getId());
        task1.setJobPriority(job1.getJobPriority());
        task1.setType(job1.getType());
        task1.setAsset(job1.getAsset());
        task1.setState(JobStateEnum.SCHEDULE);
        task1.setAssignedBy(job1.getAssignedBy());
        task1.setDeadLine(job1.getDeadLine());
        task1.setDuration(job1.getDuration());
        task1.setSite(job1.getSite());
        task1.setTicket(job1.getTicket());

        TaskDetailModel detail1 = new TaskDetailModel();
        detail1.setDefault();
        detail1.setOrderNdx(1);
        detail1.setDescription("Use temperature sensor to check temperature of turbine.");

        TaskDetailModel detail2 = new TaskDetailModel();
        detail2.setDefault();
        detail2.setOrderNdx(2);
        detail2.setDescription("Input temperature reading.");

        TaskDetailModelList taskList1 = new TaskDetailModelList();
        taskList1.add(detail1);
        taskList1.add(detail2);

        CommandFacade.jobTaskDetailUpdate(task1, taskList1, context);

        JobTaskModel task2 = new JobTaskModel();
        task2.setDefault();
        task2.setName("Heat temperature of water");
        task2.setJobFlag(false);
        task2.setOrderNdx(2);
        task2.setParent(job1.getId());
        task2.setJobPriority(job1.getJobPriority());
        task2.setType(job1.getType());
        task2.setAsset(job1.getAsset());
        task2.setState(JobStateEnum.SCHEDULE);
        task2.setAssignedBy(job1.getAssignedBy());
        task2.setDeadLine(job1.getDeadLine());
        task2.setDuration(job1.getDuration());
        task2.setSite(job1.getSite());
        task2.setTicket(job1.getTicket());

        TaskDetailModel detail10 = new TaskDetailModel();
        detail10.setDefault();
        detail10.setOrderNdx(1);
        detail10.setDescription("Heat temperature of water to within 100 degrees of turbine tempertature.");

        TaskDetailModel detail11 = new TaskDetailModel();
        detail11.setDefault();
        detail11.setOrderNdx(2);
        detail11.setDescription("Capture photo of water temperature reading.");

        TaskDetailModelList taskList2 = new TaskDetailModelList();
        taskList2.add(detail10);
        taskList2.add(detail11);

        CommandFacade.jobTaskDetailUpdate(task2, taskList2, context);

        JobTaskModel task3 = new JobTaskModel();
        task3.setDefault();
        task3.setName("Inspect the inlent plenum and bell-mouth");
        task3.setJobFlag(false);
        task3.setOrderNdx(3);
        task3.setParent(job1.getId());
        task3.setJobPriority(job1.getJobPriority());
        task3.setType(job1.getType());
        task3.setAsset(job1.getAsset());
        task3.setState(JobStateEnum.SCHEDULE);
        task3.setAssignedBy(job1.getAssignedBy());
        task3.setDeadLine(job1.getDeadLine());
        task3.setDuration(job1.getDuration());
        task3.setSite(job1.getSite());
        task3.setTicket(job1.getTicket());

        TaskDetailModel detail20 = new TaskDetailModel();
        detail20.setDefault();
        detail20.setOrderNdx(1);
        detail20.setDescription("Inspect the inlet plenum and gas turbine bell-mouth for large accumulations of atmospheric contaminants which could be washed into the compressor.");

        TaskDetailModel detail21 = new TaskDetailModel();
        detail21.setDefault();
        detail21.setOrderNdx(2);
        detail21.setDescription("If these  deposits are present remove them by washing with a garden hose.");

        TaskDetailModelList taskList3 = new TaskDetailModelList();
        taskList3.add(detail20);
        taskList3.add(detail21);

        CommandFacade.jobTaskDetailUpdate(task3, taskList3, context);

        JobTaskModel task4 = new JobTaskModel();
        task4.setDefault();
        task4.setName("Prepare areas for washing and perform wash");
        task4.setJobFlag(false);
        task4.setOrderNdx(4);
        task4.setParent(job1.getId());
        task4.setJobPriority(job1.getJobPriority());
        task4.setType(job1.getType());
        task4.setAsset(job1.getAsset());
        task4.setState(JobStateEnum.SCHEDULE);
        task4.setAssignedBy(job1.getAssignedBy());
        task4.setDeadLine(job1.getDeadLine());
        task4.setDuration(job1.getDuration());
        task4.setSite(job1.getSite());
        task4.setTicket(job1.getTicket());

        TaskDetailModel detail30 = new TaskDetailModel();
        detail30.setDefault();
        detail30.setOrderNdx(1);
        detail30.setDescription("Prepare the atomizing air circuit, the cooling and sealing air circuit and the skid.");

        TaskDetailModel detail31 = new TaskDetailModel();
        detail31.setDefault();
        detail31.setOrderNdx(2);
        detail31.setDescription("Wash the turbine for 8-10 minutes.");

        TaskDetailModel detail32 = new TaskDetailModel();
        detail32.setDefault();
        detail32.setOrderNdx(3);
        detail32.setDescription("Switch to rinse mode and rinse for 4-5 minutes.");

        TaskDetailModelList taskList4 = new TaskDetailModelList();
        taskList4.add(detail30);
        taskList4.add(detail31);
        taskList4.add(detail32);

        CommandFacade.jobTaskDetailUpdate(task4, taskList4, context);

        JobTaskModel task5 = new JobTaskModel();
        task5.setDefault();
        task5.setName("Ready Turbine for operation");
        task5.setJobFlag(false);
        task5.setOrderNdx(5);
        task5.setParent(job1.getId());
        task5.setJobPriority(job1.getJobPriority());
        task5.setType(job1.getType());
        task5.setAsset(job1.getAsset());
        task5.setState(JobStateEnum.SCHEDULE);
        task5.setAssignedBy(job1.getAssignedBy());
        task5.setDeadLine(job1.getDeadLine());
        task5.setDuration(job1.getDuration());
        task5.setSite(job1.getSite());
        task5.setTicket(job1.getTicket());

        TaskDetailModel detail40 = new TaskDetailModel();
        detail40.setDefault();
        detail40.setOrderNdx(1);
        detail40.setDescription("Return the valves that have been operated before the compressor washing operation to their previous positions to allow the turbine to fire.");

        TaskDetailModel detail41 = new TaskDetailModel();
        detail41.setDefault();
        detail41.setOrderNdx(2);
        detail41.setDescription("Inspect Weld Joints and capture photos.");

        TaskDetailModel detail42 = new TaskDetailModel();
        detail42.setDefault();
        detail42.setOrderNdx(3);
        detail42.setDescription("Inspect Torque settings.");

        TaskDetailModelList taskList5 = new TaskDetailModelList();
        taskList5.add(detail40);
        taskList5.add(detail41);
        taskList5.add(detail42);

        CommandFacade.jobTaskDetailUpdate(task5, taskList5, context);

        TaskActionModel action50 = new TaskActionModel();
        action50.setDefault();
        action50.setAction(TaskActionEnum.CAMERA);

        TaskActionModelList actionList5 = new TaskActionModelList();
        actionList5.add(action50);

        CommandFacade.jobTaskActionUpdate(task5, actionList5, context);

        return job1;
    }

    public void loadChats(Context context, List<PersonModel> personModels) {
        String participants[] = {"", "Technicians"};
        ChatTypeEnum types[] = {ChatTypeEnum.oneOnOne, ChatTypeEnum.group};
        String messages[] = {"Yes", "All good!"};

        String messageTimes[] = {
                "2015-10-05 18:14",
                "2015-10-03 11:54"
        };

        // use user "donal":
        PersonModel model = null;

        for (PersonModel personModel : personModels) {
            if (personModel.getNameUser().equals("donal")) {
                model = personModel;
                break;
            }
        }

        List<ChatModel> models = new ArrayList<>();

        for (int i = 0; i < participants.length; i++) {
            ChatModel chatModel = new ChatModel();
            chatModel.setDefault();

            if (i == 0) {
                chatModel.setPersonId(model.getId());
            } else {
                chatModel.setParticipant(participants[i]);
            }

            chatModel.setType(types[i]);
            chatModel.setLastMessage(messages[i]);
            chatModel.setLastMessageTime(getAsDate(messageTimes[i]));
            models.add(chatModel);

            ContextList list = new ContextList();
            addToChatContextList(chatModel, list, context);
            CommandFactory.execute(list);
        }

        loadChatMessages(context, models);
    }

    private void addToChatContextList(ChatModel model, ContextList list, Context context) {
        ChatUpdateCtx ctx
                = (ChatUpdateCtx) ContextFactory.factory(CommandEnum.CHAT_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }

    public void loadChatMessages(Context context, List<ChatModel> chatModels) {
        String messages[][] = {
                {
                        "I need help!",
                        "What seems to be the problem?",
                        "It seems to be broken",
                        "Did you reset the station?",
                        "Yes"},
                {
                        "Hello",
                        "How are you?",
                        "All good!"}

        };

        String messageTimes[] = {
                "2015-08-25 08:14",
                "2015-09-02 11:02",
                "2015-09-05 08:04",
                "2015-09-05 09:14",
                "2015-09-10 16:04",
                "2015-09-15 03:10",
                "2015-10-15 04:18",
                "2015-10-03 01:44"
        };

        for (int i = 0; i < chatModels.size(); i++) {
            ChatModel chatModel = chatModels.get(i);

            for (int j = 0; j < messages[i].length; j++) {
                ChatMessageModel model = new ChatMessageModel();
                model.setDefault();

                model.setMessage(messages[i][j]);
                model.setMessageTime(getAsDate(messageTimes[j]));

                ChatMessageTypeEnum type = (j % 2 == 0)
                        ? ChatMessageTypeEnum.outbound : ChatMessageTypeEnum.inbound;

                model.setType(type);
                model.setChatId(chatModel.getId());

                ContextList list = new ContextList();
                addToChatMessageContextList(model, list, context);
                CommandFactory.execute(list);
            }
        }

    }

    // convenience utility
    private Date getAsDate(String value) {
        Date date = null;

        try {
            date = DATE_FORMATTER.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private void addToChatMessageContextList(ChatMessageModel model, ContextList list, Context context) {
        ChatMessageUpdateCtx ctx
                = (ChatMessageUpdateCtx) ContextFactory.factory(CommandEnum.CHAT_MSG_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }

    public List<PartModel> loadParts(Context context, List<SiteModel> sites) {

        InventoryCategoryEnum[] categories = {
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.tools,
                InventoryCategoryEnum.parts,
                InventoryCategoryEnum.parts,
                InventoryCategoryEnum.parts,
                InventoryCategoryEnum.parts,
                InventoryCategoryEnum.parts
        };

        String[][] items = {
                //tools
                {"Chain Wrench", "1U-8760"},
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

        Long siteId = sites.get(sites.size() - 1).getId();

        for (int i = 0; i < items.length; i++) {
            PartModel model = new PartModel();
            model.setDefault();

            String name = items[i][0];

            model.setName(name);

            InventoryStatusEnum itemStatus = (pickupParts.contains(name)) ?
                    InventoryStatusEnum.pickUp : InventoryStatusEnum.inCustody;

            model.setStatus(itemStatus);

            model.setManufacturer(manufacturers[i]);
            model.setModelNumber(items[i][1]);
            model.setSerial(serialNumbers[i]);
            model.setBarcode(barcodes[i]);
            model.setOwner("John Smith");
            model.setPickedUpDate(new Date());
            model.setCondition("Good");
            model.setCategory(categories[i]);

            model.setBleAddress(bleAddresses[i]);

            siteId = (itemStatus.equals(InventoryStatusEnum.pickUp))
                    ? getJobSiteId("Equipment Shelter1", sites) : siteId;

            model.setSiteId(siteId);

            SiteModel siteModel = getJobSiteById(siteId, sites);

            model.setLocation(siteModel.getLocation());

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

    public void loadChainOfCustodyItems(Context context, List<PartModel> parts,
                                        List<SiteModel> jobSites, PersonModel personModel) {

        String siteNames[] = {
                "Equipment Shelter1",
                "Wellpad 34"
        };

        for (int i = 0; i < parts.size(); i++) {
            PartModel partModel = parts.get(i);

            ChainOfCustodyModel model;
            ContextList list = new ContextList();

            String name;
            String siteName;

            if (partModel.getStatus().equals(InventoryStatusEnum.pickUp)) {
                siteName = siteNames[0];

                name = siteName;
            } else {
                name = personModel.getName();

                siteName = siteNames[1];
            }

            SiteModel siteModel = getJobSite(siteName, jobSites);

            model = getCOCModel(partModel.getId(), name, siteModel);

            addToCOCContextList(model, list, context);

            CommandFactory.execute(list);
        }
    }

    private ChainOfCustodyModel getCOCModel(Long partId, String name, SiteModel siteModel) {
        ChainOfCustodyModel model = new ChainOfCustodyModel();
        model.setDefault();

        model.setPartId(partId);
        model.setCustodian(name);
        model.setPickupDate(new Date());

        model.setPickupSiteId(siteModel.getId());

        model.setLocation(siteModel.getLocation());

        return model;
    }

    private void addToCOCContextList(ChainOfCustodyModel model, ContextList list, Context context) {
        ChainOfCustodyUpdateCtx ctx
                = (ChainOfCustodyUpdateCtx) ContextFactory.factory(CommandEnum.CHAIN_OF_CUSTODY_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }

    public void loadPartsForPerson(Context context, List<PartModel> parts, PersonModel personModel) {
        // assign all except excluded parts to user "Allan"

        Set<String> excludeParts = new HashSet<>(Arrays.asList(
                new String[]{"Ultrasonic grease gun", "Torque Wrench Kit", "Bearing Lubricant"}
        ));

        for (int i = 0; i < parts.size(); i++) {
            PartModel partModel = parts.get(i);

            if (!excludeParts.contains(partModel.getName())) {
                PersonPartModel model = new PersonPartModel();
                model.setDefault();

                model.setPartId(partModel.getId());
                model.setPersonId(personModel.getId());

                ContextList list = new ContextList();

                addToPersonPartContextList(model, list, context);

                CommandFactory.execute(list);
            }
        }
    }

    private void addToJobPartContextList(JobPartModel model, ContextList list, Context context) {
        JobPartUpdateCtx ctx
                = (JobPartUpdateCtx) ContextFactory.factory(CommandEnum.JOB_PART_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }

    /**
     * @param context
     * @param jobTasks
     * @param parts
     */
    public void loadPartsForJobs(Context context, List<JobTaskModel> jobTasks, List<PartModel> parts) {
        Log.i("DatabaseScenario", "load parts for jobs");

        String jobNames[] = {
                "CAT Engine Oil and Filter Change",
                "John Deere Engine Oil and Filter Change",
                "Monitor pump operation and lubrication of bearings",
                "Offline Gas Turbine Wash and Inspection"
        };

        String partSets[][] = {
                {
                        "Oil Filter - CAT",
                        "Engine Oil - CAT",
                        "Chain Wrench",
                        "Oil Filter Cutter GP",
                        "Magnetic testing rod",
                        "Used Oil Container",
                        "Diesel Engine Servicing Kit"
                },
                {
                        "Oil Filter - JD",
                        "Engine Oil - JD",
                        "Chain Wrench",
                        "Oil Filter Cutter GP",
                        "Magnetic testing rod",
                        "Used Oil Container",
                        "Diesel Engine Servicing Kit"
                },
                {
                        "Bearing Lubricant",
                        "Ultrasonic grease gun",
                        "Torque Wrench Kit"
                },
                {
                        "Turbine Water Wash system",
                        "Temperature sensor",
                        "Torque Wrench Kit"
                }
        };

        for (JobTaskModel jobTask : jobTasks) {
            int index = 0;

            for (int i = 0; i < jobNames.length; i++) {
                if (jobTask.getName().equals(jobNames[i])) {
                    index = i;
                    break;
                }
            }

            String partNames[] = partSets[index];

            for (String partName : partNames) {
                PartModel partModel = getPartModelForName(partName, parts);

                JobPartModel model = new JobPartModel();
                model.setDefault();

                model.setJobtaskId(jobTask.getId());
                model.setPartId(partModel.getId());

                ContextList list = new ContextList();

                addToJobPartContextList(model, list, context);

                CommandFactory.execute(list);
            }
        }
    }

    private PartModel getPartModelForName(String name, List<PartModel> partModels) {
        PartModel match = null;

        for (PartModel model : partModels) {
            if (model.getName().equals(name)) {
                match = model;
                break;
            }
        }

        return match;
    }

    private void addToPersonPartContextList(PersonPartModel model, ContextList list, Context context) {
        PersonPartUpdateCtx ctx
                = (PersonPartUpdateCtx) ContextFactory.factory(CommandEnum.PERSON_PART_UPDATE, context);

        ctx.setModel(model);
        list.add(ctx);
    }
}