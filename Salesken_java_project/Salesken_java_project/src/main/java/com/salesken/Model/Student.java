package com.salesken.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(indexName = "student")
public class Student {

	@Id
	private Integer studentRoll;
	private String studentName;
	private Integer mathematicsMark;
	private Integer scienceMark;
	private Integer englishMark;
	private Integer semester;

}
