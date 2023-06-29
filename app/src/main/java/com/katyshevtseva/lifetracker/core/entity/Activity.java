package com.katyshevtseva.lifetracker.core.entity;

import com.katyshevtseva.lifetracker.db.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity implements Entity {
    private long id;
    private String title;
}
