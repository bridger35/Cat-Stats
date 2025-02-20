package CatStats;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.Scanner;

public class Class {
    private List<Categories> categories;
    private TreeMap<String, Float> grading_scale; // Map letter grades to numerical grades (Float)
    private String name;

    public Class(String name) {
        this.name = name;
        this.categories = new ArrayList<>();
        this.grading_scale = new TreeMap<>();
    }

    public String get_class_name() {
        return this.name;
    }

    public void create_category(String name, int num_assign, float cat_weight) {
        Categories category = new Categories(name, num_assign, cat_weight);
        categories.add(category);
    }
    public void load_scale(TreeMap<String, Float> scale) {
    	grading_scale = scale;
    }

    // Set grading scale - reversing the map to use letter grades as keys and numbers as values
    public void set_scale(Boolean custom) {
        if (custom) {
            custom_scale();
        } else {
            // Default grading scale
            grading_scale.put("A", 93.0f);
            grading_scale.put("A-", 90.0f);
            grading_scale.put("B+", 87.0f);
            grading_scale.put("B", 83.0f);
            grading_scale.put("B-", 80.0f);
            grading_scale.put("C+", 77.0f);
            grading_scale.put("C", 73.0f);
            grading_scale.put("C-", 70.0f);
            grading_scale.put("D+", 67.0f);
            grading_scale.put("D", 63.0f);
            grading_scale.put("D-", 60.0f);
        }
    }

    // Custom scale for grades entered by the user
    public void custom_scale() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter numerical grades for each letter grade:");

        System.out.print("A: ");
        float grade = scanner.nextFloat();
        grading_scale.put("A", grade);

        System.out.print("A-: ");
        grade = scanner.nextFloat();
        grading_scale.put("A-", grade);

        System.out.print("B+: ");
        grade = scanner.nextFloat();
        grading_scale.put("B+", grade);

        System.out.print("B: ");
        grade = scanner.nextFloat();
        grading_scale.put("B", grade);

        System.out.print("B-: ");
        grade = scanner.nextFloat();
        grading_scale.put("B-", grade);

        System.out.print("C+: ");
        grade = scanner.nextFloat();
        grading_scale.put("C+", grade);

        System.out.print("C: ");
        grade = scanner.nextFloat();
        grading_scale.put("C", grade);

        System.out.print("C-: ");
        grade = scanner.nextFloat();
        grading_scale.put("C-", grade);

        System.out.print("D+: ");
        grade = scanner.nextFloat();
        grading_scale.put("D+", grade);

        System.out.print("D: ");
        grade = scanner.nextFloat();
        grading_scale.put("D", grade);

        System.out.print("D-: ");
        grade = scanner.nextFloat();
        grading_scale.put("D-", grade);

        // Don't close scanner here as we are using it elsewhere (main method might need it)
    }

    public TreeMap<String, Float> get_grading_scale() {
        return grading_scale;
    }

    public List<Categories> get_cat_list() {
        return categories;
    }

    public Categories get_category(int num) {
        return categories.get(num);
    }

    public int get_cat_size() {
        return categories.size();
    }
}
