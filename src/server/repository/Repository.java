package server.repository;

public interface Repository {
    void save(String text);
    String load();
}
