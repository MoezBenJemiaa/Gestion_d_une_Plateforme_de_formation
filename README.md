# Gestion d'une Plateforme de Formation

**Gestion_d_une_Plateforme_de_formation** is a Java application designed to manage an educational/training platform, supporting both trainers ("Formateur") and students ("Etudiant"). The platform allows user registration, authentication, course (formation) management, and provides tailored graphical interfaces for each user type.

## Features

- **User Authentication & Registration:** Secure login and signup functionality for trainers and students.
- **Role-based Interfaces:**
  - **Formateur (Trainer):** Propose and manage training courses, view formations they've created.
  - **Etudiant (Student):** Browse available courses, enroll or drop from courses, view their own enrollments.
- **Database Connectivity:** All user and course data are stored and managed in a MySQL database.
- **Graphical User Interface:** Built with Java Swing, providing an interactive desktop UI.
- **Course Management:** Trainers can propose new courses; students can enroll in, view, and drop courses.
- **Error Handling:** Custom exceptions and validations for robust user input and actions.

## Technologies Used

- **Java** (main language)
- **Swing** (UI framework)
- **MySQL** (database)
- **JDBC** (database connectivity)

## Main Classes and Structure

- `Main.java` – Launches the app and connects to the database.
- `MainGUI.java` – Core GUI logic, including login, registration, and main window navigation.
- `Manipilation Db/Manipulation_DB.java` – Handles database connection and management.
- `Manipilation Db/Manipulation_DB_For_user.java` – User-specific DB queries (register, authenticate, update info).
- `Manipilation Db/Manipulation_DB_For_Formateur.java` – Trainer-specific DB actions (manage courses).
- `Manipilation Db/Manipulation_DB_For_Etudiant.java` – Student-specific DB actions (enrollments, courses).
- `classes/Formation.java`, `classes/Etudiant.java` – Object models for courses and students.
- `GUI_Interfaces/EtudiantGUI.java`, `GUI_Interfaces/UserGUI.java` – Interface logic for students and users.

## Getting Started

1. **Clone the repository:**  
   `git clone https://github.com/MoezBenJemiaa/Gestion_d_une_Plateforme_de_formation.git`
2. **Configure MySQL database:**  
   Update the connection string and credentials in `Manipilation Db/Manipulation_DB.java`.
3. **Compile and run:**  
   Use your preferred Java IDE, or run from command line:
   ```
   javac Main.java
   java Main
   ```
4. **Login or Register:**  
   Use the GUI to create an account and start using the platform.

## License

_No license specified. Please contact the repository owner for usage or distribution rights._

## Author

- [Moez Ben Jemiaa](https://github.com/MoezBenJemiaa)

---

> This project was created for educational and demonstration purposes. Contributions and suggestions are welcome!
