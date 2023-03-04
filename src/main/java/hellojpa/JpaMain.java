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
            // 팀 저장
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            // 멤버 저장
            Member member = new Member();
            member.setName("memeber1");
            member.setTeamId(team.getId());
            em.persist(member);
            
            // 조회
            Member findMember = em.find(Member.class, member.getId());

            // 연관관계가 없음
            Team findTeam = em.find(Team.class, findMember.getTeamId());


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
