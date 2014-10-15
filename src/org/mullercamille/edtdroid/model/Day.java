package org.mullercamille.edtdroid.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



public class Day implements Iterable<Lesson> {
	
	//Solution de gitan
	//public static Day leJourEnCours;
	
	private List<Lesson> lessons;
	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	private String name;

	public Day(String name) {
		this.lessons = new ArrayList<Lesson>();
		this.name = name;
	/*	
		//Si le jour est le jour d'aujourd'hui
		SimpleDateFormat formater = new SimpleDateFormat("EEEE, d MMM");
		Date date = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 date = dateFormat.parse(this.name);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( formater.format(date).equals(  formater.format(new Date())   ) )
		{
		 leJourEnCours = this;
		}		
		*/
		
	}

	public void addLesson(Lesson lesson) {
		this.lessons.add(lesson);
	}

	@Override
	public Iterator<Lesson> iterator() {
		return this.lessons.iterator();
	}

	public Lesson getLesson(int i) {
		return this.lessons.get(i);
	}

	public String getName() {
		return this.name;
	}

	public int getNumber() {

		return Integer.parseInt(this.name.substring(8,10));
	}

	public int size() {
		return this.lessons.size();
	}

	public void clear() {
		this.lessons.clear();
	}

	/**
	 * A finirs -> Implemant des lesson
	 * @param olds
	 * @param news
	 * @return
	 */
	static public List<Day> compare( List<Day> olds, List<Day> news)
	{
		List<Day> dayChanged = new ArrayList<Day>();

		//List<String> gasList = // create list with duplicates...
			//	Set<String>  uniqueGas = new HashSet<String>(gasList);
		//System.out.println("Unique gas count: " + uniqueGas.size());

		for(Day unAncien: olds)
		{
			for(Day unNv : news)
			{
				if(unAncien.getName().equals(unNv.getName()))  // Si même jour 
				{
					for( Lesson laNouvelle  : unNv.getLessons()) 
					{
						for(Lesson unAncienne : unAncien.getLessons()) 
						{
							
							// Manque le cas d'un nouveau cours rajouté 
							// bug si changement de groupe
							if( laNouvelle.getBegin().equals( unAncienne.getBegin()) // On cherche les cours correspondant
									&& laNouvelle.getEnd().equals(unAncienne.getEnd())  )
							{

								if(!laNouvelle.getName().equals(unAncienne.getName()))
								{
									Day unDay = new Day(unNv.getName());
									unDay.addLesson(laNouvelle);
									dayChanged.add(unDay);

								}
								if(!laNouvelle.getProf().equals(unAncienne.getProf()))
								{
									Day unDay = new Day(unNv.getName());
									unDay.addLesson(laNouvelle);
									dayChanged.add(unDay);
								}

								if(!laNouvelle.getClassroom().equals(unAncienne.getClassroom()))
								{
									Day unDay = new Day(unNv.getName());
									unDay.addLesson(laNouvelle);
									dayChanged.add(unDay);
								}

							}
						}
					}
				}

			}
		}


		return dayChanged;

	}

}
