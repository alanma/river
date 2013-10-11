package com.lolRiver.river.models;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * @author mxia (mxia@lolRiver.com)
 *         10/1/13
 */

public class Elo {
    public enum Name {
        BRONZE1, BRONZE2, BRONZE3, BRONZE4, BRONZE5,
        SILVER1, SILVER2, SILVER3, SILVER4, SILVER5,
        GOLD1, GOLD2, GOLD3, GOLD4, GOLD5,
        PLATINUM1, PLATINUM2, PLATINUM3, PLATINUM4, PLATINUM5,
        DIAMOND1, DIAMOND2, DIAMOND3, DIAMOND4, DIAMOND5,
        CHALLENGER
    }

    public static final String ID_STRING = "id";
    public static final String NAME_STRING = "elo";
    public static final String TIME_STRING = "time";
    public static final String USER_ID_STRING = "user_id";

    private int id;
    private Name name;
    private Timestamp time;
    private int userId;

    public Elo() {

    }

    public Elo(Name name) {
        this.name = name;
    }

    public static Elo fromString(String eloName) {
        if (StringUtils.isBlank(eloName)) {
            return null;
        }
        return new Elo(Elo.Name.valueOf(eloName));
    }

    public List<String> invalidFields() {
        List<String> list = new ArrayList<String>();
        if (name == null || StringUtils.isBlank(name.name())) {
            list.add(NAME_STRING);
        }
        if (time == null) {
            list.add(TIME_STRING);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getUserId() {
        return userId;
    }

    public Elo setName(Name name) {
        this.name = name;
        return this;
    }

    public Elo setId(int id) {
        this.id = id;
        return this;
    }

    public Elo setTime(Timestamp time) {
        this.time = time;
        return this;
    }

    public Elo setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "Elo{" +
               "id=" + id +
               ", name=" + name +
               ", time=" + time +
               ", userId=" + userId +
               '}';
    }
}
