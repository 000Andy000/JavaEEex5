import com.lad.config.MybatisConfig;
import com.lad.config.SpringConfig;
import com.lad.config.SpringMvcConfig;
import com.lad.dao.PictureDao;
import com.lad.model.Picture;
import com.lad.service.Impl.PictureServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author Andy
 * @date 2023-6-26 026 21:44
 */

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, SpringMvcConfig.class, MybatisConfig.class})
public class PictureTest {

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private PictureServiceImpl pictureService;

    @Test
    public void testConcernPic() {
        List<Picture> pictures = pictureDao.selectPicturesByConcern(1, 1, 8);
        System.out.println(pictures);
    }

    @Test
    public void testSelectPicturesByTime() {
        List<Picture> pictures = pictureDao.selectPicturesByTime(8, 8);
        System.out.println(pictures);
    }
    @Test
    public void testSearchPicturesByName() {
        List<Picture> pictures = pictureDao.searchPicturesByNameAndUserId("1","1");
        System.out.println(pictures);
    }


}
