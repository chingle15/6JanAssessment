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
import vttp2022.paf.assessment.eshop.models.Order;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Order insertOrder(final Order order) {
        KeyHolder keyholder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT_ORDER,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, order.getOrderId());
                ps.setString(2, order.getCreatedBy());
                ps.setTimestamp(3, new Timestamp(((Object) order.getOrderDate()).toDateTime().getMillis()));
                return ps;
            }, keyholder);
            BigInteger primaryKeyVal = (BigInteger) keyholder.getKey();
            order.setOrderId(primaryKeyVal.intValue());
    }}

    public boolean updateOrder(final Order order) {
        return jdbcTemplate.update(SQL_UPDATE_ORDER,
                order.getOrderId(),
				order.getCreatedBy(),
                order.getOrderDate(),
                new Timestamp(((Object) order.getOrderDate()).toDateTime().getMillis())
				)> 0;
    }


	public static void main(String[] args)   
{  
UUID uuid=UUID.randomUUID(); //Generates random UUID  
System.out.println(uuid);  
} }
