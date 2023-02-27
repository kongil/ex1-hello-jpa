package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em =  emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //영속
            Member member = new Member(200L, "member200");
            em.persist(member);

            //준영속 상태로 만드는 법
            /*
            em.detach(entity);
            em.clear();
            em.close();
            */

            em.flush();

            System.out.println("============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
