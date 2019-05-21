package com.bridgelabz.fundoo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoo.model.Label;

public interface LabelRepositoryInterface extends MongoRepository<Label, String> {

	List<Label> findByUserId(String userId);

	Optional<Label> findByLabelId(String labelId);

}
