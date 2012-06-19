import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.hazelcast.core.Hazelcast;


public class server {

     //creates data and put them into the server.
	public static void main(String[] args) {
		ArrayList<Student> students= new ArrayList<Student>();
		ArrayList<Course> courses= new ArrayList<Course>();
		Random r = new Random(2009400111);
		for (int i = 0; i < 3; i++) {
			Student aux = new Student(i+1);
			students.add(aux);
		}
		for(int i=65;i<70;i++){
			for(int j=100;j<(101+r.nextInt(2));j++){
				Course aux = new Course(""+((char)i), j, (j-99), 3+r.nextInt(10));
				courses.add(aux);
			}
		}
		
		Map<String, Student> studentsMap = Hazelcast.getMap("students-map");
		for (Student s : students) {
			studentsMap.put(s.toString(),s);
		}
		Map<String, Course> 	coursesMap = Hazelcast.getMap("courses-map");
		for (Course c : courses) {
			coursesMap.put(c.toString(), c);
		}
		System.out.println("SERVER SAYS: "+studentsMap.size()+"---"+coursesMap.size());

		
	
	}

}
