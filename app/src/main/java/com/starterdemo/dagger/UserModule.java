package com.starterdemo.dagger;

import dagger.Module;
import dagger.Provides;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import com.starterdemo.BasicConfiguration;
import com.starterdemo.persistence.UserDetailsDao;
import com.starterdemo.service.UserService;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Singleton;

@Module
public class UserModule {

    @Provides
    @Singleton
    UserDetailsDao userDetailsDao(BasicConfiguration configuration, Environment environment)
    {
        JdbiFactory factory = new JdbiFactory();
        Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgres");
        return jdbi.onDemand(UserDetailsDao.class);
    }

    @Provides
    @Singleton
    UserService userService(UserDetailsDao userDetailsDao)
    {
        return new UserService(userDetailsDao);
    }

}
