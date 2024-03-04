package es.uv.prnr.p2;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

public class ProjectService {
	EntityManagerFactory emf;
	EntityManager em;

	public ProjectService() {
		this.emf = Persistence.createEntityManagerFactory("acmeEmployees");
		this.em = emf.createEntityManager();
	}

	/**
	 * TODO
	 * Busca un departamento
	 * 
	 * @param id identificador del departamento
	 * @return entidad con el deparamenteo encontrado
	 */
	public Department getDepartmentById(String id) {
		Department dep = this.em.find(Department.class, id);
		return dep;
	}

	/**
	 * TODO
	 * Asciende a un empleado a manager. Utilizar una estrateg�a de herencia
	 * adecuada
	 * en employee. Tened en cuenta que NO puede haber dos entidades con el mismo id
	 * por lo que habr� que eliminar el empleado original en algun momento.
	 * 
	 * @param employeeId
	 * @param bonus
	 * @return
	 */
	public Manager promoteToManager(int employeeId, long bonus) {
		em.getTransaction().begin();
		Employee emp = this.em.find(Employee.class, employeeId);
		em.remove(emp);
		em.flush();

		Manager mng = new Manager(emp, bonus);

		em.persist(mng);
		em.getTransaction().commit();
		return mng;
	}

	/**
	 * TODO
	 * Crea un nuevo proyecto en el area de Big Data que comienza en la fecha actual
	 * y que finalizax
	 * en 3 a�os.
	 * 
	 * @param name
	 * @param d      departamento asignado al proyecto
	 * @param m      manager que asignado al proyecto
	 * @param budget
	 * @return el proyecto creado
	 */
	public Project createBigDataProject(String name, Department d, Manager m, BigDecimal budget) {
		Calendar calNow = Calendar.getInstance();
		LocalDate currentDate = calNow.getTime().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		;

		Calendar calLater = Calendar.getInstance();
		calLater.add(Calendar.YEAR, 3);
		LocalDate nextYear = calLater.getTime().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		;
		
		Project project = new Project(name, d, m, budget, currentDate, nextYear, null);

		em.persist(project);
		return null;
	}

	/**
	 * TODO
	 * Crea un equipo de proyecto. Se debera implementa el m�todo addEmployee de
	 * Project para incluir los empleados
	 * 
	 * @param p       proyecto al cual asignar el equipo
	 * @param startId identificador a partir del cual se asignan empleado
	 * @param endId   identificador final de empleados. Se asume que start id <
	 *                endId
	 */
	public void assignTeam(Project p, int startId, int endId) {

	}

	/**
	 * TODO
	 * Genera un conjunto de horas inicial para cada empleado. El m�todo asigna para
	 * cada
	 * mes de duraci�n del proyecto, un n�mero entre 10-165 de horas a cada
	 * empleado.
	 * 
	 * @param projectId
	 * @return total de horas generadas para el proyecto
	 */
	public int assignInitialHours(int projectId) {
		int totalHours = 0;
		// TODO Buscar proyecto
		Project p = null;
		LocalDate start = p.getStartDate();
		while (start.isBefore(p.getEndDate())) {
			for (Employee e : p.getEmployees()) {
				int hours = new Random().nextInt(165) + 10;
				totalHours += hours;
				// TODO Agregar las horas del empleado al proyecto
			}
			start = start.plusMonths(1);
		}
		// TODO guardar resultados
		return totalHours;
	}

	/**
	 * TODO
	 * Busca si un empleado se encuentra asignado en el proyecto utilizando la
	 * namedQuery Project.findEmployee
	 * 
	 * @param projectId
	 * @param firstName
	 * @param lastName
	 * @return cierto si se encuentra asignado al proyecto
	 */
	public boolean employeeInProject(int projectId, String firstName, String lastName) {
		return false;
	}

	/**
	 * TODO
	 * Devuelve los meses con mayor n�mero de horas de un a�o determinado
	 * utilizando la namedQuery Project.getTopMonths
	 * 
	 * @param projectId
	 * @param year      a�o a seleccionar
	 * @param rank      n�mero de meses a mostrar, se asume que rank <= 12
	 * @return una lista de objetos mes,hora ordenados de mayor a menor
	 */
	public List getTopHourMonths(int projectId, int year, int rank) {
		return null;
	}

	/**
	 * TODO
	 * Devuelve para cada par mes-a�o el presupuesto teniendo en cuenta el
	 * coste/hora de los empleados asociado utilizando la namedQuery
	 * Project.getMonthlyBudget
	 * que realiza una consulta nativa
	 * 
	 * @param projectId
	 * @return una colecci�n de objetos MonthlyBudget
	 */
	public List<MonthlyBudget> getMonthlyBudget(int projectId) {
		return null;
	}

}
