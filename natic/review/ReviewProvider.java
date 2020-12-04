package natic.review;

import natic.IDGenerator;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class ReviewProvider implements Provider<Review> {
    private ArrayList<Review> ReviewList;
    private IDGenerator IDGen;
    private Connection conn;

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
