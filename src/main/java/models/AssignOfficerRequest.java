package models;

import com.fasterxml.jackson.annotation.JsonProperty;
public class AssignOfficerRequest
{
    @JsonProperty("officer_id")
    private int officerId;

    public AssignOfficerRequest(int officerId)
    {
        this.officerId = officerId;
    }

    public int getOfficerId()
    {
        return officerId;
    }

    public void setOfficerId(int officerId)
    {
        this.officerId = officerId;
    }
}