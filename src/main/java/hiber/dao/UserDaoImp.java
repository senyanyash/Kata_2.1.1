package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      TypedQuery<User> query;
      try {
         query = sessionFactory.getCurrentSession().createQuery("from User as User where User.car.model = :model and User.car.series = :series ");
         query.setParameter("model", model);
         query.setParameter("series", series);
         return query.getSingleResult();
      } catch (RuntimeException e) {
         System.out.println("This car doesn't exist");
      }
      return new User();
   }
}
