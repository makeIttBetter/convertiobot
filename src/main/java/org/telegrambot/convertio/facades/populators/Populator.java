package org.telegrambot.convertio.facades.populators;


import org.springframework.core.convert.ConversionException;

/**
 * Interface for a populator.
 * A populator sets values in a target instance based on values in the source instance.
 * Populators are similar to converters except that unlike converters the target instance must already exist.
 *
 * @param <SOURCE> the type of the source object
 * @param <TARGET> the type of the destination object
 */
public interface Populator<SOURCE, TARGET> {
    /**
     * Populate the target instance with values from the source instance.
     *
     * @param source the source object
     * @param target the target to fill
     * @throws ConversionException if an error occurs
     */
    void populate(SOURCE source, TARGET target) throws ConversionException;
}