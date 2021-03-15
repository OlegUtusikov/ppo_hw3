package com.utusikov.hw3;

import com.utusikov.hw3.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {
    Boolean existsActionByAccountIdAndType(Long accId, Action.Type type);
    List<Action> findAllByAccountIdAndType(Long accId, Action.Type type);
    List<Action> findAllByIdAfter(Long id);
}
