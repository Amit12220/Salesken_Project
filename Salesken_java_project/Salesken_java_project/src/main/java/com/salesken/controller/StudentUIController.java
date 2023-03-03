package com.salesken.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesken.Model.Student;
import com.salesken.Repo.StudentRepo;


@Controller
public class StudentUIController {
	
	@Autowired
	private StudentRepo sRepo;

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";

	}

	@GetMapping("/student")
	public String saveStudent() {
		return "saveStudent";

	}

	@PostMapping("/student")
	public String saveStudent(@RequestParam Integer studentRoll, @RequestParam String studentName,
			@RequestParam Integer mathematicsMark, @RequestParam Integer scienceMark, @RequestParam Integer englishMark,
			@RequestParam Integer semester) {
		Student student = new Student(studentRoll, studentName, mathematicsMark, scienceMark, englishMark, semester);
		sRepo.save(student);
		return "welcome";

	}

	@GetMapping("/getPercentage")
	public String getPercentage() {
		return "getPercentage";
	}

	@PostMapping("/getPercentage")
	public String getPercentageView(Model model, @RequestParam Integer semester) {
		int sum = 0;
		int count = 0;
		List<Student> std = sRepo.findBySemester(semester);
		for (Student sts : std) {
			count++;
			sum = sum + sts.getMathematicsMark() + sts.getEnglishMark() + sts.getScienceMark();
		}
		sum = sum / count;
		sum = sum * 100 / 300;
		model.addAttribute("avg", sum);
		return "getPercentageResult";
	}

	@GetMapping("/avgMarks")
	public String avgMarks() {

		return "avgMarks";
	}

	@PostMapping("/avgMarks")
	public String avgMarks(Model model, @RequestParam String subject) {
		Iterable<Student> itr = sRepo.findAll();
		List<Student> students = new ArrayList<>();
		itr.forEach(students::add);
		int count = 0;
		int sum = 0;
		for (Student st : students) {
			System.out.println(st);
			count++;
			if (subject.equalsIgnoreCase("mathematics")) {
				sum += st.getMathematicsMark();
			} else if (subject.equalsIgnoreCase("english")) {
				sum += st.getEnglishMark();
			} else if (subject.equalsIgnoreCase("science")) {
				sum += st.getScienceMark();
			}
		}
		model.addAttribute("avgMarks", sum / count);
		return "avgResult";
	}

	@GetMapping("/topTwo")
	public String topView() {
		return "topTwo";
	}

	@PostMapping("/topTwo")
	public String topView(Model model) {
		Iterable<Student> itr = sRepo.findAll();

		List<Student> students = new ArrayList<>();
		itr.forEach(students::add);
		HashMap<String, Integer> map = new HashMap<>();
		for (Student stud : students) {
			map.put(stud.getStudentName(), (stud.getMathematicsMark() + stud.getEnglishMark() + stud.getScienceMark()) / 3);
		}

		List<Entry<String, Integer>> l = new LinkedList<Entry<String, Integer>>(map.entrySet());
		Collections.sort(l, new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> s1, Entry<String, Integer> s2) {
				return s2.getValue() - s1.getValue();
			}

		});

		List<String> list1 = new ArrayList<>();
		int count = 0;
		for (Map.Entry<String, Integer> name : l) {
			if (count <3) {
				list1.add(name.getKey());
				count++;
			}

		}

		model.addAttribute("topper2", list1);
		return "topTwoResult";
	}

}
