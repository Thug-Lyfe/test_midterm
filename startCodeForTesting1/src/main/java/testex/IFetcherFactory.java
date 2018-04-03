package testex;

import java.util.List;

public interface IFetcherFactory {

    List<String> getAvailableTypes();

    List<IjokeFetcher> getJokeFetchers(String jokesToFetch);

}
