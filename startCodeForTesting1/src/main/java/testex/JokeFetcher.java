
package testex;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.response.ExtractableResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class used to fetch jokes from a number of external joke API's
 */
public class JokeFetcher {

    private IFetcherFactory factory;

    public JokeFetcher(IFetcherFactory factory)
    {
        this.factory = factory;
    }

    /**
     * These are the valid types that can be used to indicate required jokes
     * eduprog: Contains joke related to programming and education. API only returns a new value each hour
     * chucknorris: Fetch a chucknorris joke. Not always political correct ;-)
     * moma: Fetch a "MOMA" joke. Defenitely never political correct ;-)
     * tambal: Just random jokes
     */
    private final List<String> availableTypes = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");


    /**
     * The valid string values to use in a call to getJokes(..)
     *
     * @return All the valid strings that can be used
     */
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    /**
     * Verifies whether a provided value is a valid string (contained in availableTypes)
     * @return true if the param was a valid value, otherwise false
     */
    public boolean isStringValid(String jokeTokens)
    {
        String[] tokens = jokeTokens.split(",");
        for (String token : tokens)
        {
            if (!availableTypes.contains(token))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Fetch jokes from external API's as given in the input string - jokesToFetch
     *
     * @param jokesToFetch A comma separated string with values (contained in availableTypes) indicating the jokes
     *                     to fetch. Example: "eduprog,chucknorris,chucknorris,moma,tambal" will return five jokes (two chucknorris)
     * @return A Jokes instance with the requested jokes + time zone adjusted string representing fetch time
     * (the jokes list can contain null values, if a server did not respond correctly)
     * @throws JokeException. Thrown if either of the two input arguments contains illegal values
     */
    public Jokes getJokes(String jokesToFetch, IDateFormatter Idf) throws JokeException
    {
        if (!isStringValid(jokesToFetch))
        {
            throw new JokeException("Inputs (jokesToFetch) contain types not recognized");
        }

        Jokes jokes = new Jokes();

        for (IjokeFetcher fetcher : factory.getJokeFetchers(jokesToFetch))
        {
            jokes.addJoke(fetcher.getJoke());
        }

        String tzString = Idf.getFormattedDate();
        System.out.println(Idf.getFormattedDate());
        jokes.setTimeZoneString(tzString);
        System.out.println(jokes.getTimeZoneString());
        return jokes;
    }

    /**
     * DO NOT TEST this function. It's included only to get a quick way of executing the code
     *
     * @param args
     */
    public static void main(String[] args) throws JokeException {
//        JokeFetcher jf = new JokeFetcher();
//        Jokes jokes = jf.getJokes("eduprog,chucknorris,chucknorris,moma,tambal", new DateFormatter("Europe/Copenhagen"));
//        System.out.println(jokes.getTimeZoneString());
//        jokes.getJokes().forEach((joke) -> {
//            System.out.println(joke);
//        });
//        System.out.println("Is String Valid: " + jf.isStringValid("edu_prog,xxx"));
    }
}
