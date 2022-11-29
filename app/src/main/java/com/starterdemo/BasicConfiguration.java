package com.starterdemo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class BasicConfiguration extends Configuration {

    @NotNull
    private final String defaultMessage;

    @Valid
    @NotNull
    private DataSourceFactory dataSource = new DataSourceFactory();

    @JsonCreator
    public BasicConfiguration(@JsonProperty("defaultMessage") String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    @JsonProperty("dataSource")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.dataSource = factory;
    }

    @JsonProperty("dataSource")
    public DataSourceFactory getDataSourceFactory() {
        return dataSource;
    }
}
