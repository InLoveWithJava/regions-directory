package ru.marchenko.regionsdirectory.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author Created by Vladislav Marchenko on 01.10.2020
 */
@Data
@AllArgsConstructor
public class SortSetting implements Serializable {
    private SortedField sortedField;
    private SortOrder sortOrder;
}
