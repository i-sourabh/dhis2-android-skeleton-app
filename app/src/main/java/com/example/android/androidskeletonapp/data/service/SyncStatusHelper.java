package com.example.android.androidskeletonapp.data.service;

import org.hisp.dhis.android.core.d2manager.D2Manager;

public class SyncStatusHelper {

    // TODO implement count methods

    public static int programCount() {
        return D2Manager.getD2().programModule().programs.count();
    }

    public static int dataSetCount() {
        return D2Manager.getD2().dataSetModule().dataSets.count();
    }

    public static int trackedEntityInstanceCount() {
        return D2Manager.getD2().trackedEntityModule().trackedEntityInstances.count();
    }

    public static int singleEventCount() {
        return D2Manager.getD2().eventModule().events.count();
    }

    public static int dataValueCount() {
        return D2Manager.getD2().dataValueModule().dataValues.count();
    }
}
