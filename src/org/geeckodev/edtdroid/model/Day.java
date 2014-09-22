package org.geeckodev.edtdroid.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Day implements Iterable<Lesson> {
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

	/**
	 * A finirs -> Implemant des lesson
	 * @param olds
	 * @param news
	 * @return
	 */
	static public List<Lesson> compare( List<Day> olds, List<Day> news)
	{
		List<Lesson> dayChanged = new ArrayList<Lesson>();



		for(Day unAncien: olds)
		{
			for(Day unNv : news)
			{
				if(unAncien.getName() == unAncien.getName()) 
				{
					for( Lesson unAncienne  : unAncien.getLessons()) //4
					{
						for(Lesson laNouvelle : unNv.getLessons()) // 4 
						{
							if( laNouvelle.getBegin() == unAncienne.getBegin() && laNouvelle.getEnd() == unAncienne.getEnd()  )
							{
								//TODO A REVOIR
								if(laNouvelle.getName() != unAncienne.getName())
								{
									dayChanged.add(laNouvelle);
									
								}
								if(laNouvelle.getProf() != unAncienne.getProf())
								{
									dayChanged.add(laNouvelle);
								}
								
								if(laNouvelle.getClassroom() != unAncienne.getClassroom())
								{
									dayChanged.add(laNouvelle);
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
