package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Details type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Details")
public final class Details implements Model {
  public static final QueryField ID = field("Details", "id");
  public static final QueryField TITLE = field("Details", "title");
  public static final QueryField BODY = field("Details", "body");
  public static final QueryField STATE = field("Details", "state");
  public static final QueryField IMAGE_NAME = field("Details", "imageName");
  public static final QueryField LON = field("Details", "lon");
  public static final QueryField LAT = field("Details", "lat");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="String") String state;
  private final @ModelField(targetType="String") String imageName;
  private final @ModelField(targetType="Float") Double lon;
  private final @ModelField(targetType="Float") Double lat;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getState() {
      return state;
  }
  
  public String getImageName() {
      return imageName;
  }
  
  public Double getLon() {
      return lon;
  }
  
  public Double getLat() {
      return lat;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Details(String id, String title, String body, String state, String imageName, Double lon, Double lat) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.state = state;
    this.imageName = imageName;
    this.lon = lon;
    this.lat = lat;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Details details = (Details) obj;
      return ObjectsCompat.equals(getId(), details.getId()) &&
              ObjectsCompat.equals(getTitle(), details.getTitle()) &&
              ObjectsCompat.equals(getBody(), details.getBody()) &&
              ObjectsCompat.equals(getState(), details.getState()) &&
              ObjectsCompat.equals(getImageName(), details.getImageName()) &&
              ObjectsCompat.equals(getLon(), details.getLon()) &&
              ObjectsCompat.equals(getLat(), details.getLat()) &&
              ObjectsCompat.equals(getCreatedAt(), details.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), details.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBody())
      .append(getState())
      .append(getImageName())
      .append(getLon())
      .append(getLat())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Details {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("imageName=" + String.valueOf(getImageName()) + ", ")
      .append("lon=" + String.valueOf(getLon()) + ", ")
      .append("lat=" + String.valueOf(getLat()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Details justId(String id) {
    return new Details(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      body,
      state,
      imageName,
      lon,
      lat);
  }
  public interface TitleStep {
    BuildStep title(String title);
  }
  

  public interface BuildStep {
    Details build();
    BuildStep id(String id);
    BuildStep body(String body);
    BuildStep state(String state);
    BuildStep imageName(String imageName);
    BuildStep lon(Double lon);
    BuildStep lat(Double lat);
  }
  

  public static class Builder implements TitleStep, BuildStep {
    private String id;
    private String title;
    private String body;
    private String state;
    private String imageName;
    private Double lon;
    private Double lat;
    @Override
     public Details build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Details(
          id,
          title,
          body,
          state,
          imageName,
          lon,
          lat);
    }
    
    @Override
     public BuildStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep state(String state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep imageName(String imageName) {
        this.imageName = imageName;
        return this;
    }
    
    @Override
     public BuildStep lon(Double lon) {
        this.lon = lon;
        return this;
    }
    
    @Override
     public BuildStep lat(Double lat) {
        this.lat = lat;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String body, String state, String imageName, Double lon, Double lat) {
      super.id(id);
      super.title(title)
        .body(body)
        .state(state)
        .imageName(imageName)
        .lon(lon)
        .lat(lat);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder state(String state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder imageName(String imageName) {
      return (CopyOfBuilder) super.imageName(imageName);
    }
    
    @Override
     public CopyOfBuilder lon(Double lon) {
      return (CopyOfBuilder) super.lon(lon);
    }
    
    @Override
     public CopyOfBuilder lat(Double lat) {
      return (CopyOfBuilder) super.lat(lat);
    }
  }
  
}
