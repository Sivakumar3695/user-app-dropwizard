/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.starterdemo;

import com.starterdemo.model.CustomException;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.Map;

public class UserApplication extends Application<BasicConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);

    public static void main(String[] args) throws Exception {
        new UserApplication().run("server", "app-config.yml");
    }

    @Override
    public void initialize(Bootstrap<BasicConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }

    @Override
    public void run(BasicConfiguration basicConfiguration, Environment environment) {
        UserComponent userComponent = DaggerUserComponent
                .builder().environment(environment).configuration(basicConfiguration)
                .build();
        environment.jersey().register(userComponent.getUserResource());
        environment.jersey().register(userComponent.getGraphQLResource());
        registerCustomExceptionResponse(environment);
    }

    private void registerCustomExceptionResponse(Environment environment)
    {
        environment.jersey().register(new ExtendedExceptionMapper<CustomException>() {
            @Override
            public Response toResponse(CustomException exception) {

                LOGGER.info("Custom exception handling response.");
                LOGGER.error(exception.getMessage());
                exception.printStackTrace();
                return Response
                        .status(exception.getExceptionCode().responseCode)
                        .entity(Map.of("message", exception.getExceptionCode().message))
                        .build();
            }

            @Override
            public boolean isMappable(CustomException exception) {
                LOGGER.info("Check if custom exception resp can be handled");
                return exception.getExceptionCode() != null;
            }
        });
    }
}
