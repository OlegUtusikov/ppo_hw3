package com.utusikov.hw3.controller;

import com.utusikov.hw3.ActionRepository;
import com.utusikov.hw3.model.Action;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class TurnstileController extends Base {
    public TurnstileController(ActionRepository actionRepository) {
        super(actionRepository);
    }
    private boolean checkReg(Long accId) {
        return actionRepository.existsActionByAccountIdAndType(accId, Action.Type.Register);
    }
    @PostMapping("/turnstile/enter/{accId}")
    public Boolean doEnter(@PathVariable Long accId) {
        if (!checkReg(accId)) {
            return false;
        }

        if (isVisitNow(accId)) {
            return false;
        }

        if (getAccActualVisits(accId) == 0L) {
            return false;
        }

        actionRepository.save(new Action(accId, Action.Type.Enter));
        return true;
    }

    @PostMapping("/turnstile/leave/{accId}")
    public Boolean doLeave(@PathVariable Long accId) {
        if (!checkReg(accId)) {
            return false;
        }
        if (isVisitNow(accId)) {
            actionRepository.save(new Action(accId, Action.Type.Leave));
            return true;
        }
        return false;
    }

}
