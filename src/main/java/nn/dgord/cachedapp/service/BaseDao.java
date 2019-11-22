package nn.dgord.cachedapp.service;

import java.util.List;

public interface BaseDao<T, ID> {
    List<T> getAll();
    T getOne(ID id);
    void delete(ID id);
    T create(T entity);
    T update(T updatedEntity, ID id);
}
