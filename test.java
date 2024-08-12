import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RepositoryProvider {

    private final Map<Class<?>, JpaRepository<?, Long>> repositories = new HashMap<>();

    @Autowired
    public RepositoryProvider(ApplicationContext applicationContext) {
        // Retrieve all beans that implement JpaRepository
        Map<String, JpaRepository> beans = applicationContext.getBeansOfType(JpaRepository.class);

        for (JpaRepository<?, Long> repository : beans.values()) {
            Class<?> entityType = getEntityType(repository);
            repositories.put(entityType, repository);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> JpaRepository<T, Long> getRepository(Class<T> clazz) {
        return (JpaRepository<T, Long>) repositories.get(clazz);
    }

    private Class<?> getEntityType(JpaRepository<?, Long> repository) {
        // Use reflection to get the entity type from the repository's generic type
        return (Class<?>) ((java.lang.reflect.ParameterizedType) repository.getClass()
                .getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}