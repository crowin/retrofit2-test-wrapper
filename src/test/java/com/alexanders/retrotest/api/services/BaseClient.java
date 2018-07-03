package com.alexanders.retrotest.api.services;

import com.alexanders.retrotest.app.ClientAbstract;

/**
 * @author astolnikov: 03.07.2018
 */
public class BaseClient extends ClientAbstract {

    public BaseClient() {
        super(new ClientBuilder());
    }
}
