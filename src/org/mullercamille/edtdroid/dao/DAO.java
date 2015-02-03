package org.mullercamille.edtdroid.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mullercamille.edtdroid.model.Day;
import org.mullercamille.edtdroid.model.Department;
import org.mullercamille.edtdroid.model.Establishment;
import org.mullercamille.edtdroid.model.Group;
import org.mullercamille.edtdroid.model.Lesson;
import org.mullercamille.edtdroid.model.Model;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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
		this.model = model;
	}

	public void connectBDD () throws  IllegalAccessException, IOException
	{
		

		if (conexionMySQL == null)    	
		{
			String	urlConexionMySQL = "jdbc:mysql://" + ip + ":" +	port + "/" + catalog;
			if (user != "" & motdepasse != "" & ip != "" & port != "")
			{
				try 
				{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					dbConnect =	DriverManager.getConnection(urlConexionMySQL, 
							user, motdepasse);		
					this.dbStatement = this.dbConnect.createStatement();
					
					if(this.dbConnect.isClosed())
					{
						Log.d("DEBUG","La connexion est planté");
					}else
					{
						Log.d("DEBUG","La connexion est ouverte");
					}
					
				} 
				catch (ClassNotFoundException and) 
				{
					and.printStackTrace();
					throw new IOException();
				} 
				catch (SQLException And) 
				{
					And.printStackTrace();
					throw new IOException();

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IOException();
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

		depts.add(new Department("DII_3A","DII_3A"));
		depts.add(new Department("DII_4A","DII_4A"));
		depts.add(new Department("DII_5A","DII_5A"));

		depts.add(new Department("DI_5A_S9","DI_5A_S09"));
		depts.add(new Department("DI_5A_S10","DI_5A_S10"));


		depts.add(new Department("DI_4A_S7","DI_4A_S7"));
		depts.add(new Department("DI_4A_S8","DI_4A_S8"));

		depts.add(new Department("DI_3A_S5","DI_3A_S5"));
		depts.add(new Department("DI_3A_S6","DI_3A_S6"));

		depts.add(new Department("3A_Elec","3A_Elec"));
		depts.add(new Department("4A_Elec","4A_Elec"));
		depts.add(new Department("5A_Elec","5A_Elec"));


		depts.add(new Department("3A_Méca","3A_Méca"));
		depts.add(new Department("4A_Méca","4A_Méca"));
		depts.add(new Department("5A_Méca","5A_Méca"));

		depts.add(new Department("PeiP1 S1","PeiP S1"));
	}

	public void findGroups(String estt, String dept, List<Group> groups)
			throws IOException {

		groups.clear();


		if(dept.contains("DII_4A"))
		{
			groups.add(new Group("DII_4A_G1","DII_4A_G1"));
			groups.add(new Group("DII_4A_G2","DII_4A_G2"));	
		}

		if(dept.contains("DI_5A_S09"))
		{
			groups.add(new Group("DI_5A_G1","DI_5A_G1"));
			groups.add(new Group("DI_5A_G2","DI_5A_G2"));
		}
		if(dept.contains("DI_5A_S10"))
		{
			groups.add(new Group("DI_5A Option L&O","DI_5A Option L&O"));
			groups.add(new Group("DI_5A Option RV","DI_5A Option RV"));
			groups.add(new Group("DI_5A Option S&H","DI_5A Option S&H"));
			groups.add(new Group("DI_5A Option WM","DI_5A Option WM"));


		}

		if(dept.contains("DI_4A"))
		{
			groups.add(new Group("DI_4A_G1","DI_4A_G1"));
			groups.add(new Group("DI_4A_G2","DI_4A_G2"));
			groups.add(new Group("DI_4A_G3","DI_4A_G3"));
		}
		if(dept.contains("DI_3A"))
		{
			groups.add(new Group("DI_3A_G1","DI_3A_G1"));
			groups.add(new Group("DI_3A_G2","DI_3A_G2"));
		}

		if(dept.contains("3A_Elec"))
		{
			groups.add(new Group("3A_E1","3A_E1"));
			groups.add(new Group("3A_E2","3A_E2"));
		}

		if(dept.contains("4A_Elec"))
		{
			groups.add(new Group("4A_E1","4A_E1"));
			groups.add(new Group("4A_E2","4A_E2"));
			groups.add(new Group("4A_E3","4A_E3"));
		}


		if(dept.contains("5A_Elec"))
		{
			groups.add(new Group("5A_E1","5A_E1"));
			groups.add(new Group("5A_E2","5A_E2"));
		}

		if(dept.contains("3A_Méca"))
		{
			groups.add(new Group("3A_M1","3A_M1"));
			groups.add(new Group("3A_M2","3A_M2"));
			groups.add(new Group("3A_M3","3A_M3"));
			groups.add(new Group("3A_M4","3A_M4"));
		}
		if(dept.contains("4A_Méca"))
		{
			groups.add(new Group("4A_M1","4A_M1"));
			groups.add(new Group("4A_M2","4A_M2"));
			groups.add(new Group("4A_M3","4A_M3"));
			groups.add(new Group("4A_M4","4A_M4"));
		}

		if(dept.contains("5A_Méca"))
		{
			groups.add(new Group("5A_M1","5A_M1"));
			groups.add(new Group("5A_M2","5A_M2"));
			groups.add(new Group("5A_M3","5A_M3"));
			groups.add(new Group("5A_M4","5A_M4"));
		}

		if(dept.contains("PeiP1 S1"))
		{
			groups.add(new Group("PeiP1 Groupe 1","PeiP1 Groupe 1"));
			groups.add(new Group("PeiP1 Groupe 2","PeiP1 Groupe 2"));
			groups.add(new Group("PeiP1 Groupe 3","PeiP1 Groupe 3"));
			groups.add(new Group("PeiP1 Groupe 4","PeiP1 Groupe 4"));
			groups.add(new Group("PeiP1 Groupe 5","PeiP1 Groupe 5"));
			groups.add(new Group("PeiP1 Groupe 6","PeiP1 Groupe 6"));
			groups.add(new Group("PeiP1 Groupe 7","PeiP1 Groupe 7"));
			groups.add(new Group("PeiP1 Groupe 8","PeiP1 Groupe 8"));
		}

	}
	
	@SuppressLint("SimpleDateFormat")
	public void findDays(String estt, String dept, String group, List<Day> days)
			throws IOException {
		Day curr_day = null;




		days.clear();

		//TODO modification de la récuperation des dates par between
		/*	String format = "yyyy-MM-dd"; 

		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format ); 
		java.util.Date date = new java.util.Date(); 

		java.util.Date date2 = new java.util.Date(date.getTime()+86400000*7); 

		 */		

		ResultSet rs = null;
		try {

			this.connectBDD();


			String sqlQuery;

			sqlQuery=	"SELECT * FROM   Cours, Groupe	 WHERE " //+
					//"SUBSTRING(DATEDEBUT,0,10) BETWEEN '"+formater.format( date )+"' AND '" +formater.format( date2 )+"'"
					+"  (Groupe.name like  '%"+dept+"'" // Cours commun 
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
			throw new IOException();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new IOException();
		}
	}
}
