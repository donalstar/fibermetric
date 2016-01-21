package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.TurnByTurnDirectionsModel;


/**
 * Created by donal on 9/22/15.
 */
public class TurnByTurnDirectionsCtx
        extends AbstractCmdCtx {

    private TurnByTurnDirectionsModel _instructionSet;
    private Double[] _jobSiteLocation;

    public TurnByTurnDirectionsCtx(Context androidContext) {
        super(CommandEnum.TURN_BY_TURN_DIRECTIONS, androidContext);
    }

    public void setJobSiteLocation(Double[] location) {
        _jobSiteLocation = location;
    }

    public Double[] getJobSiteLocation() {
        return _jobSiteLocation;
    }

    public void setInstructionSet(TurnByTurnDirectionsModel instructionSet) {
        _instructionSet = instructionSet;
    }

    public TurnByTurnDirectionsModel getInstructionSet() {
        return _instructionSet;
    }
}