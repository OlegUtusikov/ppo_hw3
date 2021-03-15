package com.utusikov.hw3.controller;

import com.utusikov.hw3.ActionRepository;
import com.utusikov.hw3.model.Action;

import java.util.List;

public class Base {
    protected final ActionRepository actionRepository;

    protected Base(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    protected Long getAccDoneVisits(Long accId) {
        List<Action> leafVisits = actionRepository.findAllByAccountIdAndType(accId, Action.Type.Leave);
        return leafVisits == null ? 0L : leafVisits.size();
    }

    protected Long getAccActualVisits(Long accId) {
        long allVisits = 0;
        List<Action> addActions = actionRepository.findAllByAccountIdAndType(accId, Action.Type.AddVisits);
        if (addActions != null) {
            for (Action action : addActions) {
                allVisits += Long.parseLong(action.getData(), 10);
            }
        }
        return allVisits - getAccDoneVisits(accId);
    }

    protected Boolean isVisitNow(Long accId) {
        List<Action> enterVisits = actionRepository.findAllByAccountIdAndType(accId, Action.Type.Enter);
        return enterVisits != null && enterVisits.size() == getAccDoneVisits(accId) + 1;
    }
}
