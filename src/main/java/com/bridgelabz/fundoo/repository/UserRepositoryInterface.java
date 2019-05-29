package com.bridgelabz.fundoo.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.User;

@Repository
public interface UserRepositoryInterface extends  MongoRepository<User, String>
{
	Optional<User> findByEmailId(String  emailId);
	Optional<User> findByUserId(String userId);

}