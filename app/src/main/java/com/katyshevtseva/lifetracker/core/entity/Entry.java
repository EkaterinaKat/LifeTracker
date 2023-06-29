package com.katyshevtseva.lifetracker.core.entity;

import com.katyshevtseva.lifetracker.db.Entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entry implements Entity {
    private long id;
    private Date begin;
    private long activityId;
}
