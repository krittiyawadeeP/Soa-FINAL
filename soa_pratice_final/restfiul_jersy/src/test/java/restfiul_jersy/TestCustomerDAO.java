package restfiul_jersy;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import dao.CustomerDAO;
import model.Customer;
import model.Phonenumber;

public class TestCustomerDAO {
	public static void main(String[] args) throws SQLException { // testing Session, you can put this in a separate
																	// class if you want
		CustomerDAO c = new CustomerDAO();
		Customer cus = new Customer("VVS", "S1", "password", new HashSet<Phonenumber>());

		Phonenumber p = new Phonenumber();
		p.setNumber("0999999999");
		 // use helper to keep both sides in sync
		cus.addPhonenumber(p);

		// save customer and its phone number in one transaction (cascade)
		c.addCustomer(cus);
		
		// verify
		for (Customer cs : c.getAllCustomers()) {
			System.out.println(cs.getName() + " " + cs.getUsername() + " " + cs.getPwd());

			for (Phonenumber ph : cs.getPhonenumbers()) {
				System.out.println("   " + ph.getNumber());
			}
		}	}
}