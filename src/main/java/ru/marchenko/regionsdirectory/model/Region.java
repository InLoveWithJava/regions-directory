package ru.marchenko.regionsdirectory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region implements Serializable {

    private Long id = 0L;

    private String name;

    private String abbreviatedName;

    public Region(String name, String abbreviatedName) {
        this.name = name;
        this.abbreviatedName = abbreviatedName;
    }

}
