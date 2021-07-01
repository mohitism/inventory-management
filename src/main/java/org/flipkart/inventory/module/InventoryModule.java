package org.flipkart.inventory.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.flipkart.inventory.InventoryConfiguration;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryModule.class);

    private final HibernateBundle hibernateBundle;

    public InventoryModule(Bootstrap<InventoryConfiguration> bootstrap, HibernateBundle hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    protected void configure() {

    }

    @Provides
    public SessionFactory provideSessionFactory(InventoryConfiguration configuration,
                                                Environment environment) {
        return hibernateBundle.getSessionFactory();
    }
}
