package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.dao.UserDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class})
public class AbstractTest {

    @Autowired
    private UserDAO userDAO;

    //Below an example of test
    @Test
    public void someTest(){
        //User user = userDAO.getUserById(BigInteger.valueOf(21));
        Assert.assertEquals(1,1);
    }

}
