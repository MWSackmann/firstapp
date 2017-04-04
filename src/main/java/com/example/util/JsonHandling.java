/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.util;


import com.example.model.AbstractModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class JsonHandling {//NOSONAR

    // Sonar false positive 'Add private constructor to hide the implicit public one'

    public static Object getFromNestedMaps(Map<String, Object> aggregate, String path) {
        StringTokenizer st = new StringTokenizer(path, "/");
        Object map = aggregate;
        while (st.hasMoreTokens()) {
            if (map == null)
                return null;
            if (map instanceof AbstractModel) {
                map = ((AbstractModel) map).getProperties();
            }
            if (map instanceof List) {
                map = ((List) map).get(Integer.valueOf(st.nextToken()));
            } else {
                map = ((Map<String, Object>) map).get(st.nextToken());
            }
        }
        return map;
    }

    public static String getAsString(Map<String, Object> aggregate, String path) {
        return (String) getFromNestedMaps(aggregate, path);
    }

    public static Map<String, Object> getAsMap(Map<String, Object> aggregate, String path) {

        Object result = getFromNestedMaps(aggregate, path);

        if (result == null)
            return null;

        return result instanceof AbstractModel ?
                ((AbstractModel) result).getProperties() :
                (Map<String, Object>) result;
    }

    public static Map<String, Object> getNodeOrCreateUnderneath(Map<String, Object> parent, String key) {
        parent.putIfAbsent(key, new HashMap<>());
        return getAsMap(parent, key);
    }
}
