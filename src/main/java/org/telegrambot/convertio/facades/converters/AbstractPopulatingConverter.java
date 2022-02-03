package org.telegrambot.convertio.facades.converters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.telegrambot.convertio.facades.populators.Populator;
import org.telegrambot.convertio.facades.populators.PopulatorList;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Populating converter that uses a list of configured populators to populate the target during conversion. Class used
 * to be but is no longer abstract. It allows to declare it as an abstract bean in Spring, otherwise we'd get
 * BeanInstantiationException.
 */
@Slf4j
public class AbstractPopulatingConverter<SOURCE, TARGET> extends AbstractConverter<SOURCE, TARGET> implements
        PopulatorList<SOURCE, TARGET> {

    private List<Populator<SOURCE, TARGET>> populators;

    @Override
    public List<Populator<SOURCE, TARGET>> getPopulators() {
        return populators;
    }

    @Override
    public void setPopulators(final List<Populator<SOURCE, TARGET>> populators) {
        this.populators = populators;
    }

    /**
     * Populate the target instance from the source instance. Calls a list of injected populators to populate the
     * instance.
     *
     * @param source the source item
     * @param target the target item to populate
     */
    @Override
    public void populate(final SOURCE source, final TARGET target) {
        final List<Populator<SOURCE, TARGET>> list = getPopulators();

        if (list == null) {
            return;
        }

        for (final Populator<SOURCE, TARGET> populator : list) {
            if (populator != null) {
                populator.populate(source, target);
            }
        }
    }

    // execute when BEAN name is known
    @PostConstruct
    public void removePopulatorsDuplicates() {
        // CHECK for populators duplicates
        if (CollectionUtils.isNotEmpty(populators)) {
            final LinkedHashSet<Populator<SOURCE, TARGET>> distinctPopulators = new LinkedHashSet<>();

            for (final Populator<SOURCE, TARGET> populator : populators) {
                if (!distinctPopulators.add(populator)) {
                    log.warn("Duplicate populator entry [" + populator.getClass().getName() + "] found for converter "
                            + getMyBeanName() + "! The duplication has been removed.");
                }
            }
            this.populators = new ArrayList<>(distinctPopulators);
        } else {
            log.warn("Empty populators list found for converter " + getMyBeanName() + "!");
        }
    }
}
