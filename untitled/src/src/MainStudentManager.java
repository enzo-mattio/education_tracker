package src;
import java.util.Scanner;
import java.sql.SQLException;

public class MainStudentManager {
    private static final String URL = "jdbc:mysql://localhost:3306/education_tracker";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "Enzane0728*";

    public static void main(String[] args) {
        try {
            StudentDatabaseManager databaseManager = new StudentDatabaseManager(URL, UTILISATEUR, MOT_DE_PASSE);
            Scanner scanner = new Scanner(System.in);

            boolean quitter = false;

            while (!quitter) {
                afficherMenu();
                System.out.print("Veuillez choisir une option : ");
                int choix = scanner.nextInt();
                scanner.nextLine(); // Vider la ligne après la lecture de l'entier

                switch (choix) {
                    case 1:
                        ajouterEtudiant(databaseManager, scanner);
                        break;
                    case 2:
                        modifierEtudiant(databaseManager, scanner);
                        break;
                    case 3:
                        supprimerEtudiant(databaseManager, scanner);
                        break;
                    case 4:
                        afficherTousLesEtudiants(databaseManager, scanner);
                        break;
                    case 5:
                        rechercherEtudiant(databaseManager, scanner);
                        break;
                    case 6:
                        calculerMoyenneEtudiant(databaseManager, scanner);
                    case 7:
                        quitter = true;
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }

                System.out.println();
            }

            databaseManager.fermerConnexion();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    private static void afficherMenu() {
        System.out.println("===== Gestion des étudiants =====");
        System.out.println("1. Ajouter un étudiant");
        System.out.println("2. Modifier un étudiant");
        System.out.println("3. Supprimer un étudiant");
        System.out.println("4. Afficher tous les étudiants");
        System.out.println("5. Rechercher un étudiant par ID");
        System.out.println("6. Calculer la moyenne d'un étudiant");
        System.out.println("7. Quitter");
    }

    private static void ajouterEtudiant(StudentDatabaseManager databaseManager, Scanner scanner) throws SQLException {
        System.out.print("Entrez le prénom de l'étudiant : ");
        String prenom = scanner.nextLine();

        System.out.print("Entrez le nom de l'étudiant : ");
        String nom = scanner.nextLine();

        System.out.print("Entrez l'âge de l'étudiant : ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Ajout d'un scanner.nextLine() supplémentaire pour consommer la nouvelle ligne

        System.out.print("Entrez les notes de l'étudiant : ");
        String notes = scanner.nextLine();
        scanner.nextLine(); // Ajout d'un scanner.nextLine() supplémentaire pour consommer la nouvelle ligne

        databaseManager.ajouterEtudiant(prenom, nom, age, notes);
        System.out.println("L'étudiant a été ajouté avec succès !");
    }

    private static void modifierEtudiant(StudentDatabaseManager databaseManager, Scanner scanner) throws SQLException {
        System.out.print("Entrez l'ID de l'étudiant à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Vider la ligne après la lecture de l'entier

        System.out.print("Entrez le prénom de l'étudiant : ");
        String prenom = scanner.nextLine();

        System.out.print("Entrez le nom de l'étudiant : ");
        String nom = scanner.nextLine();

        System.out.print("Entrez l'âge de l'étudiant : ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Ajout d'un scanner.nextLine() supplémentaire pour consommer la nouvelle ligne

        System.out.print("Entrez les notes de l'étudiant : ");
        String notes = scanner.nextLine();



        databaseManager.modifierEtudiant(id, prenom, nom, age, notes);
        System.out.println("L'étudiant a été modifié avec succès !");
    }

    private static void supprimerEtudiant(StudentDatabaseManager databaseManager, Scanner scanner) throws SQLException {
        System.out.print("Entrez l'ID de l'étudiant à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Vider la ligne après la lecture de l'entier

        databaseManager.supprimerEtudiant(id);
        System.out.println("L'étudiant a été supprimé avec succès !");
    }
    private static void calculerMoyenneEtudiant(StudentDatabaseManager databaseManager, Scanner scanner) throws SQLException {
        System.out.print("Entrez l'ID de l'étudiant à calculer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Vider la ligne après la lecture de l'entier

        databaseManager.calculerMoyenneEtudiant(id);

    }
    private static void afficherTousLesEtudiants(StudentDatabaseManager databaseManager, Scanner scanner) throws SQLException {
        System.out.println("Liste de tous les étudiants :");
        databaseManager.afficherTousLesEtudiants();
        // ajout de la possibilité de demander trier les étudiants par ordre alphabétique
        System.out.println("Voulez vous trier les étudiants par ordre alphabétique ? (O/N)");
        String reponse = scanner.nextLine();
        if (reponse.equals("O")) {
            databaseManager.trierEtudiantParPrénom();
        }

    }

    private static void rechercherEtudiant(StudentDatabaseManager databaseManager, Scanner scanner) throws SQLException {
        System.out.print("Entrez l'ID de l'étudiant à rechercher : ");
        int id = scanner.nextInt();

        System.out.println("Résultat de la recherche :");
        databaseManager.rechercherEtudiant(id);
    }
}
