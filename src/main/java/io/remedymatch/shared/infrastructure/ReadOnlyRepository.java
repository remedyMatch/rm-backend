package io.remedymatch.shared.infrastructure;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {
	
	Optional<T> findById(final ID id);
	
	T getOne(final ID id);

	List<T> findAll();

	List<T> findAll(final Sort sort);

	Page<T> findAll(final Pageable pageable);
}