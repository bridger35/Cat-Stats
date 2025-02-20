package CatStats;

public class Assignment {
	private float assign_grade;
	private float assign_weight;
	public Assignment(float weight) {
		this.assign_grade = -1; //-1 means unentered grade
		this.assign_weight = weight;
	}
	public float get_grade() {
		return this.assign_grade;
	}
	public float get_weight() {
		return this.assign_weight;
	}
	public void set_grade(float grade) {
		this.assign_grade = grade;
	}
}
