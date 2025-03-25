package siarhei.luskanau.sql.example.demo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import siarhei.luskanau.sql.example.demo.model.TestModel1;
import siarhei.luskanau.sql.example.demo.model.TestModel2;
import siarhei.luskanau.sql.example.demo.model.TestModel3;

import java.util.List;

public interface TaskRepository extends Repository<TestModel1, Integer> {

    @Query(
            """
SELECT T_VEHICLE_MAKE.*, COUNT(T_LOAN.id) AS sold_count
FROM `loan-schema`.`m_loan` AS T_LOAN
  JOIN `asset-schema`.`asset` AS T_ASSET ON T_LOAN.id = T_ASSET.m_loan_id
  JOIN `asset-schema`.`vehicle_model` AS T_VEHICLE_MODEL ON T_ASSET.model_id = T_VEHICLE_MODEL.id
  JOIN `asset-schema`.`vehicle_make` AS T_VEHICLE_MAKE ON T_VEHICLE_MODEL.vehicle_make_id = T_VEHICLE_MAKE.id
WHERE T_LOAN.disbursedon_date >= :dateFrom
  AND T_LOAN.disbursedon_date < :dateTo
GROUP BY T_VEHICLE_MAKE.id
HAVING sold_count > :soldCount
ORDER BY sold_count DESC
            """
    )
    List<TestModel1> task1a(
            @Param("dateFrom") String dateFrom,
            @Param("dateTo") String dateTo,
            @Param("soldCount") int soldCount
    );

    @Query(
            """
SELECT T_VEHICLE_MAKE.*, COUNT(T_LOAN.id) AS sold_count
FROM `loan-schema`.`m_loan` AS T_LOAN
  JOIN `asset-schema`.`asset` AS T_ASSET ON T_LOAN.id = T_ASSET.m_loan_id
    AND T_LOAN.disbursedon_date >= :dateFrom
    AND T_LOAN.disbursedon_date < :dateTo
  JOIN `asset-schema`.`vehicle_model` AS T_VEHICLE_MODEL ON T_ASSET.model_id = T_VEHICLE_MODEL.id
  RIGHT JOIN `asset-schema`.`vehicle_make` AS T_VEHICLE_MAKE ON T_VEHICLE_MODEL.vehicle_make_id = T_VEHICLE_MAKE.id
GROUP BY T_VEHICLE_MAKE.id
ORDER BY sold_count DESC
            """
    )
    List<TestModel1> task1b(
            @Param("dateFrom") String dateFrom,
            @Param("dateTo") String dateTo
    );

    @Query(
            """
SELECT
  T_LP_SCHEDULE.loan_id,
  T_LP_SCHEDULE.duedate,
  IFNULL(T_LP_SCHEDULE.principal_amount, 0) + IFNULL(T_LP_SCHEDULE.interest_amount, 0) AS payment_amount
FROM `loan-schema`.`m_loan_repayment_schedule` AS T_LP_SCHEDULE
  LEFT JOIN `loan-schema`.m_loan_repayment_schedule AS T_2
    ON T_LP_SCHEDULE.loan_id = T_2.loan_id
      AND T_2.completed_derived = 0
      AND T_2.duedate < T_LP_SCHEDULE.duedate
WHERE T_LP_SCHEDULE.completed_derived = 0 AND T_2.loan_id IS NULL
ORDER BY T_LP_SCHEDULE.loan_id, T_LP_SCHEDULE.duedate
            """
    )
    List<TestModel2> task2();

    @Query(
            """
SELECT T_union.loan_id, SUM(T_union.current_balance) AS current_balance
FROM (
  (
    SELECT
      T_LP_SCHEDULE.loan_id,
        IFNULL(T_LP_SCHEDULE.principal_amount, 0) +
        IFNULL(T_LP_SCHEDULE.interest_amount, 0) +
        IFNULL(T_LP_SCHEDULE.fee_charges_amount, 0) +
        IFNULL(T_LP_SCHEDULE.penalty_charges_amount, 0) AS current_balance
    FROM `loan-schema`.`m_loan_repayment_schedule` AS T_LP_SCHEDULE
  ) UNION (
    SELECT T_transaction.loan_id, T_transaction.amount * IF(T_transaction.is_reversed, 1, -1) AS current_balance
    FROM `loan-schema`.`m_loan_transaction` AS T_transaction
  )
) as T_union
GROUP BY T_union.loan_id
ORDER BY T_union.loan_id
            """
    )
    List<TestModel3> task3();
}
