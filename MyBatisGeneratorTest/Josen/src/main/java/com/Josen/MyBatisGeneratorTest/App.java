package com.Josen.MyBatisGeneratorTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
        
        
        SqlSession session = sqlSessionFactory.openSession(true); //Make the session autocommite true. Default autocommit is false.
        try {
            
            StudentsDomainObjectMapper mapper = session.getMapper(StudentsDomainObjectMapper.class);
            
            
            //Query
            StudentsDomainObject student = mapper.selectByPrimaryKey("000000001");
            
            if (null != student)
            {
                System.out.println(String.format("The Age of %s is %d.", student.getName(), student.getAge()));
            }
            
            //Insert
            StudentsDomainObject student1 = new StudentsDomainObject();
            student1.setID("000000003");
            student1.setName("Miyuer");
            student1.setAge(new Integer(28));
            
            mapper.insert(student1);
            
            List<StudentsDomainObject> students = mapper.selectAll();
            System.out.println(String.format("Total %d students, include Miyuer", students.size()));
            PrintAllStudents(students);
            
            //Update
            student = mapper.selectByPrimaryKey("000000001");
            student.setAge(30);
            
            mapper.updateByPrimaryKey(student);
            System.out.println(String.format("Update the age of %s", "000000001"));
            students = mapper.selectAll();
            PrintAllStudents(students);
            
            //Delete
            mapper.deleteByPrimaryKey("000000003");
            students = mapper.selectAll();
            System.out.println(String.format("Total %d students. Miyuer is leaving", students.size()));
            PrintAllStudents(students);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        finally 
        {
            //session.commit(); //If the session is not autocommit, need to commit manually.
            session.close();
        }

    }
    
    public static void PrintAllStudents(List<StudentsDomainObject> students)
    {
        for (StudentsDomainObject one : students)
        {
            System.out.println(String.format("Name:%s Age:%d", one.getName(), one.getAge()));
        }
    }
}
