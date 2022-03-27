package com.battap.vpn.repository;

import com.battap.vpn.domain.Tunnel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Tunnel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TunnelRepository extends MongoRepository<Tunnel, String> {}
