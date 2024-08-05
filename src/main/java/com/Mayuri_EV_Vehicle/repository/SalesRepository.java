package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.dto.SalesDto;
import com.Mayuri_EV_Vehicle.entity.Sales;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SalesRepository extends JpaRepository<Sales,String> {
	List<Sales> findByRegion(String region);

	@Modifying
	@Transactional
	@Query("UPDATE Sales s SET s.cashAmount = :cashAmount, s.checkAmount = :checkAmount, s.onlineAmount = :onlineAmount, " +
			"s.guarantorAmount = :guarantorAmount, s.sales_totalPrice = :totalPrice, s.perQuantityPrice = :perQuantityPrice, " +
			"s.checkNumber = :checkNumber, s.online_tran_type = :online_tran_type, s.online_tran = :online_tran " +
			"WHERE s.id = :id")
	void updatePayment(@Param("cashAmount") String cashAmount,
						@Param("checkAmount") String checkAmount,
						@Param("onlineAmount") String onlineAmount,
						@Param("guarantorAmount") String guarantorAmount,
						@Param("totalPrice") Double totalPrice,
						@Param("perQuantityPrice") Double perQuantityPrice,
						@Param("checkNumber") String checkNumber,
						@Param("online_tran_type") String online_tran_type,
						@Param("online_tran") String online_tran,
						@Param("id") String id);

	@Modifying
	@Transactional
	@Query("UPDATE Sales s SET s.cashAmount = :cashAmount, s.checkAmount = :checkAmount, s.onlineAmount = :onlineAmount, " +
			"s.guarantorAmount = :guarantorAmount, s.sales_totalPrice = :totalPrice, s.perQuantityPrice = :perQuantityPrice, " +
			"s.checkNumber = :checkNumber, s.online_tran_type = :online_tran_type, s.online_tran = :online_tran, s.full_payment = :full_payment " +
			"WHERE s.id = :id")
	void updatePaymentFull(@Param("cashAmount") String cashAmount,
							@Param("checkAmount") String checkAmount,
							@Param("onlineAmount") String onlineAmount,
							@Param("guarantorAmount") String guarantorAmount,
							@Param("totalPrice") Double totalPrice,
							@Param("perQuantityPrice") Double perQuantityPrice,
							@Param("checkNumber") String checkNumber,
							@Param("online_tran_type") String online_tran_type,
							@Param("online_tran") String online_tran,
						   @Param("full_payment") boolean full_payment,
							@Param("id") String id);



}
