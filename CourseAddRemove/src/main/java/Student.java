import java.io.Serializable;

import com.hazelcast.util.ConcurrentHashSet;


public class Student implements Serializable {

		private static final long serialVersionUID = 2L;
		private  int ID;
		private ConcurrentHashSet<String> courses;
		
		public Student(int id){
				this.ID =id;
				this.courses=new ConcurrentHashSet<String>();
		}
		
		public int courseSize(){
			return courses.size();
		}
		
		public boolean addCourse(String c){
			return courses.add(c);
		}
		
		public boolean removeCourse(String c){
			return courses.remove(c);
		}
		
		public boolean containsCourse(String c){
			return courses.contains(c);
		}
			
		public String toString() {		
				return  ""+ID;
		}
}
