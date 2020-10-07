package com.mroczkowski.map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepo extends CrudRepository<Point, Long> {

    @Query(value = "SELECT * FROM POINT WHERE USER_ID = :user_id", nativeQuery = true)
    List<Point> findAllPointsFromActiveUser(@Param("user_id") Long userId);

    @Query(value = "SELECT * FROM POINT WHERE NOT USER_ID = :user_id", nativeQuery = true)
    List<Point> findAllPointsFromOthers(@Param("user_id") Long userId);


}
