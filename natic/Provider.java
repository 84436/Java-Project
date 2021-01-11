package natic;

import java.sql.SQLException;

public interface Provider<T extends Object> {
    public T get(T o) throws SQLException;
    public void add(T o) throws SQLException;
    public void edit(T o) throws SQLException;
    public void remove(T o) throws SQLException;
}
