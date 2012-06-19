import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.util.ConcurrentHashSet;


public class Instructor implements EntryListener<String, Course>{
	private int ID;
	private ConcurrentHashSet<String> coursesTeaching;
	
	public Instructor(int iD) {
		ID = iD;
		this.coursesTeaching = new ConcurrentHashSet<String>();
	}
	
	public int getID() {
		return ID;
	}

	public ConcurrentHashSet<String> getCoursesTeaching() {
		return coursesTeaching;
	}

	@Override
	public void entryAdded(EntryEvent<String, Course> event) {
		System.out.println("to: "+ID+" Topic: added course; "+event.getKey());
		
	}

	@Override
	public void entryRemoved(EntryEvent<String, Course> event) {
		System.out.println("to: "+ID+" Topic: removed course; "+event.getKey());
		
	}

	@Override
	public void entryUpdated(EntryEvent<String, Course> event) {
		System.out.println("to: "+ID+" Topic: updated course; "+event.getKey());
		
	}

	@Override
	public void entryEvicted(EntryEvent<String, Course> event) {
		System.out.println("to: "+ID+" Topic: evicted course; "+event.getKey());
	}
	
	
}
