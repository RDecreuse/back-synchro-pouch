package com.synchro.pouch.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class DatasourceConfiguration extends RepositoryRestConfigurerAdapter {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config) {
        // by default, spring-data hides the @Id name when serializing to json
        // to by-pass that, add the classes below (only applies for @Entity / @Id like attributes)
        this.entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> entityType.getBindableJavaType())
                .forEach(aClass -> config.exposeIdsFor(aClass));
    }
}
