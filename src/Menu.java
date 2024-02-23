import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    int choix;
    Admin admin = new Admin();
    User user = new User();
    public static Scanner scanner = new Scanner(System.in);
    public void menuPrincipal() throws SQLException, ClassNotFoundException {
        System.out.println("--------------------- Menu principal ----------------------");
        System.out.println("\t1- Admin ");
        System.out.println("\t2- Utilisateur ");
        System.out.println("-----------------------------------------------------------");
        System.out.print("CHOISISSEZ : ");choix=scanner.nextInt();
        choiceMenuprincipal(choix);
    }
    public void choiceMenuprincipal(int choix) throws SQLException, ClassNotFoundException {
        switch (choix)
        {
            case 1 :
                menuAdmin();
                break;
            case 2 :
                menuUser();
                break;
            default:
                System.out.println("CHOIX INVALID !! Ressayer..");
                menuPrincipal();
                break;
        }
    }
    public void menuAdmin() throws SQLException, ClassNotFoundException {
        System.out.println("--------------------- Menu ADMIN ----------------------");
        System.out.println("\t1- AJOUTER UNE VILLE  ");
        System.out.println("\t2- MODIFIER UNE VILLE ");
        System.out.println("\t3- SUPPRIMER UNE VILLE ");
        System.out.println("\t4- AJOUTER UNE HISTORIQUE ");
        System.out.println("\t5- MODIFIER UNE HISTORIQUE  ");
        System.out.println("\t6- SUPPRIMER UNE HISTORIQUE ");
        System.out.println("\t7- Retour au menu principal");
        System.out.println("--------------------------------------------------------");
        System.out.print("CHOISISSEZ : ");choix=scanner.nextInt();
        choiceMenuAdmin(choix);
    }
    public void choiceMenuAdmin(int choix) throws SQLException, ClassNotFoundException {
        switch (choix)
        {
            case 1 :
                if(!admin.addCity(Connecter.getconnection()))
                {
                   menuAdmin();
                }
                user.showAllCity(Connecter.getconnection());
                menuAdmin();
                break;
            case 2 :
                if(!admin.updateCity(Connecter.getconnection())){
                    menuAdmin();
                }
                user.showAllCity(Connecter.getconnection());
                menuAdmin();
                break;
            case 3 :
                if(!admin.deleteCity(Connecter.getconnection())){
                    menuAdmin();
                }
                user.showAllCity(Connecter.getconnection());
                menuAdmin();
                break;
            case 4 :
                admin.addHistory(Connecter.getconnection());
                menuAdmin();
                break;
            case 5 :
                if(!admin.updateHistory(Connecter.getconnection())){
                    menuAdmin();
                }
                menuAdmin();
                break;
            case 6 :
                if(!admin.deleteHistory(Connecter.getconnection())){
                    menuAdmin();
                }
                menuAdmin();
                break;
            case 7 :
                menuPrincipal();
                break;
            default:
                System.out.println("CHOIX INVALID !! Ressayer..");
                menuAdmin();
                break;
        }
    }
    public void menuUser() throws SQLException, ClassNotFoundException {
        System.out.println("------------------------------- Menu USER --------------------------------");
        System.out.println("\t1- AFFICHER LES VILLES ");
        System.out.println("\t2- RECHERCHER UNE VILLE ");
        System.out.println("\t3- AFFICHER LES PREVISION METEOROLOGIQUES ACTUELLES D UNE VILLE ");
        System.out.println("\t4- AFFICHER L HISTORIQUE METEOROLOGIQUES D UNE VILLE ");
        System.out.println("\t5- RETOUR AU MENU PRINCIPAL ");
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("CHOISISSEZ : ");choix=scanner.nextInt();
        choiceMenuUser(choix);
    }
    public void choiceMenuUser(int choix) throws SQLException, ClassNotFoundException {
        switch (choix)
        {
            case 1 :
                user.showAllCity(Connecter.getconnection());
                menuUser();
                break;
            case 2 :
                if(!user.searchCity(Connecter.getconnection())){
                    menuUser();
                }
                menuUser();
                break;
            case 3 :
                if(!user.temperatureNowOfCity(Connecter.getconnection())){
                    menuUser();
                }
                menuUser();
                break;
            case 4 :
                if(!user.historyOfCity(Connecter.getconnection())){
                    menuUser();
                }
                menuUser();
                break;
            case 5 :
                menuPrincipal();
                break;
            default:
                System.out.println("CHOIX INVALID !! Ressayer..");
                menuUser();
                break;
        }
    }
}
