package com.utusikov.hw3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Action implements Comparable<Action> {

    public enum Type {
        Enter, Leave, Register, AddVisits, None
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    private Type type;
    private String data;
    private long tmsp;

    @Override
    public int compareTo(Action other) {
        if (id.equals(other.id)) {
            return 0;
        }
        return id < other.id ? -1 : 1;
    }

    private void initFields(Long accountId, Type type, String data) {
        this.accountId = accountId;
        this.type = type;
        this.data = data;
        this.tmsp = System.currentTimeMillis();
    }

    public String toString() {
        return "Action {accId: " + accountId + ", type: " + type + ", data: '" + data + "'}";
    }

    protected Action() {
        initFields(0L, Type.None, "");
    }

    public Action(Long accountId, Type type, String data) {
        initFields(accountId, type, data);
    }

    public Action(Long accountId, Type type) {
        initFields(accountId, type, "");
    }

    public Type getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public long getTmsp() {
        return tmsp;
    }
}
