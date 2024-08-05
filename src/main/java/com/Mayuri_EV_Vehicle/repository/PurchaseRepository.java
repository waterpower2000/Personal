package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.Purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {
	List<Purchase> findByRegion(String region);
	@Modifying
	@Transactional
	@Query("UPDATE Purchase p set p.perQuantityPrice= :perQuantityPrice where p.id= :id")
	void updatePayment(@Param("perQuantityPrice") Double perQuantityPrice,@Param("id") String id);

	@Modifying
	@Transactional
	@Query("UPDATE Purchase p set p.perQuantityPrice= :perQuantityPrice,p.full_payment= :full_payment where p.id= :id")
	void updatePaymentFull(@Param("perQuantityPrice") Double perQuantityPrice,
						   @Param("full_payment") boolean full_payment,@Param("id") String id);
}
