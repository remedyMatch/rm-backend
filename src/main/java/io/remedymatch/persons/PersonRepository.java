package io.remedymatch.persons;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, UUID> {

    PersonEntity findByUserName(String userName);
}
