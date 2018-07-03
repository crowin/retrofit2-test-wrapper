# retrofit2-test-wrapper
Retrofit2 applying in functional tests

1. TEST SETTINGS EXAMPLE:

Test class

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

Settings class

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

Class which must be extended in service api classes

public class BaseClient extends ClientAbstract {

    public BaseClient() {
        super(new ClientBuilder());
    }
}


2. TEST API EXAMPLE:

Api interface:
public interface PeopleInterface {

    @GET("people/{id}")
    Call<ResponseBody> get(@Path("id") int userId);
}

Service class:

public class PeopleClient extends BaseClient {
    private PeopleInterface service = apiClient.create(PeopleInterface.class);

    public ResponseContext<PeopleEntity, String> get(int id) {
        Call<ResponseBody> callResponse = service.get(id);
        return getResponseContext(callResponse, PeopleEntity.class, String.class);
    }
}

Class helper:

public class ServicesManager {

    public PeopleClient people() {
        return new PeopleClient();
    }
}


