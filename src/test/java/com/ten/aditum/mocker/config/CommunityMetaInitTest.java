package com.ten.aditum.mocker.config;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * CommunityMetaInit Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 18, 2019</pre>
 */
public class CommunityMetaInitTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: init()
     */
    @Test
    public void testInit() throws Exception {
        new CommunityMetaInit().init();
    }


    /**
     * Method: getForCommunity()
     */
    @Test
    public void testGetForCommunity() throws Exception {
        CommunityMetaInit init = new CommunityMetaInit();
        init.getForCommunity();
    }

    /**
     * Method: postForPerson(String communityId)
     */
    @Test
    public void testPostForPerson() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = CommunityMetaInit.getClass().getMethod("postForPerson", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: postForDevice(String communityId)
     */
    @Test
    public void testPostForDevice() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = CommunityMetaInit.getClass().getMethod("postForDevice", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
