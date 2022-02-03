package org.telegrambot.convertio.facades.facades;

import org.telegrambot.convertio.exceptions.facades.FacadeDeleteException;
import org.telegrambot.convertio.exceptions.facades.FacadeException;

import java.util.List;

interface Facade<Entity, Key> {
    Entity create(Entity dataObject) throws FacadeException;

    Entity update(Entity dataObject) throws FacadeException;

    Entity read(Key id) throws FacadeException;

    void delete(Key id) throws FacadeDeleteException;

    List<Entity> getAll();

    long count();
}
