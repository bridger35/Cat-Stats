package CatStats;
import java.util.ArrayList;
import java.util.List;

public class Categories {
	private String category;
	private int num_assigns;
	private float category_weight;
	private List<Assignment> assignments;
	
	public Categories(String name, int num_assign, float cat_weight) {
		this.category = name;
		this.category_weight = cat_weight;
		this.num_assigns = num_assign;
		set_array();
	}
	private void set_array() { //intializes all assignments with the proper weight and -1 grade
		assignments = new ArrayList<>();
		float assign_weight = category_weight/num_assigns;
		for(int i = 0; i < num_assigns;i++) {
			Assignment assignment = new Assignment(assign_weight);
			assignments.add(assignment);
		}
	}
	
	public float get_cat_weight() {
		return this.category_weight;
	}
	public int get_num_assign() {
		return this.num_assigns;
	}
	public String get_cat_name() {
		return this.category;
	}
	public Assignment get_assign(int num) {
		return assignments.get(num);
	}
	public int get_assign_size() {
		return assignments.size();
	}
	public List<Assignment> get_assign_list(){
		return assignments;
	}
}
