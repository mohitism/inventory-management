package org.flipkart.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Stage;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.flipkart.inventory.core.Author;
import org.flipkart.inventory.core.Book;
import org.flipkart.inventory.module.InventoryModule;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<InventoryConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "InventoryApp";
    }

    private final HibernateBundle<InventoryConfiguration> hibernateBundle =
        new HibernateBundle<InventoryConfiguration>(Author.class, Book.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(InventoryConfiguration configuration) {
                return configuration.getDatabase();
            }
        };

    @Override
    public void initialize(final Bootstrap<InventoryConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)
            )
        );

        bootstrap.addBundle(new MigrationsBundle<InventoryConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(InventoryConfiguration configuration) {
                return configuration.getDatabase();
            }
        });

        bootstrap.addBundle(GuiceBundle.<InventoryConfiguration>builder()
            .enableAutoConfig("org.flipkart.inventory")
            .modules(new InventoryModule(bootstrap,hibernateBundle))
            .printDiagnosticInfo()
            .build(Stage.DEVELOPMENT));

        configureObjectMapper(bootstrap.getObjectMapper());
    }

    @Override
    public void run(InventoryConfiguration inventoryConfiguration, Environment environment) throws Exception {

    }

    private void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
