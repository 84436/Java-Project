package natic.review;

import natic.IDGenerator;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class ReviewProvider implements Provider<Review> {
    private ArrayList<Review> ReviewList;
    private IDGenerator IDGen;
    private Connection conn;

    public ReviewProvider(Connection conn, IDGenerator idgen) {
        this.conn = conn;
        this.IDGen = idgen;
    }

    public Review get(Review o) {
        return null;
    }

    public void add(Review o) {
    }

    public void edit(Review o) {
    }

    public void remove(Review o) {
    }
}
