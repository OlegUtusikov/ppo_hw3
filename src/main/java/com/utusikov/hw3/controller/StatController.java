package com.utusikov.hw3.controller;

import com.utusikov.hw3.ActionRepository;
import com.utusikov.hw3.model.Action;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class StatController extends Base {
    private final Set<Action> actions;
    private Long curId;
    private final static String REPORT_BORDER = "<==================================================>\n";
    private final static long dayMillis = Duration.ofDays(1).toMillis();

    protected StatController(ActionRepository actionRepository) {
        super(actionRepository);
        actions = new TreeSet<>();
        curId = 0L;
    }

    private void updateActions() {
        List<Action> newActions = actionRepository.findAllByIdAfter(curId);
        if(newActions != null) {
            curId += newActions.size();
            actions.addAll(newActions.stream()
                    .filter(action -> action.getType() == Action.Type.Enter || action.getType() == Action.Type.Leave)
                    .collect(Collectors.toSet()));
        }
    }

    private Map<Long, List<Action>> groupByTmsp() {
        Map<Long, List<Action>> groups = new TreeMap<>();
        for (Action action : actions) {
            long key = action.getTmsp() / dayMillis;
            List<Action> curGroup = groups.containsKey(key) ? groups.get(key) : new ArrayList<>();
            curGroup.add(action);
            groups.put(key, curGroup);
        }
        return groups;
    }

    @GetMapping("/stat/perDays")
    public String doStatPerDay() {
        updateActions();
        StringBuilder reportBuilder = new StringBuilder(REPORT_BORDER);
        reportBuilder.append("Stat for per day:\n");
        for (Map.Entry<Long, List<Action>> entry : groupByTmsp().entrySet()) {
            reportBuilder.append("day [").append(entry.getKey()).append("]\n");
            for (Action action : entry.getValue()) {
                reportBuilder.append(action.toString()).append("\n");
            }
        }
        reportBuilder.append(REPORT_BORDER);
        return reportBuilder.toString();
    }

    private Double getAvgDurationOfVisit() {
        long cnt = 0;
        long sum = 0;
        long left  = 0;
        for (Action action : actions) {
            if (action.getType() == Action.Type.Enter) {
                left = action.getTmsp();
            } else if (action.getType() == Action.Type.Leave) {
                sum += action.getTmsp() - left;
                ++cnt;
            }
        }
        return 1.0 * sum / (cnt == 0 ? 1 : cnt);
    }

    @GetMapping("/stat/avg")
    public String doAvgStat() {
        updateActions();
        return REPORT_BORDER + "Avg statistics\n" +
                "Avg visits per day: " + 1.0 * actions.stream()
                .filter(action -> action.getType() == Action.Type.Leave)
                .toArray().length / (groupByTmsp().isEmpty() ? 1 : groupByTmsp().size() )+
                "\n" +
                "Avg visit duration: " + getAvgDurationOfVisit() + "ms\n" +
                REPORT_BORDER;
    }
}
