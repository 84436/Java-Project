package natic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A simple linear ID generator
 */
public class IDGenerator {
    private String IDPrefix;
    private int GeneratorState;
    private Connection conn;

    public IDGenerator(Connection conn, String prefix) throws SQLException {
        this.conn = conn;
        this.IDPrefix = prefix;

        if (conn == null)
            throw new SQLException("Invalid connection");
        
        // Call to DB to get generator state
        dbGet();
    }

    private void dbGet() {
        try (Statement stmt = conn.createStatement()) {
            String query = String.join("\n",
                "SELECT State",
                "FROM idgen_states",
                String.format("WHERE Prefix = \"%s\"", IDPrefix)
            );
            ResultSet r = stmt.executeQuery(query);
            r.next();
            GeneratorState = r.getInt(1);
        }
        catch (SQLException e) {
            Log.l.warning("Failed to get ID generator state");
            e.printStackTrace();
        }
    }

    private void dbUpdate() {
        try (Statement stmt = conn.createStatement()) {
            String query = String.join("\n",
                "UPDATE idgen_states",
                String.format("SET State = %d", GeneratorState),
                String.format("WHERE Prefix = \"%s\"", IDPrefix)
            );
            if (stmt.executeUpdate(query) == 0) {
                Log.l.warning("Failed to update ID generator state");
            }
        }
        catch (SQLException e) {
            Log.l.warning("Failed to update ID generator state");
            e.printStackTrace();
        }
    }

    /**
     * Return current (latest) ID
     * @return An ID string corresponding to the current state
     */
    public String current() {
        String r = String.format("%s%08d", IDPrefix, GeneratorState);
        return r;
    }
    
    /**
     * Increment the current state by one, update the corresponding state in database, then return a new ID (that hopefully doesn't collide with existing ones).
     * @return A new ID string corresponding to the new state
     */
    public String next() {
        GeneratorState += 1;
        dbUpdate();
        String r = String.format("%s%08d", IDPrefix, GeneratorState);
        return r;
    }
}
