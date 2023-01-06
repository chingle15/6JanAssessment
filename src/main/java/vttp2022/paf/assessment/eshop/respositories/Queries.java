package vttp2022.paf.assessment.eshop.respositories;

public class Queries {

    public static final String SQL_FIND_CUSTOMER_BY_NAME = "select id, name, address, email";
    
    public static final String SQL_INSERT_ORDER = "insert into order(order_id, order_date) values (?,?)";

    public static final String SQL_UPDATE_ORDER = "update order set order_id = ?, order_date = ?";
}
