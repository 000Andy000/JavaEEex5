import com.lad.config.MybatisConfig;
import com.lad.config.SpringConfig;
import com.lad.config.SpringMvcConfig;
import com.lad.dao.UserDao;
import com.lad.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Andy
 * @date 2023-6-27 027 17:15
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, SpringMvcConfig.class, MybatisConfig.class})
public class UserTest {
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Test
    public void getByIdDaoTest(){
        System.out.println(userDao.selectByUserId(1));
    }

    @Test
    public void getByIdServiceTest(){
        System.out.println(userService.getUserById(1,1));
    }

    @Test
    public void getFansTest(){
        System.out.println(userService.getFans(11,1));
    }

    @Test
    public void getUserByIdTest(){
        System.out.println(userService.getUserById(5,1));
    }




}
