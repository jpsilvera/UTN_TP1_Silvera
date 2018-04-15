package trabajoPractico1;

import java.io.*;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TrabajoPractico1
{
    public static void main(String [] args) throws IOException 
    {
        List<Student> studentsList = new ArrayList<>();
        float studentsAverageScore = 0;
        int scoreAboveFourCounter = 0;
        int rankingPosition = 1;
        float studentCounter = 0;
    	int errorFlag = 0;
    	int whileCounter = 0;
        
        int maleCounter = 0;
        int femaleCounter = 0;
        float maleTotalAttendance = 0;
        float femaleTotalAttendance = 0;
        
        int unapprovedStudent = 0;
   
        try (PrintWriter badLineWriter = new PrintWriter("C:/UTN - TP 1 Java/Usuarios con error.bad"))
        {  	
        	FileReader fileLocation = new FileReader("C:/UTN - TP 1 Java/Archivo de calificaciones.txt");
            BufferedReader br = new BufferedReader(fileLocation);
            		
            String fileRead = br.readLine();
         
            
            while (fileRead != null)
            {
                String[] SplitPosition = fileRead.split(",");
                whileCounter++;
                fileRead = br.readLine();
                
                try 
                {
                    int tempFirstTestScore = Integer.parseInt(SplitPosition[3].trim());
                    int tempSecondTestScore = Integer.parseInt(SplitPosition[4].trim());
                    float tempAttendancePercent = Float.parseFloat(SplitPosition[5].trim());
                    
                }catch (NumberFormatException e)
                {
        			System.out.println("Se encontraron errores en la linea: " + whileCounter);
        			System.out.println("Por favor revise que no queden espacios vacios entre los datos separados por coma.");
        			System.out.println("La linea que contiene errores es: " + SplitPosition[0].trim() +","+ SplitPosition[1].trim() +","+ SplitPosition[2] +","+ SplitPosition[3].trim() +","+ SplitPosition[4].trim() +","+ SplitPosition[5].trim());
        			System.out.println("");
        			badLineWriter.println(SplitPosition[0].trim() +","+ SplitPosition[1].trim() +","+ SplitPosition[2] +","+ SplitPosition[3].trim() +","+ SplitPosition[4].trim() +","+ SplitPosition[5].trim());
                } 
                
                int tempFirstTestScore = Integer.parseInt(SplitPosition[3].trim());
                int tempSecondTestScore = Integer.parseInt(SplitPosition[4].trim());
                float tempAttendancePercent = Float.parseFloat(SplitPosition[5].trim());
                
                Student tempStudent = new Student(SplitPosition[0].trim(), SplitPosition[1].trim(), SplitPosition[2], tempFirstTestScore, tempSecondTestScore, tempAttendancePercent);             
                studentsList.add(tempStudent);          
            }
            
            for(int i = 0; i < studentsList.size(); i++) 
			{	  
            	Student student = studentsList.get(i);
            	String nameWithoutWhitespace = student.getName();
            	int positionCounter = 0;
				
				nameWithoutWhitespace = nameWithoutWhitespace.replaceAll("\\s", "");
				Pattern allowedCharacters = Pattern.compile("[^A-Za-z]");
				
            	//Verificación de campo DNI, Name, Scores y asistencia
				if (student.getAttendancePercent() < 0 || student.getAttendancePercent() > 100) 
				{
            		errorFlag = exceptionErrorAndPrintToBadFile(errorFlag, badLineWriter, i, student);
				}
				
            	if (verifyDniValue(student) || checkCharacters(nameWithoutWhitespace, allowedCharacters) || student.getName().isEmpty() || student.getGender().length() != 1 || student.getGender() == null || checkGenderCharacters(student) || student.getFirstTestScore() < 1 || student.getFirstTestScore() > 10 || student.getSecondTestScore() < 1 || student.getSecondTestScore() > 10)
            	{
            		errorFlag = exceptionErrorAndPrintToBadFile(errorFlag, badLineWriter, i, student);
        		
            	}else if (checkForErrorFlags(studentsList, errorFlag, i)) 
            	{
            		throw new RuntimeException("El log de los errores se encuentra en la ruta: C:/UTN - TP 1 Java/Usuarios con error.bad");
				}
			}
            
            studentsList.sort(Comparator.comparing(Student::getAverageScore).reversed());
            //2.1 Los nombres de los 5 mejores promedios (indicar dni, nombre y nota)
            for(int i = 0; i < 5; i++) 
            {
            	Student student = studentsList.get(i);
            	
	            System.out.println("Puesto en el ranking de promedios: " + rankingPosition);
	            System.out.println("Promedio general: " + student.getAverageScore());
	            System.out.println("DNI: " + student.getDNI() + " | Nombre: " + student.getName() + " | " + "Primera nota: "+ student.getFirstTestScore() + " | " + "Segunda nota: " + student.getSecondTestScore());
	            rankingPosition++;
            }
            
            //2.2 El promedio general del curso
            //2.4 El promedio de asistencia separado por genero
			for(int i = 0; i < studentsList.size(); i++) 
			{	
				Student student = studentsList.get(i);
				studentCounter = studentsList.size();
				studentsAverageScore += student.getAverageScore();
				
				if (isMale(student))
				{
					maleCounter++;
					maleTotalAttendance +=  student.getAttendancePercent();
				}
				
				if (isFemale(student))
				{
			        femaleCounter++;
			        femaleTotalAttendance += student.getAttendancePercent();
				}
				
				//2.3 La cantidad de aprobados (Con ambas notas mayores a 4)
				if (isTheScoreAboveFour(student))
				{
					scoreAboveFourCounter++;
				}
				
				//2.5 Cantidad de desaprobados con asistencia mayor al 75%
				if (isTheStudentUnapproved(student)  && student.getAttendancePercent() >= 75)
				{
					unapprovedStudent++;
				}		
				
				if (isItTheEndOfTheList(studentsList, i))
				{					
					femaleTotalAttendance /= femaleCounter;
					maleTotalAttendance /= maleCounter;

					System.out.println("=================================================================================");
					System.out.println("El promedio general del curso es: " + (studentsAverageScore / studentCounter));
					System.out.println("=================================================================================");
					System.out.println("La cantidad de alumnos aprobados con más de 4 en ambas notas es: " + scoreAboveFourCounter);
					System.out.println("=================================================================================");
					System.out.println("El promedio de asistencia por Genero es:");
					System.out.println("Genero Masculino: " + maleTotalAttendance +"%");
					System.out.println("Genero Femenino: " + femaleTotalAttendance +"%");
					System.out.println("=================================================================================");
					System.out.println("La cantidad de alumnos desaprobados con el 75% de asistencia es: " + unapprovedStudent);
				}
			}
            
            br.close();
        }
        
        catch (FileNotFoundException fnfe)
        {
            System.out.println("No se encontró el archivo.");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

	private static int exceptionErrorAndPrintToBadFile(int errorFlag, PrintWriter badLineWriter, int i,
			Student student) {
		int positionCounter;
		positionCounter = i + 1;
		printBadFileToLog(badLineWriter, student);
		System.out.println("Se encontraron errores en la linea: " + positionCounter);
		System.out.println("Por favor revise que los datos ingresados. El formato de los datos debe ser: DNI(8 digitos), Nombre y Apellido(Solo caracteres alfanuméricos), Genero(M o F), Primera nota(1 al 10), Segunda nota(1 al 10), Asistencia(0 al 100).");
		System.out.println("Los datos de la linea con error son: " + student.getDNI() + "," + student.getName() + "," + student.getGender() + ","  + student.getFirstTestScore() + ","  + student.getSecondTestScore() + ","  + student.getAttendancePercent());
		System.out.println("");
		errorFlag++;
		return errorFlag;
	}

	private static boolean checkGenderCharacters(Student student) 
	{
		return !student.getGender().matches("^[m-mM-Mf-fF-F]*$") || student.getGender() == null;
	}

	private static boolean checkCharacters(String nameWithoutWhitespace, Pattern allowedCharacters) 
	{
		return allowedCharacters.matcher(nameWithoutWhitespace).find();
	}

	private static void printBadFileToLog(PrintWriter badLineWriter, Student student) 
	{
		badLineWriter.println((String) student.getDNI() + "," + student.getName() + "," + student.getGender() + ","  + student.getFirstTestScore() + ","  + student.getSecondTestScore() + ","  + student.getAttendancePercent());
	}

	private static boolean checkForErrorFlags(List<Student> studentsList, int errorFlag, int i) 
	{
		return i >= (studentsList.size() - 1) && errorFlag > 0;
	}

	private static boolean verifyDniValue(Student student) {
		return student.getDNI().length() != 8 || !student.getDNI().matches("[0-9]+") || student.getDNI() == null;
	}

	private static boolean isItTheEndOfTheList(List<Student> studentsList, int i) 
	{
		return i == studentsList.size()-1;
	}

	private static boolean isTheStudentUnapproved(Student student) 
	{
		return student.getFirstTestScore() < 4 || student.getSecondTestScore() < 4;
	}

	private static boolean isTheScoreAboveFour(Student student) 
	{
		return student.getFirstTestScore() >= 4 && student.getSecondTestScore() >= 4;
	}

	private static boolean isFemale(Student student) 
	{
		return student.getGender().equals("F") || student.getGender().equals("f");
	}

	private static boolean isMale(Student student)
	{
		return student.getGender().equals("M") || student.getGender().equals("m");
	}
}