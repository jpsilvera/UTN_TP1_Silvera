package trabajoPractico1;

public class Student
{
	private String DNI;
	private String name;
	private String gender;
	private int firstTestScore;
	private int secondTestScore;
	private float attendancePercent;
	private float averageScore;

	public Student(String DNI, String name, String gender, int firstTestScore, int secondTestScore, float attendancePercent)
    {
        this.DNI = DNI;
        this.name = name;
        this.gender = gender;
        this.firstTestScore = firstTestScore;
        this.secondTestScore = secondTestScore;
        this.attendancePercent = attendancePercent;
        
    }

	public float getAverageScore() 
	{
		return this.averageScore = (this.firstTestScore + this.secondTestScore) / 2;
	}	
	
	public String getDNI() 
	{
			return DNI;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public String getGender() 
	{
		return gender;
	}
	
	public int getFirstTestScore() 
	{
		return firstTestScore;
	}
	
	public int getSecondTestScore() 
	{
		return secondTestScore;
	}
	
	public float getAttendancePercent() 
	{
		return attendancePercent;
	}
}
