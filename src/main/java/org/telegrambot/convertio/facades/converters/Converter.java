package org.telegrambot.convertio.facades.converters;

import org.slf4j.Logger;
import org.springframework.core.convert.ConversionException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface Converter<SOURCE, TARGET> extends org.springframework.core.convert.converter.Converter<SOURCE, TARGET> {
    TARGET convert(SOURCE var1) throws ConversionException;

    TARGET convert(SOURCE var1, TARGET var2) throws ConversionException;

    default List<TARGET> convertAll(Collection<? extends SOURCE> sources) throws ConversionException {
        if (CollectionUtils.isEmpty(sources)) {
            return Collections.emptyList();
        } else {
            List<TARGET> result = new ArrayList<>(sources.size());

            for (SOURCE source : sources) {
                result.add(this.convert(source));
            }

            return result;
        }
    }

    default List<TARGET> convertAllIgnoreExceptions(Collection<? extends SOURCE> sources) {
        if (CollectionUtils.isEmpty(sources)) {
            return Collections.emptyList();
        } else {
            List<TARGET> targets = new ArrayList<>(sources.size());

            for (SOURCE value : sources) {
                try {
                    targets.add(this.convert(value));
                } catch (ConversionException var6) {
                    this.getLogger().warn("Exception while converting object!", var6);
                }
            }

            return targets;
        }
    }

    default Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger(this.getClass());
    }
}
