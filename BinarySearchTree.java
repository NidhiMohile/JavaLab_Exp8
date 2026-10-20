import java.util.Scanner;

// Class representing an individual student record (Node)
class StudentNode {
    int rollNumber;
    String name;
    int age;
    double gpa;
    
    StudentNode left, right;

    public StudentNode(int rollNumber, String name, int age, double gpa) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        this.left = null;
        this.right = null;
    }

    public void displayRecord() {
        System.out.println("Roll Number : " + rollNumber);
        System.out.println("Name        : " + name);
        System.out.println("Age         : " + age);
        System.out.println("GPA         : " + gpa);
        System.out.println("----------------------------");
    }
}

// Class representing the Binary Search Tree
class BinarySearchTree {
    private StudentNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    // 1. Insertion
    public void insert(int rollNumber, String name, int age, double gpa) {
        root = insertRec(root, rollNumber, name, age, gpa);
        System.out.println("Student record inserted successfully.");
    }

    private StudentNode insertRec(StudentNode root, int rollNumber, String name, int age, double gpa) {
        if (root == null) {
            return new StudentNode(rollNumber, name, age, gpa);
        }

        if (rollNumber < root.rollNumber) {
            root.left = insertRec(root.left, rollNumber, name, age, gpa);
        } else if (rollNumber > root.rollNumber) {
            root.right = insertRec(root.right, rollNumber, name, age, gpa);
        } else {
            System.out.println("Error: Roll number already exists!");
        }

        return root;
    }

    // 2. Search
    public StudentNode search(int rollNumber) {
        return searchRec(root, rollNumber);
    }

    private StudentNode searchRec(StudentNode root, int rollNumber) {
        if (root == null || root.rollNumber == rollNumber) {
            return root;
        }

        if (rollNumber < root.rollNumber) {
            return searchRec(root.left, rollNumber);
        }

        return searchRec(root.right, rollNumber);
    }

    // 3. Deletion
    public void delete(int rollNumber) {
        root = deleteRec(root, rollNumber);
    }

    private StudentNode deleteRec(StudentNode root, int rollNumber) {
        if (root == null) {
            System.out.println("Roll number not found in the records.");
            return null;
        }

        if (rollNumber < root.rollNumber) {
            root.left = deleteRec(root.left, rollNumber);
        } else if (rollNumber > root.rollNumber) {
            root.right = deleteRec(root.right, rollNumber);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                System.out.println("Student record deleted successfully.");
                return root.right;
            } else if (root.right == null) {
                System.out.println("Student record deleted successfully.");
                return root.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            root.rollNumber = minValue(root.right);
            // Copy other fields from successor as well (simplified by copying data)
            // For full record synchronization, we'd copy name, age, gpa from successor node.
            // Let's implement a helper or inline successor extraction:
            StudentNode successor = findMinNode(root.right);
            root.name = successor.name;
            root.age = successor.age;
            root.gpa = successor.gpa;

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.rollNumber);
        }

        return root;
    }

    private int minValue(StudentNode root) {
        int minv = root.rollNumber;
        while (root.left != null) {
            minv = root.left.rollNumber;
            root = root.left;
        }
        return minv;
    }

    private StudentNode findMinNode(StudentNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // 4. Traversals
    public void inorder() {
        System.out.println("\n--- Student Records (In-Order / Sorted by Roll No) ---");
        inorderRec(root);
    }

    private void inorderRec(StudentNode root) {
        if (root != null) {
            inorderRec(root.left);
            root.displayRecord();
            inorderRec(root.right);
        }
    }

    public void preorder() {
        System.out.println("\n--- Student Records (Pre-Order Traversal) ---");
        preorderRec(root);
    }

    private void preorderRec(StudentNode root) {
        if (root != null) {
            root.displayRecord();
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    public void postorder() {
        System.out.println("\n--- Student Records (Post-Order Traversal) ---");
        postorderRec(root);
    }

    private void postorderRec(StudentNode root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            root.displayRecord();
        }
    }
}

// Main class providing the interactive menu
public class BinarySearchTree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree bst = new BinarySearchTree();
        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("   UNIVERSITY STUDENT RECORD SYSTEM     ");
            System.out.println("========================================");
            System.out.println("1. Insert Student Record");
            System.out.println("2. Search Student Record by Roll Number");
            System.out.println("3. Delete Student Record by Roll Number");
            System.out.println("4. Display Records (In-Order / Sorted)");
            System.out.println("5. Display Records (Pre-Order)");
            System.out.println("6. Display Records (Post-Order)");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 7: ");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll Number: ");
                    int roll = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    System.out.print("Enter GPA: ");
                    double gpa = scanner.nextDouble();
                    bst.insert(roll, name, age, gpa);
                    break;

                case 2:
                    System.out.print("Enter Roll Number to search: ");
                    int searchRoll = scanner.nextInt();
                    StudentNode foundNode = bst.search(searchRoll);
                    if (foundNode != null) {
                        System.out.println("\nRecord Found:");
                        foundNode.displayRecord();
                    } else {
                        System.out.println("Student with Roll Number " + searchRoll + " not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Roll Number to delete: ");
                    int deleteRoll = scanner.nextInt();
                    bst.delete(deleteRoll);
                    break;

                case 4:
                    bst.inorder();
                    break;

                case 5:
                    bst.preorder();
                    break;

                case 6:
                    bst.postorder();
                    break;

                case 7:
                    System.out.println("Exiting application. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice! Please choose between 1 and 7.");
            }
        } while (choice != 7);

        scanner.close();
    }
}
