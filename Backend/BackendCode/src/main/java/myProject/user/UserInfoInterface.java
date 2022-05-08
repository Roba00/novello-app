package myProject.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoInterface extends JpaRepository<UserInfo, Integer> {
    UserInfo findById(int id);

    @Transactional
    void deleteById(int id);
}
