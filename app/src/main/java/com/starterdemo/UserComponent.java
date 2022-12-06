package com.starterdemo;

import com.starterdemo.Resources.GraphQLResource;
import com.starterdemo.dagger.GraphQLModule;
import com.starterdemo.dagger.UserModule;
import dagger.BindsInstance;
import dagger.Component;
import io.dropwizard.setup.Environment;
import com.starterdemo.Resources.UserResource;

import javax.inject.Singleton;

@Component(modules = {UserModule.class, GraphQLModule.class})
@Singleton
interface UserComponent {
    UserResource getUserResource();
    GraphQLResource getGraphQLResource();

    @Component.Builder
    interface Builder
    {
        @BindsInstance
        Builder environment(Environment environment);

        @BindsInstance
        Builder configuration(BasicConfiguration summonerServiceConfiguration);

        UserComponent build();
    }
}
