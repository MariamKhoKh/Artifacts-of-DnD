package org.example.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "artifacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Artifact {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artifact_id_seq")
    @SequenceGenerator(name = "artifact_id_seq", sequenceName = "artifact_id_seq", initialValue = 109, allocationSize = 1)
    private Long id;

    @Column(name = "artifact_title", nullable = false)
    private String artifactTitle;

    @Column(name = "artifacter", nullable = false)
    private String artifacter;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "artifact_gender", nullable = false)
    private String artifactGender;

    @Column(name = "product_top_notes", nullable = false)
    private String productTopNotes;

    @Column(name = "product_middle_notes", nullable = false)
    private String productMiddleNotes;

    @Column(name = "product_base_notes", nullable = false)
    private String productBaseNotes;

    @Column(name = "description")
    private String description;

    @Column(name = "filename")
    private String filename;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "volume", nullable = false)
    private String volume;

    @Column(name = "type", nullable = false)
    private String type;
}
