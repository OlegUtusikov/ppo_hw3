package com.utusikov.hw3.controller;

import com.utusikov.hw3.AccountRepository;
import com.utusikov.hw3.ActionRepository;
import com.utusikov.hw3.model.Account;
import com.utusikov.hw3.model.Action;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class AdminController extends Base {
    private final AccountRepository accountRepository;

    public AdminController(ActionRepository actionRepository, AccountRepository accountRepository) {
        super(actionRepository);
        this.accountRepository = accountRepository;
    }

    // commands
    @PostMapping("/admin/acc/create")
    public void doCreateAcc() {
        Account acc = new Account();
        accountRepository.save(acc);
        actionRepository.save(new Action(acc.getId(), Action.Type.Register));
    }

    @PostMapping("/admin/acc/addVisits/{accId}")
    public void doAddVisits(@PathVariable Long accId, @RequestParam Long cnt) {
        actionRepository.save(new Action(accId, Action.Type.AddVisits, cnt.toString()));
    }

    // queries
    @GetMapping("/admin/acc/check/{accId}")
    public  Boolean doCheckAcc(@PathVariable Long accId) {
        return actionRepository.existsActionByAccountIdAndType(accId, Action.Type.Register);
    }

    @GetMapping("admin/acc/doneVisits/{accId}")
    public Long doGetAccDoneVisits(@PathVariable Long accId) {
        return getAccDoneVisits(accId);
    }

    @GetMapping("/admin/acc/actualVisits/{accId}")
    public Long doGetAccActualVisits(@PathVariable Long accId) {
        return getAccActualVisits(accId);
    }
}

