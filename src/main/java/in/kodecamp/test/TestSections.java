package in.kodecamp.test;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.internal.oxm.schema.model.List;

import in.kodecamp.entities.CourseEntity;
import in.kodecamp.entities.SectionEntity;
import in.kodecamp.entities.StudentEntity;

/**
 * @author sunil
 */
public class TestSections {

  private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
  private static EntityManager em = factory.createEntityManager();

  public static void main(String[] args) {
    // testCreateCourse();
    // testCreateStudent();
    Set<StudentEntity> students = getStudentsOf("CSC001_1");
    students.forEach((student) -> {
      System.out.println("Name : " + student.getName());
    });

  }

  private static void testCreateCourse() {
    em.getTransaction().begin();
    em.persist(createCourse());
    em.getTransaction().commit();
  }

  private static void testCreateStudent() {
    em.getTransaction().begin();
    createStudents("CSC001_1").forEach((student) -> {
      em.persist(student);
    });
    em.getTransaction().commit();
  }

  private static CourseEntity createCourse() {
    CourseEntity ce = new CourseEntity();
    ce.setTitle("Computer Science");
    ce.setCode("CSC001");
    createSectionsIn("CSC001").stream().forEach((section) -> {
      ce.addSection(section);
    });
    return ce;
  }

  private static Set<SectionEntity> createSectionsIn(String courseCode) {
    SectionEntity s1 = new SectionEntity();
    s1.setId(courseCode + "_1");
    s1.setName("A");

    SectionEntity s2 = new SectionEntity();
    s2.setId(courseCode + "_2");
    s2.setName("B");

    Set<SectionEntity> sections = new HashSet<>();
    sections.add(s1);
    sections.add(s2);
    return sections;
  }

  private static Set<StudentEntity> createStudents(final String sectionId) {
    SectionEntity se = getSectionBy(sectionId);
    StudentEntity stu1 = new StudentEntity("1");
    stu1.setName("Student 1");
    stu1.setCourse(se.getCourse());
    stu1.setSection(se);

    StudentEntity stu2 = new StudentEntity("2");
    stu2.setName("Student 2");
    stu2.setCourse(se.getCourse());
    stu2.setSection(se);
    Set<StudentEntity> students = new HashSet<StudentEntity>();
    students.add(stu1);
    students.add(stu2);
    return students;
  }

  private static SectionEntity getSectionBy(final String sectionId) {
    SectionEntity se = em.find(SectionEntity.class, sectionId);
    System.out.println("se : " + se);
    return se;
  }

  private static Set<StudentEntity> getStudentsOf(final String sectionId) {
    SectionEntity se = em.find(SectionEntity.class, sectionId);
    return se.getStudents();
  }
}
