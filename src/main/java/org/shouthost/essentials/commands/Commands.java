package org.shouthost.essentials.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Commands {
    String name();

    String permission();

    String syntax() default "";

    String description() default "";

    String[] alias() default {};

    boolean console() default false;

    boolean commandblocks() default false;
}
