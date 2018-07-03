package com.alexanders.retrotest.api.services;

import com.alexanders.retrotest.app.ClientBuilderInterface;

/**
 * @author astolnikov: 03.07.2018
 */
public class ClientBuilder implements ClientBuilderInterface {
    @Override
    public String getBaseUrl() {
        return "https://swapi.co/api/";
    }

    @Override
    public boolean isUsedSSL() {
        return true;
    }
}
