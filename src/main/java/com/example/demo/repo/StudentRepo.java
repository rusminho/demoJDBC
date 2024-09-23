package com.example.demo.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Student;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(rollbackFor = DataAccessException.class)
public class StudentRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void save (Student student) {
        String sql = "insert into student (id, name) values (?,?)";
        int rows = jdbcTemplate.update(sql, student.getId(), student.getName());
        System.out.println(rows + " row(s) affected");

    }

    public List<Student> findAll  () {
        String sql = "select * from student";

        RowMapper<Student> mapper = new RowMapper<Student>()
        {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                return s;
            }
        };

        List<Student> students = jdbcTemplate.query(sql, mapper);

        return students;

    }
}
