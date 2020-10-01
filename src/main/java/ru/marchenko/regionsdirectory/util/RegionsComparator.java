package ru.marchenko.regionsdirectory.util;

import lombok.RequiredArgsConstructor;
import ru.marchenko.regionsdirectory.model.Region;

import java.util.Comparator;

/**
 * @author Created by Vladislav Marchenko on 01.10.2020
 */
@RequiredArgsConstructor
public class RegionsComparator implements Comparator<Region> {
    private final SortSetting sortSetting;

    @Override
    public int compare(Region o1, Region o2) {
        return sortSetting.getSortedField().equals(SortedField.NAME)
                ? sortSetting.getSortOrder().equals(SortOrder.ASC)
                    ? o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase())
                    : o2.getName().toUpperCase().compareTo(o1.getName().toUpperCase())
                : sortSetting.getSortOrder().equals(SortOrder.ASC)
                    ? o1.getAbbreviatedName().toUpperCase().compareTo(o2.getAbbreviatedName().toUpperCase())
                    : o2.getAbbreviatedName().toUpperCase().compareTo(o1.getAbbreviatedName().toUpperCase());
    }
}
