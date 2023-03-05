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
            member.setUsername("member1");
            member.setTeam(team);
//            member.setTeamId(team.getId());
            em.persist(member);

            em.flush();
            em.clear();

            // 조회
            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

            // 연관관계가 없음
//            Team findTeam = em.find(Team.class, findMember.getTeamId());

            // 참조를 사용해서 연관관계 조회
            Team findTeam = findMember.getTeam();

            System.out.println(findTeam.getName());

            // 새로운 팀 B
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            // 회원1에 새로운 팀 B 설정
            member.setTeam(teamB);

            Team findTeam2 = findMember.getTeam();

            System.out.println(findTeam2.getName());

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
