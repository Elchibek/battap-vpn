package com.battap.vpn.repository;

import com.battap.vpn.domain.VirServer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the VirServer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VirServerRepository extends MongoRepository<VirServer, String> {}
