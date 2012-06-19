import java.io.Serializable;

import com.hazelcast.util.ConcurrentHashSet;




public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
		private String abbrv;
		private int ID;
		private int section;
		private int maxQuota;
		private ConcurrentHashSet<String> students;
		
		public Course(String abbrv, int iD, int section, int kota) {
			this.abbrv = abbrv;
			ID = iD;
			this.section = section;
			this.maxQuota = kota;
			this.students = new ConcurrentHashSet<String>();
		}
		public String getAbbrv() {
			return abbrv;
		}
		public int getID() {
			return ID;
		}
		public int getSection() {
			return section;
		}
		public int getMaxQuota() {
			return maxQuota;
		}
		public int studentSize(){
			return students.size();
		}
		
		public boolean addStudent(String s){
			return students.add(s);
		}
		
		public boolean removeStudent(String s){
			return students.remove(s);
		}
		
		public boolean containsStudent(String s){
			return students.contains(s);
		}
		
		public String toString() {	
			return abbrv+"-"+ID+"-"+section;
		}
		
		public boolean valid(){
			return  this.students.size()<=this.maxQuota;
		}
}
