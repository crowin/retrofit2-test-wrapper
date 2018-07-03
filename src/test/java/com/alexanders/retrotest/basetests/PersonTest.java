package com.alexanders.retrotest.basetests;

import com.alexanders.retrotest.api.services.people.PeopleEntity;
import com.alexanders.retrotest.app.ResponseContext;
import com.alexanders.retrotest.api.ServicesManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author astolnikov: 03.07.2018
 */
public class PersonTest {
    ServicesManager api = new ServicesManager();

    @Test
    public void personShouldBeInResponse() {
        ResponseContext<PeopleEntity, String> people = api.people().get(1);
        Assert.assertEquals(200, people.getResponse().getCode());
        Assert.assertNotNull(people.getResponseEntity().getName());
    }

    @Test
    public void personShouldNotBeInResponse() {
        ResponseContext<PeopleEntity, String> people = api.people().get(111111111);
        Assert.assertEquals(404, people.getResponse().getCode());
        Assert.assertTrue(people.getError().getMessage().contains("Not found"));
    }
}
