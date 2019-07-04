package com.example.android.androidskeletonapp.data.service;

import com.example.android.androidskeletonapp.data.Sdk;

import org.hisp.dhis.android.core.common.State;
import org.hisp.dhis.android.core.d2manager.D2Manager;

public class SyncStatusHelper {

    public static int programCount() {
        return Sdk.d2().programModule().programs.count();
    }

    public static int dataSetCount() {
        return Sdk.d2().dataSetModule().dataSets.count();
    }

    public static int trackedEntityInstanceCount() {
        return Sdk.d2().trackedEntityModule().trackedEntityInstances.byState().neq(State.RELATIONSHIP).count();
    }

    public static int singleEventCount() {
        return Sdk.d2().eventModule().events.byEnrollmentUid().isNull().count();
    }

    public static int dataValueCount() {
        return Sdk.d2().dataValueModule().dataValues.count();
    }

    public static boolean isThereDataToUpload() {
        // TODO Logic to know if there is data to upload
        return D2Manager.getD2().trackedEntityModule().trackedEntityInstances
                .byState().eq(State.TO_POST)
                .get().size() > 0 || D2Manager.getD2().trackedEntityModule().trackedEntityInstances
                .byState().eq(State.TO_UPDATE)
                .get().size() > 0 || D2Manager.getD2().trackedEntityModule().trackedEntityInstances
                .byState().eq(State.TO_DELETE)
                .get().size() > 0;

    }

}
