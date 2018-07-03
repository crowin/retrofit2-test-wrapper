package com.alexanders.retrotest.api;

import com.alexanders.retrotest.api.services.people.PeopleClient;

/**
 * @author astolnikov: 03.07.2018
 */
public class ServicesManager {

    public PeopleClient people() {
        return new PeopleClient();
    }
}
