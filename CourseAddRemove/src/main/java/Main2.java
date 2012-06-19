

public class Main2 {


	public static void main(String[] args) {

		CourseAddRemoveService serv = new CourseAddRemoveService();
		
		serv.addCourse("3", "E-101-2");
		serv.addCourse("3", "E-101-2");
		serv.addCourse("3", "E-101-2");
		serv.removeCourse("3", "E-101-2");
		serv.addCourse("3", "E-101-2");
		serv.addCourse("3", "E-101-2");
//		for(int i=0;i<20;i++){
//			Student s= students.get(r.nextInt(students.size()));
//			Course  c= courses.get(r.nextInt(courses.size()));
//			serv.addCourse(s.toString(), c.toString());
//		}
	}

}

