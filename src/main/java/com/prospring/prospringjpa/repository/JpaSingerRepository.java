package com.prospring.prospringjpa.repository;

import com.prospring.prospringjpa.domain.Singer;
import com.prospring.prospringjpa.view.SingerSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author SiWoo Kim,
 * @version 1.0.0
 * @email sm123tt@gmail.com
 * @github : https://github.com/Siwoo-Kim
 * @since 2018-08-06 오전 10:30
 **/

@Repository
@Transactional
public class JpaSingerRepository implements SingerRepository {

    @PersistenceContext
    EntityManager entityManager;
    private static Logger logger = LoggerFactory.getLogger(JpaSingerRepository.class);

    private static final String NATIVE_QUERY_FIND_ALL
            = "select id, first_name, last_name, birth_date, version from Singer ";

    @Override
    public List<Singer> findAllNative() {
        return entityManager.createNativeQuery(NATIVE_QUERY_FIND_ALL, "singerResult")
                .getResultList();
    }
    @Override
    public List<Singer> findAll() {
        return entityManager
                .createQuery("select s from Singer s", Singer.class)
                .getResultList();
    }

    @Override
    public Optional<Singer> findById(long id) {
        try {
            return Optional.ofNullable(entityManager
                    .createQuery("select distinct s from Singer s " +
                            "left join fetch s.albums a " +
                            "where s.id = :id ", Singer.class)
                    .setParameter("id", id)
                    .getSingleResult());
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Singer> findAllFetched() {
        return entityManager
                .createQuery("select distinct s from Singer s left join fetch s.albums a ", Singer.class)
                .getResultList();
    }

    private static final String SELECT_ALL_SUMMARIES
            = "select new com.prospring.prospringjpa.view.SingerSummary(s.firstName, s.lastName, a.title) " +
            "from Singer s " +
            "left join s.albums a " +
            "where a.releaseDate = ( select max(a2.releaseDate) from Album a2 where a2.singer.id = s.id )";

    @Override
    public List<SingerSummary> findAllSummaries() {
        return entityManager
                .createQuery(SELECT_ALL_SUMMARIES, SingerSummary.class)
                .getResultList();
    }

    @Override
    public Singer save(Singer singer) {
        if(singer.getId() == null) {
            logger.info("Inserting data");
            entityManager.persist(singer);
        } else {
            logger.info("Updating exiting data");
            entityManager.merge(singer);
        }
        logger.info("Generated id " + singer.getId());
        return singer;
    }

    @Override
    public void delete(Singer singer) {
        Singer _singer = entityManager.merge(singer);
        entityManager.remove(_singer);
        logger.info("Singer with id: " + singer.getId() + " deleted ");
    }
}
