package com.prospring.prospringjpa.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SiWoo Kim,
 * @version 1.0.0
 * @email sm123tt@gmail.com
 * @github : https://github.com/Siwoo-Kim
 * @since 2018-08-06 오전 10:23
 **/


@Entity @Getter @ToString(exclude = "albums")
@EqualsAndHashCode(of = {"id","firstName","lastName"})
@SqlResultSetMapping(name = "singerResult",
    entities = @EntityResult(entityClass = Singer.class))
public class Singer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    @Column(name = "FIRST_NAME")
    private final String firstName;
    @Column(name = "LAST_NAME")
    private final String lastName;
    @Column(name = "BIRTH_DATE")
    private final Date birthDate;
    @Version
    private final int version;

    @OneToMany(mappedBy = "singer",
    orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();

    private Singer() {
        id = null;
        firstName = null;
        lastName = null;
        birthDate = null;
        version = 0;
    }

    private Singer(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.version = builder.version;
        this.albums = builder.albums;
    }

    public static Singer of(Long id, String firstName, String lastName, Date birthDate, int version, List<Album> albums) {
        return new Builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .version(version)
                .albums(albums)
                .build();
    }

    public static Singer of(Singer value) {
        return of(value.id, value.firstName, value.lastName, value.birthDate, value.version, value.albums);
    }

    public Singer removeAlbum(Album album) {
        List _copied = albums
                .stream()
                .filter(_album ->  !_album.equals(album))
                .map(Album::of)
                .collect(Collectors.toList());

        return Singer.of(this.id, this.firstName, this.lastName, this.birthDate, this.version, _copied);
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Date birthDate;
        private List<Album> albums;
        private int version;

        Builder id(Long value) {
            this.id = value;
            return this;
        }

        Builder albums(List<Album> albums) {
            this.albums = new ArrayList<>();
            for(Album album: albums) {
                this.albums.add(Album.of(album));
            }
            return this;
        }

        Builder firstName(String value) {
            this.firstName = value;
            return this;
        }

        Builder lastName(String value) {
            this.lastName = value;
            return this;
        }
        Builder birthDate(Date value) {
            this.birthDate = value;
            return this;
        }
        Builder version(int value) {
            this.version = value;
            return this;
        }

        Singer build() {
            return new Singer(this);
        }
    }
}
