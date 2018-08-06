package com.prospring.prospringjpa.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author SiWoo Kim,
 * @version 1.0.0
 * @email sm123tt@gmail.com
 * @github : https://github.com/Siwoo-Kim
 * @since 2018-08-06 오전 10:36
 **/

@Entity
@Getter @ToString
@EqualsAndHashCode(of = {"id","title"})
public class Album {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    private final Singer singer;
    private final String title;
    @Column(name = "RELEASE_dATE")
    private final Date releaseDate;
    @Version
    private final int version;

    private Album() {
        id = 0;
        singer = null;
        title = null;
        releaseDate = null;
        version = 0;
    }

    private Album(Builder builder) {
        this.id = builder.id;
        this.singer = builder.singer;
        this.title = builder.title;
        this.releaseDate = builder.releaseDate;
        this.version = builder.version;
    }

    public static Album of(Album album) {
        return new Builder()
                .id(album.id)
                .singer(album.singer)
                .title(album.title)
                .releaseDate(album.releaseDate)
                .version(album.version)
                .build();
    }

    public static class Builder{
        private long id;
        private Singer singer;
        private String title;
        private Date releaseDate;
        private int version;

        Builder id(long value) {
            this.id = value;
            return this;
        }

        Builder singer(Singer value) {
            this.singer = value;
            return this;
        }

        Builder title(String value) {
            this.title = value;
            return this;
        }

        Builder releaseDate(Date value) {
            this.releaseDate = value;
            return this;
        }

        Builder version(int version) {
            this.version = version;
            return this;
        }

        Album build() {
            return new Album(this);
        }
    }

}
