package com.battap.vpn.repository;

import com.battap.vpn.domain.Wg;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Wg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WgRepository extends MongoRepository<Wg, String> {}
