package org.telegrambot.convertio.core.services;

import org.telegrambot.convertio.exceptions.services.ServiceException;

import java.util.List;

interface Service<Entity, Key> {
    Entity create(Entity entity) throws ServiceException;

    Entity update(Entity entity);

    Entity read(Key id) throws ServiceException;

    void delete(Key id);

    List<Entity> getAll();

    long count();
}
