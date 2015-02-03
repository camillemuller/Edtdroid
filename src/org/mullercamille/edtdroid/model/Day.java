package org.mullercamille.edtdroid.model;


import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import android.util.Log;



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




	static public Day compareDay(Day unNv,Day unAncien)
	{
		Day unDay = null;
		for( Lesson laNouvelle  : unNv.getLessons()) 
		{
			for(Lesson unAncienne : unAncien.getLessons()) 
			{
				if( laNouvelle.getBegin().equals( unAncienne.getBegin()) // On cherche les cours correspondant
						&& laNouvelle.getEnd().equals(unAncienne.getEnd())  )
				{
					if(!laNouvelle.getName().equals(unAncienne.getName()))
					{
						if(unDay == null)
						unDay = new Day(unNv.getName());
						unDay.addLesson(laNouvelle);

					}
					else if(!laNouvelle.getProf().equals(unAncienne.getProf()))
					{
						
						if(unDay == null)
						unDay = new Day(unNv.getName());
						unDay.addLesson(laNouvelle);
					}

					else if(!laNouvelle.getClassroom().equals(unAncienne.getClassroom()))
					{
						if(unDay == null)
						unDay = new Day(unNv.getName());
						unDay.addLesson(laNouvelle);
					}

				}
			}
		}
		return unDay;
	}

	static public Day compareDayLessLesson(Day unNv,Day unAncien)
	{
		Day unDay = null;
		List<Lesson> lessonParsed = new ArrayList<Lesson>();
		for( Lesson laNouvelle  : unNv.getLessons()) 
		{
			for(Lesson unAncienne : unAncien.getLessons()) 
			{
				if( laNouvelle.getBegin().equals( unAncienne.getBegin()) // On cherche les cours correspondant
						&& laNouvelle.getEnd().equals(unAncienne.getEnd())  )
				{
					lessonParsed.add(unAncienne);

					if(!laNouvelle.getName().equals(unAncienne.getName()))
					{
						if(unDay == null)
						unDay = new Day(unNv.getName());
						unDay.addLesson(laNouvelle);

					}
					else if(!laNouvelle.getProf().equals(unAncienne.getProf()))
					{
						if(unDay == null)
						unDay = new Day(unNv.getName());
						unDay.addLesson(laNouvelle);
					}

					else if(!laNouvelle.getClassroom().equals(unAncienne.getClassroom()))
					{
						if(unDay == null)
						unDay = new Day(unNv.getName());
						unDay.addLesson(laNouvelle);
					}

				}
			}
		}
		
		
		
		for(Lesson uneOld : unAncien.getLessons())
		{
			if(lessonParsed.contains(uneOld))
			{	
			}else
			{
				if(unDay == null)
				unDay = new Day(uneOld.getName());
				unDay.addLesson(uneOld);
			}
		}

		return unDay;
	}





	/**
	 * 
	 * @param olds
	 * @param news
	 * @return
	 */
	static public List<Day> compare( List<Day> olds, List<Day> news)
	{
		List<Day> dayChanged = new ArrayList<Day>();
		for(Day unAncien: olds)
		{
			for(Day unNv : news)
			{
				if(unAncien.getName().equals(unNv.getName()))  // Si même jour 
				{
					// Cours en plus 
					if( olds.size() > news.size()) // Cours en moins
					{
						Day tempDay = compareDayLessLesson(unNv,unAncien);
						if(tempDay != null) // Si il y a des cours de changées
						{
							dayChanged.add(tempDay);
						}

					}
					else if( olds.size() < news.size() ) // Cours en plus
					{

					}
					else // On doit vérifier les changements de cours
					{
						Day tempDay = compareDay(unNv,unAncien);
						if(tempDay != null) // Si il y a des cours de changées
						{
							dayChanged.add(tempDay);
						}

					}



				}

			}
		}
		return dayChanged;
	}


}
