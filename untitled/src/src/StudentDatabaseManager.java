package src;

import java.sql.*;

public class StudentDatabaseManager {
    private final Connection connection;

    public StudentDatabaseManager(String url, String utilisateur, String motDePasse) throws SQLException {
        connection = DriverManager.getConnection(url, utilisateur, motDePasse);
    }

    public void ajouterEtudiant(String prenom, String nom, int age, String notes) throws SQLException {
        String requete = "INSERT INTO students (first_name, last_name, age, grades) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(requete)) {
            statement.setString(1, prenom);
            statement.setString(2, nom);
            statement.setInt(3, age);
            statement.setString(4, notes);


            statement.executeUpdate();
        }
    }


    public void modifierEtudiant(int id, String prenom, String nom, int age, String notes) throws SQLException {
        String requete = "UPDATE students SET first_name = ?, last_name = ?, age = ?, grades = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(requete)) {
            statement.setString(1, prenom);
            statement.setString(2, nom);
            statement.setInt(3, age);
            statement.setString(4, notes);


            statement.setInt(5, id);

            statement.executeUpdate();
        }
    }

    public void supprimerEtudiant(int id) throws SQLException {
        String requete = "DELETE FROM students WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(requete)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public void afficherTousLesEtudiants() throws SQLException {
        String requete = "SELECT * FROM students";
        System.out.printf("%-5s| %-15s| %-15s| %-5s| %-5s%n", "ID", "Prénom", "Nom", "Age", "Notes");
        System.out.println("-----------------------------------------------------");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(requete)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String prenom = resultSet.getString("first_name");
                String nom = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                // Récupération de toutes les notes
                String notes = resultSet.getString("grades");


                // Affichage en tableau avec des séparations "|"
                System.out.printf("%-5s| %-15s| %-15s| %-5s| %-5s%n", id, prenom, nom, age, notes);
            }
        }
    }


    public void rechercherEtudiant(int id) throws SQLException {
        String requete = "SELECT * FROM students WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(requete)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String prenom = resultSet.getString("first_name");
                    String nom = resultSet.getString("last_name");
                    int age = resultSet.getInt("age");
                    double notes = resultSet.getDouble("grades");

                    System.out.println("ID: " + id);
                    System.out.println("Prénom: " + prenom);
                    System.out.println("Nom: " + nom);
                    System.out.println("Âge: " + age);
                    System.out.println("Notes: " + notes);
                } else {
                    System.out.println("Aucun étudiant trouvé avec l'ID " + id);
                }
            }
        }
    }

    public void calculerMoyenneEtudiant(int id) throws SQLException {
        String requete = "SELECT * FROM students WHERE id = ?";
        double moyenne = 0;
        int nbNotes = 0;
        try (PreparedStatement statement = connection.prepareStatement(requete)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String notes = resultSet.getString("grades");
                    String[] notesArray = notes.split(",");
                    for (String note : notesArray) {
                        moyenne += Double.parseDouble(note);
                        nbNotes++;
                    }
                    moyenne /= nbNotes;
                    System.out.println("La moyenne de l'étudiant est de " + moyenne);
                } else {
                    System.out.println("Aucun étudiant trouvé avec l'ID " + id);
                }
            }
        }

    }

    public void fermerConnexion() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void trierEtudiantParPrénom() throws SQLException {
        String requete = "SELECT * FROM students ORDER BY last_name";
        // Stockage des étudiants dans un tableau
        String[][] etudiants = new String[100][5];
        int i = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(requete)) {
            while (resultSet.next()) {
                etudiants[i][0] = resultSet.getString("id");
                etudiants[i][1] = resultSet.getString("first_name");
                etudiants[i][2] = resultSet.getString("last_name");
                etudiants[i][3] = resultSet.getString("age");
                etudiants[i][4] = resultSet.getString("grades");
                i++;
            }
        }
        // Tri du tableau par ordre alphabétique
        String[] temp;
        for (int j = 0; j < i; j++) {
            for (int k = j + 1; k < i; k++) {
                if (etudiants[j][1].compareTo(etudiants[k][1]) > 0) {
                    temp = etudiants[j];
                    etudiants[j] = etudiants[k];
                    etudiants[k] = temp;
                } else if (etudiants[j][1].compareTo(etudiants[k][1]) == 0 && etudiants[j][2].compareTo(etudiants[k][2]) > 0) {
                    temp = etudiants[j];
                    etudiants[j] = etudiants[k];
                    etudiants[k] = temp;
                }

            }
        }

        // Affichage du tableau trié a à z
        System.out.printf("%-5s| %-15s| %-15s| %-5s| %-5s%n", "ID", "Prénom", "Nom", "Age", "Notes");
        System.out.println("-----------------------------------------------------");
        for (int j = 0; j < i; j++) {
            System.out.printf("%-5s| %-15s| %-15s| %-5s| %-5s%n", etudiants[j][0], etudiants[j][1], etudiants[j][2], etudiants[j][3], etudiants[j][4]);
        }

    }
}