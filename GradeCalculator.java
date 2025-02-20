package CatStats;
import java.util.List;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map;

public class GradeCalculator {
    public static void main(String[] args) {
        Courses course = new Courses();
        Boolean using = true;
        System.out.println("Welcome to Cat-Stats!");
        Scanner scanner = new Scanner(System.in);
        Boolean round = false;
        
        while (using) {
            System.out.println("Enter a menu item number to pick an option.");
            System.out.println("1. Create a Class");
            System.out.println("2. Enter a grade");
            System.out.println("3. View class grade");
            System.out.println("4. Calculate required class grade");
            System.out.println("5. Predict grade");
            System.out.println("6. Toggle rounding");
            System.out.println("7. Save Courses");
            System.out.println("8. Load Courses");
            System.out.println("9. Delete Course");
            
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume the newline left by nextInt()
            
            if (option == 1) {
                // Create a class (same as before)
                System.out.println("Enter class name:");
                String name = scanner.nextLine();
                Class clas = course.create_class(name);
                System.out.println("How many categories?");
                int num = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                for (int i = 0; i < num; i++) {
                    System.out.println("Enter category name: ");
                    String name1 = scanner.nextLine();
                    System.out.println("Enter category assignment quantity: ");
                    int quan = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    System.out.println("Enter category weight (ex. 35.5 for 35.5%): ");
                    float weight = scanner.nextFloat();
                    scanner.nextLine();  // Consume the newline
                    clas.create_category(name1, quan, weight);
                }
                System.out.println("Custom grade scale?(y/n): ");
                String yn = scanner.next();
                
                if (yn.equalsIgnoreCase("y")) {
                	clas.set_scale(true);
                	//System.out.println(clas.get_grading_scale());
                }else {
                	clas.set_scale(false);
                }
            } else if (option == 2) {
                // Enter grades for assignments (same as before)
                System.out.println("Choose a class");
                print_list_class(course.get_class_list());
                int op = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                Class clas = course.get_class(op - 1);
                
                for (int i = 0; i < clas.get_cat_size(); i++) {
                    Categories cat = clas.get_category(i);
                    System.out.println("Enter grade for " + cat.get_cat_name() + "(y/n)?");
                    String yn = scanner.next();
                    
                    if (yn.equalsIgnoreCase("y")) {
                        System.out.println("Enter -1 for grades you don't have");
                        List<Assignment> assign_list = cat.get_assign_list();
                        Boolean running = true;
                        
                        while (running) {
                            print_list_assign(assign_list);
                            System.out.println("Enter 0 to exit or the number for the assignment grade you want to change");
                            int ops = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline
                            
                            if (ops == 0) {
                                running = false;
                            } else {
                                System.out.println("Enter grade: ");
                                float new_grade = scanner.nextFloat();
                                scanner.nextLine();  // Consume the newline
                                cat.get_assign(ops - 1).set_grade(new_grade);
                            }
                        }
                    }
                }
            } else if (option == 3) {
                // Calculate current grade for the class
                System.out.println("Choose a class");
                print_list_class(course.get_class_list());
                int op = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                Class clas = course.get_class(op - 1);
                
                float grade_total = 0.0f;
                float total_weight= 0.0f;
                // Calculate current grade with weight
                for (int i = 0; i < clas.get_cat_size(); i++) {
                    Categories cat = clas.get_category(i);
                    int count = 0;
                    float grade = 0.0f;
                    Boolean has_assign = false;
                    
                    for (int z = 0; z < cat.get_assign_size(); z++) {
                        Assignment assign = cat.get_assign(z);
                        if (assign.get_grade() != -1) {  // Ignore -1 (not graded)
                            count += 1;
                            has_assign = true;
                            grade += assign.get_grade();
                        }
                    }
                    if(has_assign) {
                    	total_weight += cat.get_cat_weight();
                    }
                    grade = grade/count;
                    grade_total+= grade/100*cat.get_cat_weight();

                    
                }
                grade_total = grade_total/total_weight;
                if (round) {
                    // If the decimal part is 0.5 or greater, round up
                    if (grade_total - Math.floor(grade_total) >= 0.5) {
                    	grade_total = (float) Math.ceil(grade_total);  // Round up using Math.ceil
                    }
                }
                System.out.println(clas.get_class_name() + " current grade: " + getLetterGrade(clas,grade_total));
            } else if (option == 4) { // Calculate required grade to reach a target grade
                System.out.println("Choose a class");
                print_list_class(course.get_class_list());
                int op = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                Class clas = course.get_class(op - 1);
                System.out.println("Enter target grade: ");
                float target_grade = scanner.nextFloat();
                scanner.nextLine();
                
                // Calculate current grade
                float grade_total = 0.0f;
                float total_weight= 0.0f;
                // Calculate current grade with weight
                for (int i = 0; i < clas.get_cat_size(); i++) {
                    Categories cat = clas.get_category(i);
                    int count = 0;
                    float grade = 0.0f;
                    Boolean has_assign = false;
                    
                    for (int z = 0; z < cat.get_assign_size(); z++) {
                        Assignment assign = cat.get_assign(z);
                        if (assign.get_grade() != -1) {  // Ignore -1 (not graded)
                            count += 1;
                            has_assign = true;
                            grade += assign.get_grade();
                        }
                    }
                    if(has_assign) {
                    	total_weight += cat.get_cat_weight();
                    }
                    grade = grade/count;
                    grade_total+= grade/100*cat.get_cat_weight();

                    
                }
                grade_total = grade_total/total_weight;
                
                if (grade_total >= target_grade) {
                    System.out.println("You have all you need to get a " + getLetterGrade(clas, target_grade));
                } else {
                	float target_weight = 0.0f;
                	for (int i = 0; i < clas.get_cat_size(); i++) {
                        Categories cat = clas.get_category(i);
                        
                        
                        
                        for (int z = 0; z < cat.get_assign_size(); z++) {
                            Assignment assign = cat.get_assign(z);
                            if (assign.get_grade() == -1) {  // Ignore -1 (not graded)
                               target_weight+= assign.get_weight();
                            }
                        }
                        

                        
                    }// Formula for remaining required grade
                	float weight_rem = 100-target_weight;
                	grade_total = grade_total/100*weight_rem;
                	if(grade_total+target_weight < target_grade) {
                		System.out.println("It is impossible to achieve that grade!");
                	}else {
                		grade_total = (target_grade-(grade_total+target_weight-target_grade))/target_weight;
                		
                		System.out.println("You need to average a " + getLetterGrade(clas,grade_total) + " on remaining assignments to get a " + target_grade);
                	}
                }
            } else if (option == 5) {
                // Predict grade after input for upcoming assignments
                System.out.println("Choose a class");
                print_list_class(course.get_class_list());
                int op = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                Class clas = course.get_class(op - 1);

                System.out.println("Enter predicted grade: ");
                float target_grade = scanner.nextFloat();
                scanner.nextLine();
                System.out.println("Enter weight of predicted (for individual assignment %): ");
                float pred_weight = scanner.nextFloat();
                scanner.nextLine();
                 
                // Calculate current grade and add the predicted contribution
                float grade_total = 0.0f;
                float total_weight= 0.0f;
                // Calculate current grade with weight
                for (int i = 0; i < clas.get_cat_size(); i++) {
                    Categories cat = clas.get_category(i);
                    int count = 0;
                    float grade = 0.0f;
                    Boolean has_assign = false;
                    
                    for (int z = 0; z < cat.get_assign_size(); z++) {
                        Assignment assign = cat.get_assign(z);
                        if (assign.get_grade() != -1) {  // Ignore -1 (not graded)
                            count += 1;
                            has_assign = true;
                            grade += assign.get_grade();
                        }else if(assign.get_grade() == -1 && assign.get_weight() == pred_weight) {
                        	count += 1;
                            has_assign = true;
                            grade += target_grade;
                        }
                    }
                    if(has_assign) {
                    	total_weight += cat.get_cat_weight();
                    }
                    grade = grade/count;
                    grade_total+= grade/100*cat.get_cat_weight();

                    
                }
                grade_total = grade_total/total_weight;  // Add prediction to final grade
                if (round) {
                    // If the decimal part is 0.5 or greater, round up
                    if (grade_total - Math.floor(grade_total) >= 0.5) {
                    	grade_total = (float) Math.ceil(grade_total);  // Round up using Math.ceil
                    }
                }
                System.out.println("Predicted grade is " + getLetterGrade(clas,grade_total));
            } else if (option == 6) {
                round = !round;
                System.out.println("Rounding is now " + round);
            }else if(option == 7) {
            	System.out.println("Enter filename: ");
            	String filename = scanner.nextLine();
            	saveClassesToFile(course, filename);
            }else if(option ==8) {
            	System.out.println("Enter filename: ");
            	String filename = scanner.nextLine();
            	course = loadClassesFromFile(filename);
            }else if(option == 9) {
            	List classes = course.get_class_list();
            	print_list_class(classes);
            	System.out.println("Enter class number to delete: ");
            	int del = scanner.nextInt();
            	scanner.nextLine();
            	course.delete_class(del-1);
            }
        }
        scanner.close();
    }

    public static void print_list_class(List<Class> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).get_class_name());
        }
    }

    public static void print_list_cat(List<Categories> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).get_cat_name());
        }
    }

    public static void print_list_assign(List<Assignment> list) {
        for (int i = 0; i < list.size(); i++) {
            String grade = (list.get(i).get_grade() == -1) ? "null" : String.valueOf(list.get(i).get_grade());
            System.out.println("Assignment " + (i + 1) + ": " + grade);
        }
    }
    public static String getLetterGrade(Class clas, float score) {
    	TreeMap<String,Float> gradeThresholds= clas.get_grading_scale();
    	score = score*100;
        // Get the grading scale from the Class object
    	for (Map.Entry<String, Float> entry : gradeThresholds.entrySet()) {
    		System.out.println(entry.getValue());
            if (score >= entry.getValue()) {
                return entry.getKey(); // Return the first grade that the score is greater than or equal to
            }
        }
        return "F"; 
    }
    public static void saveClassesToFile(Courses course, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Save each class's data to the file
        	writer.write(course.get_class_size());
        	writer.newLine();
            for (Class clas : course.get_class_list()) {
                // Save class name
                writer.write(clas.get_class_name());
                writer.newLine();

                // Save grading scale
                
                for (Map.Entry<String, Float> entry : clas.get_grading_scale().entrySet()) {
                    writer.write(Float.toString(entry.getValue()));
                    writer.newLine();
                }

                // Save categories and assignments
                writer.write(clas.get_cat_size());
                writer.newLine();
                for (Categories category : clas.get_cat_list()) {
                    writer.write(category.get_cat_name());
                    writer.newLine();
                    writer.write(Float.toString(category.get_cat_weight()));
                    writer.newLine();
                    writer.write(category.get_num_assign());
                    writer.newLine();

                    // Save assignments for each category
                    for (Assignment assign : category.get_assign_list()) {
                        writer.write(Float.toString(assign.get_grade())); // Save assignment grade
                        writer.newLine();
                    }
                }

            }

            System.out.println("All classes saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving classes: " + e.getMessage());
        }
    }
    public static Courses loadClassesFromFile(String filename) {
        Courses courses = new Courses();  // Create a Courses object to store all classes

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            // Read the number of classes
            int numClasses = Integer.parseInt(reader.readLine());

            // Process each class
            for (int i = 0; i < numClasses; i++) {
                // Read class name
                String className = reader.readLine();
                Class currentClass = courses.create_class(className);  // Create the class

                // Read the grading scale (assuming one line with 11 thresholds)
                TreeMap<String, Float> gradingScale = new TreeMap<>();
                line = reader.readLine();
             // We expect 11 lines for the grades A, A-, B+, B, ..., F
             gradingScale.put("A", Float.parseFloat(reader.readLine()));    // Read A
             gradingScale.put("A-", Float.parseFloat(reader.readLine()));   // Read A-
             gradingScale.put("B+", Float.parseFloat(reader.readLine()));   // Read B+
             gradingScale.put("B", Float.parseFloat(reader.readLine()));    // Read B
             gradingScale.put("B-", Float.parseFloat(reader.readLine()));   // Read B-
             gradingScale.put("C+", Float.parseFloat(reader.readLine()));   // Read C+
             gradingScale.put("C", Float.parseFloat(reader.readLine()));    // Read C
             gradingScale.put("C-", Float.parseFloat(reader.readLine()));   // Read C-
             gradingScale.put("D+", Float.parseFloat(reader.readLine()));   // Read D+
             gradingScale.put("D", Float.parseFloat(reader.readLine()));
                currentClass.load_scale(gradingScale);

                // Read number of categories for the current class
                int numCategories = Integer.parseInt(reader.readLine());

                // Process each category
                for (int j = 0; j < numCategories; j++) {
                    // Read category name
                    String categoryName = reader.readLine();
                    // Read category weight
                    float categoryWeight = Float.parseFloat(reader.readLine());
                    // Read number of assignments in the category
                    int numAssignments = Integer.parseInt(reader.readLine());
                    
                    // Create category and auto-generate assignments
                    currentClass.create_category(categoryName, numAssignments, categoryWeight);

                    // Read assignment grades for this category
                    for (int k = 0; k < numAssignments; k++) {
                        float grade = Float.parseFloat(reader.readLine());
                        // Set the grade for the assignment
                        currentClass.get_category(j).get_assign(k).set_grade(grade);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return courses;
    }


    
}