package hello;

import com.dt.miniapp.Application;
import com.dt.miniapp.dao.PersonRepository;
import com.dt.miniapp.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chenlei
 * @description
 * @date 2019/3/9 11:15 PM
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestMongo {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void test() {
        personRepository.deleteAll();
        // 创建三个User，并验证User总数
        personRepository.save(Person.builder().id("1").firstName("123").lastName("321").build());
        personRepository.save(Person.builder().id("2").firstName("456").lastName("321").build());
        personRepository.save(Person.builder().id("3").firstName("789").lastName("321").build());
        Assert.assertEquals(3, personRepository.findAll().size());
        log.info("size={}", personRepository.findAll().size());
        // 删除一个User，再验证User总数
        Person u = personRepository.findById("1").orElse(Person.builder().id("4").build());
        personRepository.delete(u);
        Assert.assertEquals(2, personRepository.findAll().size());

        // 删除一个User，再验证User总数
        u = personRepository.findDistinctByFirstName("456");
        log.info("u={}", u.toString());
        personRepository.delete(u);
        Assert.assertEquals(1, personRepository.findAll().size());
    }
}
