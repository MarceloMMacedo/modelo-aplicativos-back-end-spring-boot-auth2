package br.com.areadigital.areadigital.commons;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class with static methods for common tasks.
 */
public class Util {

    /**
     * Get the names of null properties of a given object.
     *
     * @param source the object to inspect
     * @return an array with the names of null properties
     */
    public static String[] getNullProperties(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        List<String> getNullProperties = Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> Objects.isNull(wrappedSource.getPropertyValue(propertyName)))
                .collect(Collectors.toList());
        String[] result = new String[getNullProperties.size()];
        getNullProperties.toArray(result);
        return result;
    }
}
