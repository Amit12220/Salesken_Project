package com.salesken.Repo;

import java.util.List;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.salesken.Model.Student;
@Repository
public interface StudentRepo extends ElasticsearchRepository<Student, Integer> {

	public List<Student> findBySemester(int semester);

}