package io.webBack.ppmtool.Exception;

public class ProjectNeverFoundExceptionResponse {

    private String Project_Not_Founds;

    public ProjectNeverFoundExceptionResponse() {
    	
    }
    public ProjectNeverFoundExceptionResponse(String project_Not_Found) {
    	System.out.println("inside response");
        Project_Not_Founds = project_Not_Found;
    }

    public String getProject_Not_Found() {
        return Project_Not_Founds;
    }

    public void setProject_Not_Found(String projectNotFound) {
    	Project_Not_Founds = projectNotFound;
    }
}