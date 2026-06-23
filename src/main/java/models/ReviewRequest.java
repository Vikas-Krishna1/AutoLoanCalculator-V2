package models;
import com.fasterxml.jackson.annotation.JsonProperty;
public class ReviewRequest {
    @JsonProperty("officer_id")
    private int officerId;
    @JsonProperty("review_notes")
    private String notes;

    public ReviewRequest(int officerId, String notes) {
        this.officerId = officerId;
        this.notes = notes;
    }

    public int getOfficerId() {
        return officerId;
    }

    public String getNotes() {
        return notes;
    }
}