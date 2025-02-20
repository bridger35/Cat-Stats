package CatStats;
import java.util.List;
import java.util.ArrayList;

public class Courses {
	private List<Class> classes;
	public Class create_class(String name) {
		Class clas = new Class(name);
		classes.add(clas);
		return clas;
	}
	public Courses() {
		classes = new ArrayList<>();
	}
	public void delete_class(int num) {
		classes.remove(num);
	}
	public Class get_class(int num) {
		return classes.get(num);
	}
	public List<Class> get_class_list() {
		return classes;
	}
	public int get_class_size() {
		return classes.size();
	}
}
