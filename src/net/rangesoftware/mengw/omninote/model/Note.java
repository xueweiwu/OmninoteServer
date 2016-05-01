package net.rangesoftware.mengw.omninote.model;

import java.sql.Timestamp;

import edu.cmu.xueweiw.omninote.db.Column;
import edu.cmu.xueweiw.omninote.db.Column.DataType;
import edu.cmu.xueweiw.omninote.db.Table;

/**
 * Created by Xuewei Wu on 4/11/16.
 */
@Table(name="note")
public class Note extends Model {

	@Column(name="title", type =DataType.TEXT)
	private String title;
	
	@Column(name="ownerid", type =DataType.TEXT)
	private int ownerID;
	
	@Column(name="ownername", type =DataType.TEXT)
	private String ownername;

	@Column(name="visibility", type =DataType.TEXT)
	private String visibility;

	@Column(name="createdTime", type =DataType.TEXT)
	private String createdTime;

	@Column(name="category", type =DataType.TEXT)
	private String category;

	@Column(name="latitude", type =DataType.DOUBLE)
	private double latitude;
	
	@Column(name="longitude", type =DataType.DOUBLE)
	private double longitude;
	
	@Column(name="content", type =DataType.TEXT)
	private String content;

	@Column(name="image", type =DataType.TEXT)
	private String imagePath;
	
	@Column(name="voice", type =DataType.TEXT)
	private String voicePath;
	
	public Note() {
		
	}

	public Note(int id, String title, int ownerID, String visibility, String createdTime, String category,
			double latitude, double longitude, String content) {
		this.id = id;
		this.title = title;
		this.ownerID = ownerID;
		this.visibility = visibility;
		this.createdTime = createdTime;
		this.category = category;
		this.latitude = latitude;
		this.longitude = longitude;
		this.content = content;
		this.imagePath = null;
		this.voicePath = null;
	}

	public Note(int id, String title, int ownerID, String visibility, String createdTime, String category,
			double latitude, double longitude, String content, String imagePath, String voicePath) {
		this.voicePath = voicePath;
		this.id = id;
		this.title = title;
		this.ownerID = ownerID;
		this.visibility = visibility;
		this.createdTime = createdTime;
		this.category = category;
		this.latitude = latitude;
		this.longitude = longitude;
		this.content = content;
		this.imagePath = imagePath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	// public void setCreatedTime(String createdTime) {
	// this.createdTime = createdTime;
	// }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getVoicePath() {
		return voicePath;
	}

	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}

	@Override
	public String toString() {
		return "Note{" + "id=" + id + ", title='" + title + '\'' + /*", owner=" + owner + */", visibility='" + visibility
				+ '\'' + ", createdTime='" + createdTime + '\'' + ", category='" + category + '\'' + ", latitude="
				+ latitude + ", longitude=" + longitude + ", content='" + content + '\'' + ", imagePath='" + imagePath
				+ '\'' + ", voicePath='" + voicePath + '\'' + '}';
	}
}
