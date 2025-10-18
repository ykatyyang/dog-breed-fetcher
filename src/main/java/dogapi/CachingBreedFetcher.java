package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {

    HashMap<String, List<String>> cache;

    BreedFetcher fetcher;

    private int callsMade = 0;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        cache = new HashMap<>();
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // return statement included so that the starter code can compile and run.
        if (cache.containsKey(breed)) {
            return cache.get(breed);
        } else {
            callsMade += 1;
            List<String> subbreeds = fetcher.getSubBreeds(breed);
            cache.put(breed, subbreeds);
            return subbreeds;
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}