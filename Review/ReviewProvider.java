package Review;

import Core.Provider;
import Core.IDGenerator;
import java.util.ArrayList;

public class ReviewProvider implements Provider<Review> {
    private ArrayList<Review> ReviewList;
    private IDGenerator IDGen;

    @Override
    public Review get(Review o) {
        return null;
    }

    @Override
    public void add(Review o) {
    }

    @Override
    public void edit(Review o) {
    }

    @Override
    public void remove(Review o) {
    }
}
