package br.com.areadigital.areadigital.restrepository;

import br.com.areadigital.areadigital.models.Person;
import br.com.areadigital.areadigital.restrepository.view.PersonView;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = PersonView.class)
public interface PersonRestRepository extends PagingAndSortingRepository<Person, Long> {

    List<Person> findByFirstnameContainingIgnoreCase(String name);
    Person findByEmail(String username);

    @RestResource(exported = false)
    void deleteById(String aLong);
}
