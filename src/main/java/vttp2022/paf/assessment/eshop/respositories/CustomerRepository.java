package vttp2022.paf.assessment.eshop.respositories;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.EshopApplication;
import vttp2022.paf.assessment.eshop.models.Customer;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> findCustomerByName(String name) {
        final List<Customer> customers = new LinkedList<>();
        SqlRowSet rs = null;
            rs = jdbcTemplate.queryForRowSet(SQL_FIND_CUSTOMER_BY_NAME);
			return customers;
    }
}
    