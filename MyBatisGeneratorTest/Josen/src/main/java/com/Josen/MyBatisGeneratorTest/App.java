package com.Josen.MyBatisGeneratorTest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import dao.StudentsDomainObjectMapper;
import model.StudentsDomainObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        //Print the path of the resource
        //System.out.println(App.class.getResource(""));
        //System.out.println(App.class.getResource("/DBConfig.xml"));
        
        
        String resource = "DBConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        
        
        SqlSession session = sqlSessionFactory.openSession();
        try {
            
            StudentsDomainObjectMapper mapper = session.getMapper(StudentsDomainObjectMapper.class);
            
            StudentsDomainObject student = mapper.selectByPrimaryKey("000000001");
            
            if (null != student)
            {
                System.out.println(String.format("The Age of %s is %d.", student.getName(), student.getAge()));
            }
            
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            session.close();
        }

    }
}
