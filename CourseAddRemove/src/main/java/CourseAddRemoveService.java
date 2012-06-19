import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.Transaction;

public class CourseAddRemoveService {
	private static  final ConcurrentMap<String, Student> 		studentsMap= Hazelcast.getMap("students-map"); // student->courses
	private static  final ConcurrentMap<String, Course> 		coursesMap =  Hazelcast.getMap("courses-map"); // course->students

  
	public CourseAddRemoveService(){
		if(studentsMap.size()==0 && coursesMap.size()==0)
			throw new IllegalStateException("Database must be loaded first!");
		System.out.println("# of students: "+studentsMap.size()+"\n# of courses: "+coursesMap.size());
	} 
	
	public CourseAddRemoveService(List<Student> students,List<Course> courses){

		 Lock l = Hazelcast.getLock("load");
		 l.lock();
		 try {
			if(studentsMap.size()==0 && coursesMap.size()==0){
					
					for (Student s : students) {
						studentsMap.put(s.toString(),s);
					}
					for (Course c : courses) {
						coursesMap.put(c.toString(), c);
					}
					System.out.println("# of students: "+studentsMap.size()+"\n# of courses: "+coursesMap.size());
			}
		 } finally {
		   l.unlock();
		 }
	} 
	
	

	public boolean addCourse(String studentID, String key) {
//		coursesMap.lock(key);
//		studentsMap.lock(studentID);
		
		boolean success=false;
	    Transaction txn = Hazelcast.getTransaction();
	    txn.begin();
		try{

			Course course = CourseAddRemoveService.coursesMap.get(key);
			Student student = CourseAddRemoveService.studentsMap.get(studentID);
		
			if (course.studentSize() <  course.getMaxQuota() ) {
				if (!course.containsStudent(studentID) && !student.containsCourse(key)){
					course.addStudent(studentID);
					coursesMap.put(key, course);
					
					student.addCourse(key);
					studentsMap.put(studentID,student);
					
//					System.out.println(studentID + " will take " + key);
					
					success=true;
				} else {//already added one of the them.
//					System.out.println(studentID
//									+ " already has taken "
//									+ key);
//				
				}
			} else {//Quota Limit Reached
//				System.out.println(studentID+" Quota Limit Reached! "+key);
				
			}
			txn.commit();
		}catch(Exception e){
			txn.rollback();
			return false;
		}
		return success;
	}
	
	public boolean removeCourse(String studentID, String key) {
//		coursesMap.lock(key);
//		studentsMap.lock(studentID);
		
		boolean success=false;
	    Transaction txn = Hazelcast.getTransaction();
		txn.begin();
		try{
	
			Course course = CourseAddRemoveService.coursesMap.get(key);
			Student student = CourseAddRemoveService.studentsMap.get(studentID);
				
			if (course.containsStudent(studentID) && student.containsCourse(key)){
				course.removeStudent(studentID);
				coursesMap.put(key, course);
				
				student.removeCourse(key);
				studentsMap.put(studentID,student);
				
//				System.out.println(studentID + " removed " + key);
				
				success= true;
			} else {//already added one of the them.
//				System.out.println(studentID
//								+ " already removed "
//								+ key);
	
			}
			txn.commit();
		}catch(Exception e){
			txn.rollback();
			return false;
		}
		return success;
	}
}
