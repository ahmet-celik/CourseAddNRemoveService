import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;



public class Main {
	private static final Random r = new Random(123456);
	private  final static List<Student> studentList =  new ArrayList<Student>(); // Hazelcast.getList("student-list"); //random Database to look available students
	private  final static List<Course> courseList = new ArrayList<Course>(); //Hazelcast.getList("course-list");    //random Database to look available courses
	private  final static List<Instructor> instList = new ArrayList<Instructor>(); //Hazelcast.getList("course-list");    //random Database to look available courses

	private static final int THREAD_NUMBER = 2;
	private static int  studentSize=0;
	private static int courseSize=0;
	//Some client.
	public static void main(String[] args) {
//		Lock lock = Hazelcast.getLock("generate");
//		lock.lock();
//		try{
			generate();
//		}finally{
//			lock.unlock();
//		}
		studentSize = studentList.size();
		courseSize = courseList.size();
		final CourseAddRemoveService serv = new CourseAddRemoveService(studentList,courseList,instList);
	
		final AtomicInteger counter = new AtomicInteger();
		new Timer().schedule(new TimerTask(){
			public void run() {
				System.out.println(" Per second: " + counter.getAndSet(0));	
			}}, 1000, 1000);
		
	
		for (int i = 1; i <= Main.THREAD_NUMBER; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						int op = r.nextInt(2);
						int studentIndex = r.nextInt(Main.studentSize);
						int courseIndex = r.nextInt(Main.courseSize);
						switch (op) {
							case 0:
//								System.out.println(studentIndex+" ** "+courseIndex);
								serv.addCourse(studentList.get(studentIndex).toString(), courseList.get(courseIndex).toString());
								break;
							case 1:
//								System.out.println(studentIndex+" ** "+courseIndex);
								serv.removeCourse(studentList.get(studentIndex).toString(), courseList.get(courseIndex).toString());
								break;
						}
						counter.incrementAndGet();
					}
					
				}
			});
			t.start();
		}
	}
	
	private static void generate(){
		if(studentList.size()==0 && courseList.size()==0){
		
			for (int i = 0; i < 3; i++) {
				Student aux = new Student(i+1);
				studentList.add(aux);
			}
			studentSize = studentList.size();
			for(int i=65;i<70;i++){
				for(int j=100;j<(101+r.nextInt(2));j++){
					Course aux = new Course(""+((char)i), j, (j-99), 2);
					courseList.add(aux);
				}
			}
			courseSize= courseList.size();
			boolean[] courseGiven = new boolean[courseSize];
			int coursesLeft= courseSize;
			//int numberOfInstructor =Math.max(2,r.nextInt(courseSize)); //minimum 2 teachers.
			for(int i=0;i<8;i++){
				Instructor instructor = new Instructor(i);
				for(int j=0;j<Math.min(r.nextInt(3)+1,coursesLeft);j++){// 1,2 or 3 course may give
					int courseIndex= r.nextInt(courseSize);
					while(courseGiven[courseIndex]){
						courseIndex= r.nextInt(courseSize);
					}
					courseGiven[courseIndex]=true;
					coursesLeft--;
					String course = courseList.get(courseIndex).toString();
					instructor.getCoursesTeaching().add(course);
				}
			    instList.add(instructor);
			}
			for(Instructor i:instList){
				System.out.print("Inst: "+i.getID()+" ");
				for(String c:i.getCoursesTeaching()){
					 System.out.print(c+" ");
				}
				System.out.println();
			}
			System.out.println("Generated!");
		}
	}

}
