package natic;

public interface Provider<T extends Object> {
    public T get(T o);
    public void add(T o);
    public void edit(T o);
    public void remove(T o);
}
