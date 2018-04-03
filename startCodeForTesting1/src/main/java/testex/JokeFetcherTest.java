package testex;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {
    private static JokeFetcher jf;
    @Mock
    private static FetcherFactory fac;
    @Mock
    private static IjokeFetcher Edujoke, ChuckNorris, Moma, Tambal;
    @Mock
    private static IDateFormatter df;

    @BeforeClass
    public static void before() throws JokeException {
        df = mock(DateFormatter.class);
        fac = mock(FetcherFactory.class);
        jf = mock(JokeFetcher.class);

        Edujoke = mock(EduJoke.class);
        ChuckNorris = mock(ChuckNorris.class);
        Moma = mock(Moma.class);
        Tambal = mock(Tambal.class);
        List<IjokeFetcher> fetchers = new ArrayList<>();
        fetchers.add(Edujoke);
        fetchers.add(ChuckNorris);
        fetchers.add(Moma);
        fetchers.add(Tambal);

        Jokes jokes = new Jokes();
        jokes.addJoke(new Joke("edujoke", "edu url"));
        jokes.addJoke(new Joke("chuck joke", "ch url"));
        jokes.addJoke(new Joke("mom joke", "mom url"));
        jokes.addJoke(new Joke("tambal joke", "tam url"));

        //doReturn(fetchers).when(fac).getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
        when(fac.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetchers);


//        doReturn(jokes).when(jf).getJokes("EduJoke,ChuckNorris,Moma,Tambal", df);
//        doReturn(jokes).when(jf).getJokes("EduJoke", df);
//        when(df.getFormattedDate()).thenReturn("23 Mar 2018 04:02 pm");
//        when(fac.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(Arrays.asList(Edujoke, ChuckNorris, Moma, Tambal));
//        doReturn("23 Mar 2018 04:02 pm").when(jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal", df)).getTimeZoneString();
//        when(jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal", df).getTimeZoneString()).thenReturn("23 Mar 2018 04:02 pm");
//        given(jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal", df)).willReturn(jokes);
//        when(Edujoke.getJoke()).thenReturn(new Joke("edujoke", "edu url"));
//        when(ChuckNorris.getJoke()).thenReturn(new Joke("chuck joke", "ch url"));
//        when(Moma.getJoke()).thenReturn(new Joke("mom joke", "mom url"));
//        when(Tambal.getJoke()).thenReturn(new Joke("tambal joke", "tam url"));
    }

    @Test
    public void fetcherFactory() throws JokeException {
        List<IjokeFetcher> fetchers = new ArrayList<>();
        fetchers.add(Edujoke);
        fetchers.add(ChuckNorris);
        fetchers.add(Moma);
        fetchers.add(Tambal);
        assertThat(fac.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal"),is(equalTo(fetchers)));
    }

    @Test
    public void getAvailableTypes() throws JokeException {

        assertThat(jf.getAvailableTypes(), hasItems("EduJoke", "ChuckNorris", "Moma", "Tambal"));
        assertThat(jf.getAvailableTypes().size(), is(equalTo(4)));
    }

    @Test
    public void isStringValid() {
        assertThat(jf.isStringValid("EduJoke,ChuckNorris,Moma,Tambal"), is(equalTo(true)));
        assertThat(jf.isStringValid("EduJok,ChuckNorris,Moma,Tambal"), is(equalTo(false)));
    }

    @Test
    public void getFormattedDate() throws JokeException {
        given(df.getFormattedDate()).willReturn("23 Mar 2018 04:02 pm");
        assertThat(jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal", df).timeZoneString, is(equalTo("23 Mar 2018 04:02 pm")));
        verify(df, times(1)).getFormattedDate();
    }

    @Test
    public void getJokes() throws JokeException {
        Jokes jokes = new Jokes();
        jokes.addJoke(new Joke("edujoke", "edu url"));
        jokes.addJoke(new Joke("chuck joke", "ch url"));
        jokes.addJoke(new Joke("mom joke", "mom url"));
        jokes.addJoke(new Joke("tambal joke", "tam url"));


        assertThat(jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal", df), is(equalTo(jokes)));
    }
}