package natic.review;

import natic.Log;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class ReviewProvider implements Provider<Review> {
    private Connection conn;

    public ReviewProvider(Connection conn) {
        this.conn = conn;
    }

    public Review get(Review o) {
        return null;
    }

    public ArrayList<Review> get(String ISBN) {
        try {
            String query = String.format(
                    "SELECT Reviews.*, Name from Reviews, Accounts WHERE Reviews.CustomerID = Accounts.ID and Reviews.ISBN = '%s'",
                    ISBN);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Review> resList = new ArrayList<>();
            while (rs.next()) {
                Review res = new Review();
                res.setISBN(rs.getString("ISBN"));
                res.setCustomerID(rs.getString("CustomerID"));
                res.setReviewScore(rs.getInt("ReviewScore"));
                res.setReviewText(rs.getString("ReviewText"));
                res.setCustomerName(rs.getString("Name"));
                resList.add(res);
            }
            Log.l.info(String.format("None: Get REVIEWS by query returned"));
            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public float getRating(String ISBN) {
        try {
            String query = String.format(
                    "SELECT * from Reviews WHERE Reviews.ISBN = '%s'", ISBN);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            float sum = 0;
            float count = 0;
            while (rs.next()) {
                sum += rs.getInt("ReviewScore");
                count++;
            }
            Log.l.info(String.format("%s: Calculate rating", ISBN));
            return sum / count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void add(Review o) {
        try {
            String query = "INSERT INTO REVIEWS (CustomerID, ISBN, ReviewScore, ReviewText) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, o.getCustomerID());
            stmt.setString(2, o.getISBN());
            stmt.setInt(3, o.getReviewScore());
            stmt.setString(4, o.getReviewText());

            stmt.executeUpdate();
            Log.l.info(String.format("%s to %s: inserted into REVIEW", o.getCustomerID(), o.getISBN()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DO WE EVEN NEED THIS?
    public void edit(Review o) {
    }

    // ALSO THIS
    public void remove(Review o) {
    }
}
