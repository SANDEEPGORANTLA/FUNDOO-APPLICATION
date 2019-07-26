package com.bridgelabz.fundoo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoo.model.Label;

public interface LabelRepositoryInterface extends MongoRepository<Label, String> {
	Optional<Label> findByLabelIdAndUserId(String labelId, String userId);

	Optional<Label> findByLabelId(String labelId);

	List<Label> findByUserId(String userId);

}
