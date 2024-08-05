package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.Miscellanous_heads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MiscellaneousHeadsRepository extends JpaRepository<Miscellanous_heads,String> {
//    @Query("select x from Miscellanous_heads x where x.transaction_name = :expenseType")
//    List<Miscellanous_heads> findByExpenseType(@Param("expenseType") String expenseType);

}
