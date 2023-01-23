package com.winterhold.dao;


import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LoanRepository extends JpaRepository<Loan,Long> {


    @Query("""
            SELECT new com.winterhold.dto.loan.LoanGridDTO(
            lo.id,
            bo.title,
            CONCAT(cus.firstName, ' ', cus.lastName),
            lo.loanDate,
            lo.dueDate,
            lo.returnDate
            )
            FROM Loan AS lo
                JOIN lo.customer AS cus
                JOIN lo.book AS bo
            WHERE bo.title LIKE %:title% AND
            CONCAT (cus.firstName, ' ', cus.lastName) LIKE %:name%
            """)
    public Page<LoanGridDTO> findTitleName(@Param("title") String title,
                                           @Param("name") String customerNumber,
                                           Pageable pageable);






//    @Query("""
//            SELECT COUNT(lo.customerNumber)
//            FROM Loan as lo
//            WHERE lo.customerNumber = :number AND
//            lo.returnDate IS NULL
//            """)
//    public Long totalLoaned(String customerNumber);
//
//    @Query("""
//            SELECT COUNT(lo.bookCode)
//            FROM Loan as lo
//            WHERE lo.bookCode = :code AND
//            lo.returnDate IS NULL
//            """)
//    public Long totalBookByCode(String bookCode);
}
