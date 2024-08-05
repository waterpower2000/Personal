package com.Mayuri_EV_Vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="TB_PART_PAYMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartPayment {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "part_payment_id", nullable = false, unique = true)
    private String id;

    @Column(name="sales_id")
    private String sales_id;

    @Column(name="sales_customer_name")
    private String sales_customer_name;

    @Column(name="sales_totalPrice")
    private Double sales_totalPrice;

    @Column(name="cashAmount")
    private String cashAmount;

    @Column(name="checkNumber")
    private String checkNumber;

    @Column(name="checkAmount")
    private String checkAmount;

    @Column(name="online_tran_type")
    private String online_tran_type;

    @Column(name="online_tran")
    private String online_tran;

    @Column(name="onlineAmount")
    private String onlineAmount;

    @Column(name="guarantorAmount")
    private String guarantorAmount;

    @Column(name = "created_on")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate createdOn;

    @ManyToOne
    @JoinColumn(name = "eriksha_variant_details_id")
    Sales sales;

    @Column(name="tranaction_date")
    private String tranaction_date;
    public PartPayment(String sales_id,String sales_customer_name,Double sales_totalPrice,String cashAmount,String checkNumber,String checkAmount,String online_tran_type,
                       String online_tran,String onlineAmount,String guarantorAmount,Sales sales,String tranaction_date)
    {
        this.sales_id=sales_id;
        this.sales_customer_name=sales_customer_name;
        this.sales_totalPrice=sales_totalPrice;
        this.cashAmount=cashAmount;
        this.checkNumber=checkNumber;
        this.checkAmount=checkAmount;
        this.online_tran_type=online_tran_type;
        this.online_tran=online_tran;
        this.onlineAmount=onlineAmount;
        this.guarantorAmount=guarantorAmount;
        this.createdOn = LocalDate.now();
        this.sales=sales;
        this.tranaction_date=tranaction_date;
    }


}
