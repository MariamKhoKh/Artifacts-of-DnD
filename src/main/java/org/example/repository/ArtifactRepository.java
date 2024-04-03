package org.example.repository;

import org.example.domain.Artifact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtifactRepository extends JpaRepository<Artifact, Long> {

    List<Artifact> findByIdIn(List<Long> artifactsIds);

    Page<Artifact> findAllByOrderByPriceAsc(Pageable pageable);

    @Query("SELECT artifact FROM Artifact artifact WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'artifactTitle' THEN UPPER(artifact.artifactTitle) " +
            "   WHEN :searchType = 'country' THEN UPPER(artifact.country) " +
            "   ELSE UPPER(artifact.artifacter) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY artifact.price ASC")
    Page<Artifact> searchArtifacts(String searchType, String text, Pageable pageable);

    @Query("SELECT artifact FROM Artifact artifact " +
            "WHERE (coalesce(:artifacters, null) IS NULL OR artifact.artifacter IN :artifacters) " +
            "AND (coalesce(:genders, null) IS NULL OR artifact.artifactGender IN :genders) " +
            "AND (coalesce(:priceStart, null) IS NULL OR artifact.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY artifact.price ASC")
    Page<Artifact> getArtifactsByFilterParams(
            List<String> artifacters,
            List<String> genders,
            Integer priceStart,
            Integer priceEnd,
            Pageable pageable);
}
