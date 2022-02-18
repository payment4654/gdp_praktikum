import java.util.List;

import gmbh.kdb.hsw.gdp.Game;
import gmbh.kdb.hsw.gdp.domain.Project;

public class ProjectAdapter {

    static Game gameInstance;

    

    // Überprüfung nach laufenden Projekten aus printProjectData ausgelagert
    public static void checkForRunningProjects(List<Project> projects,Game gameInstance) {
        ProjectAdapter.gameInstance = gameInstance;
        if (projects.size() == 0) {
            printNoRunningProjects();
        } else {
            printProjectInfo(projects);
        }
    }

    // Ausgabe bei keinen laufenden Projekten aus checkForRunningProjects
    // ausgelagert
    private static void printNoRunningProjects() {
        System.out.println("       Keine laufenden Projekte \n");
    }

    // Ausgaben für jedes laufende Projekt aus checkForRunningProjects ausgelagert
    private static void printProjectInfo(List<Project> projects) {
        for (Project project : projects) {
            printProjectName(project);
            printDaysLeftForProject(project);
        }
    }

    // Ausgabe Projektname aus printProjectInfo ausgalagert
    private static void printProjectName(Project project) {
        System.out.println("       Projekt: " + project.getName().getName());
    }

    // Ausgabe Tage übrig für Projekt aus printProjectInfo ausgalagert
    private static void printDaysLeftForProject(Project project) {
        String daysLeft = Integer
                .toString(project.getDeadline().getNumber() - gameInstance.getDay().getNumber());
        System.out.println("          Tage übrig: " + daysLeft);
    }
}
