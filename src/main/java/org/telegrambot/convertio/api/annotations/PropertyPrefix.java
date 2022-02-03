package org.telegrambot.convertio.api.annotations;

import java.lang.annotation.*;


/**
 * Api generates an errors sometimes,
 * in the result of invalid parameters in requests.
 * It returns "message" that has some specific name,
 * but this name did not unique between different requests,
 * so in properties, due to internationalization, prefixes
 * are added before this specific "names", and this prefix we should
 * write here in value of this annotation.
 * <p>
 * This is made to easily address to the internationalised properties
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyPrefix {
    String value();
}
