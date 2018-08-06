package com.prospring.prospringjpa.repository;/**
 * @author SiWoo Kim,
 * @email sm123tt@gmail.com
 * @version 1.0.0
 * @github : https://github.com/Siwoo-Kim
 * @since 2018-08-06 오전 10:30
 **/

import com.prospring.prospringjpa.domain.Singer;
import com.prospring.prospringjpa.view.SingerSummary;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @author SiWoo Kim, 
 * @email sm123tt@gmail.com
 * @version 1.0.0
 * @since 2018-08-06 오전 10:30
 * @github : https://github.com/Siwoo-Kim
 **/

public interface SingerRepository {

    List<Singer> findAllNative();

    List<Singer> findAll();
    Optional<Singer> findById(long id);
    List<Singer> findAllFetched();

    List<SingerSummary> findAllSummaries();

    Singer save(Singer singer);

    void delete(Singer singer);
}
