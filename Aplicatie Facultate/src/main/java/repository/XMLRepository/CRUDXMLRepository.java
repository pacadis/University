package repository.XMLRepository;

import domains.Entity;

public interface CRUDXMLRepository<ID, E extends Entity<ID>> {
    void loadData();
    void saveAllToFile();
}
