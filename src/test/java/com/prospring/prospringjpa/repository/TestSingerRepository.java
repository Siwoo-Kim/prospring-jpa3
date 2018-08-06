package com.prospring.prospringjpa.repository;

import com.prospring.prospringjpa.config.JpaConfiguration;
import com.prospring.prospringjpa.domain.Album;
import com.prospring.prospringjpa.domain.Singer;
import com.prospring.prospringjpa.view.SingerSummary;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author SiWoo Kim,
 * @version 1.0.0
 * @email sm123tt@gmail.com
 * @github : https://github.com/Siwoo-Kim
 * @since 2018-08-06 오전 10:31
 **/


public class TestSingerRepository {

    static SingerRepository singerRepository;

    @BeforeClass
    public static void setup() {
        singerRepository = new AnnotationConfigApplicationContext(JpaConfiguration.class)
                .getBean(SingerRepository.class);
    }

    @Test
    public void findAll() {
        assertEquals(singerRepository.findAll().size(), 3);
        System.out.println(singerRepository.findAll());

        System.out.println( singerRepository.findAllFetched() );

        singerRepository.findAllFetched()
                .stream()
                .map(Singer::getAlbums)
                .forEach(System.out::println);

        assertEquals(singerRepository.findAllNative().size(), 3);
    }

    @Test
    public void findById() {
        Singer singer = singerRepository.findById(1l)
                .orElse(null);
        assertNotNull(singer);
        assertNotNull(singer.getAlbums());
        System.out.println(singer);
        System.out.println(singer.getAlbums());
    }

    @Test
    public void findAllSummaries() {
        List<SingerSummary> singerSummary = singerRepository.findAllSummaries();
        System.out.println(singerSummary);
        assertNotNull(singerSummary);
    }

    @Transactional
    @Test
    public void save() {
        Singer singer = Singer.of(null, "Test", "tester", new Date(), 0, new ArrayList<>());
        singerRepository.save(singer);
        assertNotNull(singer);
        Singer _singer = singerRepository.findById(singer.getId()).orElse(null);
        assertNotNull(_singer);
        assertTrue(singer.equals(_singer));
        System.out.println(_singer);

        singer = singerRepository.findById(1l).orElse(null);
        String newName = "new Name";
        singer = Singer.of(singer.getId(), newName, singer.getLastName(), singer.getBirthDate(), singer.getVersion(), singer.getAlbums());
        singerRepository.save(singer);
        _singer = singerRepository.findById(1l).orElse(null);
        assertEquals(_singer.getFirstName(), newName);
        System.out.println(_singer);
        System.out.println(_singer.getAlbums());

        Album album = _singer.getAlbums().get(0);
        _singer = _singer.removeAlbum(album);
        System.out.println(_singer.getAlbums());
        assertFalse(_singer.getAlbums().contains(album));
        singerRepository.save(_singer);
        _singer = singerRepository.findById(_singer.getId()).orElse(null);
        assertFalse(_singer.getAlbums().contains(album));
        System.out.println(_singer.getAlbums());

    }

}
