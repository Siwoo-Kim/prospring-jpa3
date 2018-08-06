package com.prospring.prospringjpa.view;

import lombok.Getter;
import lombok.ToString;

/**
 * @author SiWoo Kim,
 * @version 1.0.0
 * @email sm123tt@gmail.com
 * @github : https://github.com/Siwoo-Kim
 * @since 2018-08-06 오전 11:10
 **/

@Getter @ToString
public class SingerSummary {
    private final String firstName;
    private final String lastName;
    private final String latestAlbum;

    private SingerSummary() {
        firstName = null;
        lastName = null;
        latestAlbum = null;
    }

    public SingerSummary(String firstName, String lastName, String latestAlbum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.latestAlbum = latestAlbum;
    }

    public static SingerSummary of(String firstName, String lastName, String latestAlbum) {
        return new SingerSummary(firstName, lastName, latestAlbum);
    }

}
