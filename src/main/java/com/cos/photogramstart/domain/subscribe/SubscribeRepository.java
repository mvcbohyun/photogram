package com.cos.photogramstart.domain.subscribe;

import java.util.concurrent.Flow.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscibe, Integer> {

}
