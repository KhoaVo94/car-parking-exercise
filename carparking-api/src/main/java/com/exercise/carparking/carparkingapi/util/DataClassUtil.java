package com.exercise.carparking.carparkingapi.util;

import io.micrometer.common.lang.Nullable;
import jakarta.annotation.Nonnull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataClassUtil {
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        if (obj1.getClass() != obj2.getClass()) {
            return false;
        }
        List<Field> allFields = getFieldsUpTo(obj1.getClass(), Object.class);

        return allFields.stream()
                .map(fld -> Objects.equals(getFieldObj(obj1, fld), getFieldObj(obj2, fld)))
                .reduce(true, Boolean::logicalAnd);

    }

    public static int hashCode(Object obj) {
        return (obj == null) ? Objects.hashCode(null) :
                Objects.hash(getFieldsUpTo(obj.getClass(), Object.class).stream().map(fld -> getFieldObj(obj, fld)).toArray());
    }

    private static String toString(Object obj, Function<Field, String> fieldStringFunction, String enclosingFormat) {
        if (obj == null) {
            return "null";
        }

        List<Field> allFields = getFieldsUpTo(obj.getClass(), Object.class);
        List<String> fieldStrings = allFields.stream().map(fieldStringFunction).collect(Collectors.toList());
        return String.format(enclosingFormat, String.join(", ", fieldStrings));
    }

    private static Object getFieldObj(Object obj, Field field) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new AssertionError(String.format("Set field %s accessible, but still failed to access it.", field.getName()), e);
        }
    }

    private static List<Field> getFieldsUpTo(@Nonnull Class<?> startClass, @Nullable Class<?> exclusiveParent) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = startClass;

        while (currentClass != null && !(currentClass.equals(exclusiveParent))) {
            List<Field> superFields = Arrays.stream(currentClass.getDeclaredFields())
                    .filter(field -> !field.isSynthetic())  // Jacoco adds synthetic fields, see https://www.jacoco.org/jacoco/trunk/doc/faq.html
                    .collect(Collectors.toList());
            superFields.addAll(fields);
            fields = superFields;
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }

}
