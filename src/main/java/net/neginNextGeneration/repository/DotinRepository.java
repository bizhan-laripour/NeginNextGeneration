package net.neginNextGeneration.repository;

import net.neginNextGeneration.entity.Dotin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DotinRepository extends JpaRepository<Dotin , Integer> {

    Optional<Dotin> findByTrackingId(String trackId);
}
