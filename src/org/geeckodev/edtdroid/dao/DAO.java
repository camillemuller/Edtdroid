package org.geeckodev.edtdroid.dao;

import java.io.IOException;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.geeckodev.edtdroid.model.Day;
import org.geeckodev.edtdroid.model.Department;
import org.geeckodev.edtdroid.model.Establishment;
import org.geeckodev.edtdroid.model.Lesson;
import org.geeckodev.edtdroid.model.Group;
import org.geeckodev.edtdroid.model.Model;
import android.annotation.SuppressLint;

public class DAO {
	private final String user = "appli";
	private final String motdepasse = "XpWVmpdZDFatswaL";
	private final String port = "443";
	private final String catalog = "edt";
	private final String ip = "195.154.104.118";
	private Model model;
	private Connection conexionMySQL;

	private java.sql.Connection dbConnect = null;
	private java.sql.Statement dbStatement = null;



	public DAO(Model model) {
		/* Use ThreadSafeClientConnManager to avoid crashes on Android 2.x */

		this.model = model;
	}



	public void connectBDD () throws  IllegalAccessException
	{
		if (conexionMySQL == null)    	
		{
			//String urlConexionMySQL = "";


			String	urlConexionMySQL = "jdbc:mysql://" + ip + ":" +	port + "/" + catalog;

			if (user != "" & motdepasse != "" & ip != "" & port != "")
			{
				try 
				{

					Class.forName("com.mysql.jdbc.Driver").newInstance();
					dbConnect =	DriverManager.getConnection(urlConexionMySQL, 
							user, motdepasse);		
					this.dbStatement = this.dbConnect.createStatement();

				} 
				catch (ClassNotFoundException and) 
				{
					and.printStackTrace();
				} 
				catch (SQLException And) 
				{
					And.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}





	//TODO 
	/**
	 * This is for the moment, just Polytech who is handle.
	 * 
	 * @param estts
	 * @throws IOException
	 */
	public void findEstablishments(List<Establishment> estts)
			throws IOException {
		estts.clear();
		estts.add(new Establishment("POLYTECH'TOURS", "POLYTECH'TOURS"));

	}

	public void findDepartments(String estt, List<Department> depts)
			throws IOException {

		depts.clear();


		ResultSet rs = null;
		try {

			this.connectBDD();
			rs = this.dbStatement.executeQuery("SELECT DISTINCT NAME"
					+" FROM Groupe"
					+" WHERE name LIKE  'DI%'"
					+" AND LENGTH(name) <7	");
			while(rs.next())
			{
				depts.add(new Department(rs.getString("name"), rs.getString("name")));
			}


			depts.add(new Department("DI_5A_S9","DI_5A_S09"));

		}catch(SQLException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e)
		{
			e.printStackTrace();
		}


	}

	public void findGroups(String estt, String dept, List<Group> groups)
			throws IOException {

		groups.clear();
		ResultSet rs = null;
		try {
			this.connectBDD();
			rs = this.dbStatement.executeQuery("SELECT DISTINCT NAME FROM Groupe"
					+" WHERE name like '%"+dept+"%' " 
					+" AND LENGTH(name) > 6 "); // Retourne groups of dept 
			while(rs.next())
			{
				if(!rs.getString("name").equals(dept))
					groups.add(new Group(rs.getString("name"), rs.getString("name")));
			}
			
			if(dept.contains("DI_5A"))
			{
			groups.add(new Group("DI_5A_G1","DI_5A_G1"));
			groups.add(new Group("DI_5A_G2","DI_5A_G2"));
			}

		}catch(SQLException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e)
		{
			e.printStackTrace();	
		}



	}

	@SuppressLint("SimpleDateFormat")
	public void findDays(String estt, String dept, String group, List<Day> days)
			throws IOException {


		Day curr_day = null;




		days.clear();

		String format = "yyyy-MM"; 

		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format ); 
		java.util.Date date = new java.util.Date(); 



		ResultSet rs = null;
		try {

			this.connectBDD();


			String sqlQuery;

			sqlQuery=	"SELECT * FROM   Cours, Groupe	 WHERE " +
					"DATEDEBUT like '%"+formater.format( date )+"%'" 
					+" AND (Groupe.name like  '%"+dept+"'" // Cours commun 
					+" OR Groupe.name like  '%"+group+"%')" // Cours en groupe 
					+" AND Cours.id_groupe = Groupe.id_groupe " +
					" ORDER BY DATEDEBUT ASC";



			rs = this.dbStatement.executeQuery(sqlQuery);

			rs.next();
			String uneDate = rs.getString("DATEDEBUT");
			curr_day = new Day(uneDate.substring(0,10));




			Lesson fistLesson =  new Lesson(this.model, rs.getString("DATEDEBUT"), 
					rs.getString("dateFin"), rs.getString("Groupe.name"),
					rs.getString("Cours.name"),rs.getString("prof").replaceAll("<br/>", "-"),
					rs.getString("salle").replaceAll("<br/>", "-"));




			curr_day.addLesson(fistLesson);


			while(rs.next())
			{	

				Lesson unLesson =  new Lesson(this.model, rs.getString("DATEDEBUT"), 
						rs.getString("dateFin"), rs.getString("Groupe.name"),
						rs.getString("Cours.name"),rs.getString("prof").replaceAll("<br/>", "-"),
						rs.getString("salle").replaceAll("<br/>", "-"));

				if(! rs.getString("DATEDEBUT").substring(0, 10).equals(  uneDate.substring(0, 10) ))
				{   
					days.add(curr_day);
					curr_day = new Day(rs.getString("DATEDEBUT").substring(0,10));
					curr_day.addLesson(unLesson);

				}
				else
				{

					curr_day.addLesson(unLesson);
				}

				uneDate = rs.getString("DATEDEBUT");
			}

			//return oldDays;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



	}
}
