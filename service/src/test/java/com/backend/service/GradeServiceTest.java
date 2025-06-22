package com.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.model.Grade;
import com.backend.repository.GradeRepository;

@ExtendWith(MockitoExtension.class)
public class GradeServiceTest {

  @InjectMocks
  private GradeService gradeService = new GradeServiceImpl();

  @Mock
  private GradeRepository gradeRepository;

  @BeforeEach
  public void setup() {
    List<Grade> mock = new ArrayList<Grade>();
    mock.add(new Grade((long) 1, "1"));
    when(gradeRepository.findAll()).thenReturn(mock);
  }

  @Test
  public void getGrades() {

    List<Grade> grades = gradeService.getGrades();
    assertEquals(1, grades.size());
    assertEquals(1, grades.get(0).getId());
    assertEquals("1", grades.get(0).getName());
  }
}
