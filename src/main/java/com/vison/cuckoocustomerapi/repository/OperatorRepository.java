package com.vison.cuckoocustomerapi.repository;

import com.vison.cuckoocustomerapi.entity.Operator;
import org.springframework.data.repository.CrudRepository;

public interface  OperatorRepository extends CrudRepository<Operator,Long> {
    Operator findByUsername(String username);

}
