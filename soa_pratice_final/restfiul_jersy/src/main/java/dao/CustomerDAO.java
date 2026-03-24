package dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import model.Customer;

public class CustomerDAO implements Serializable {

    // 🔹 ดึงลูกค้าทั้งหมด (ใช้ Fetch Join เพื่อเลี่ยง Lazy Loading error)
    public List<Customer> getAllCustomers() {
        try (Session session = SessionUtil.getSession()) {
            String hql = "select distinct c from Customer c left join fetch c.phonenumbers";
            return session.createQuery(hql, Customer.class).list();
        }
    }
    

    // 🔹 ค้นหาด้วย ID
    public Customer getCustomerById(int id) {
        try (Session session = SessionUtil.getSession()) {
            return session.get(Customer.class, id);
        }
    }

    // 🔹 ค้นหาด้วย username (สำหรับ Validate Auth)
    public Customer findCustomer(String username) {
        try (Session session = SessionUtil.getSession()) {
            return session.createQuery("FROM Customer WHERE username = :u", Customer.class)
                          .setParameter("u", username).list().stream().findFirst().orElse(null);
                         // .uniqueResult();
        }
    }

    // 🔹 เพิ่มลูกค้า
    public boolean addCustomer(Customer c) {
        Transaction tx = null;
        try (Session session = SessionUtil.getSession()) {
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 อัปเดตลูกค้า
    public boolean updateCustomer(Customer c) {
        Transaction tx = null;
        try (Session session = SessionUtil.getSession()) {
            tx = session.beginTransaction();
            session.update(c);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 ลบลูกค้า
    public boolean deleteCustomer(int id) {
        Transaction tx = null;
        try (Session session = SessionUtil.getSession()) {
            tx = session.beginTransaction();
            Customer c = session.get(Customer.class, id);
            if (c != null) {
                session.delete(c);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
    
//    
// // ดึงลูกค้าทั้งหมดแบบ Simple
//    public List<Customer> getAllCustomers() {
//        try (Session session = SessionUtil.getSession()) {
//            // ไม่ต้อง Join Fetch เพราะไม่มี phonenumbers
//            return session.createQuery("from Customer", Customer.class).list();
//        }
//    }
//
//    // ค้นหาตาม ID แบบบรรทัดเดียว
//    public Customer getCustomerById(int id) {
//        try (Session session = SessionUtil.getSession()) {
//            return session.get(Customer.class, id);
//        }
//    }
//}
}